package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.*;
import com.stagefin.stagefin.entities.Voyage;
import com.stagefin.stagefin.repository.*;
import com.stagefin.stagefin.repository.VoyageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class VoyageController {

    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VoyageRepository voyageRepository;


    @GetMapping("/admin/listVoyage")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Voyage> pageVoyage = voyageRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listVoyages",pageVoyage.getContent());
        model.addAttribute("pages",new int[pageVoyage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        //return "test.Site1";
        return "Voyages";
    }


    @GetMapping("/admin/deleteVoyage")
    public String deleteVoyage(@RequestParam(name = "id") Long id, String keyword, int page){
        voyageRepository.deleteById(id);
        return "redirect:/admin/listVoyage?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formVoyage")
    public String formVoyage(Model model ){
        model.addAttribute("Voyage",new Voyage());
        List<Employee> employee = employeeRepository.findAll(); // Replace 'patientRepository' with the actual repository for the Patient entity

        model.addAttribute("listEmoloyee", employee);
        List<Vehicule> vehicule = vehiculeRepository.findAll(); // Replace 'patientRepository' with the actual repository for the Patient entity

        model.addAttribute("listVehicule", vehicule);

        model.addAttribute("listvoyage",new Voyage());
        List<Voyage> voyage = voyageRepository.findAll();




        return "formVoyage";
    }
    @PostMapping("/admin/saveVoyage")
    public String saveVoyage(@Valid Voyage Voyage, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formVoyage";
        voyageRepository.save(Voyage);
        return "redirect:/admin/listVoyage";
    }

    @PostMapping("/admin/editVoyage/{id}")
    public String editVoyage(@Valid Voyage Voyage, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editVoyage";
        }

        Voyage p = voyageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Voyage introuvable"));

        p.setNom(Voyage.getNom());
        p.setEmployee(Voyage.getEmployee());
        p.setVehicule(Voyage.getVehicule());
        p.setCommandes(Voyage.getCommandes());
        p.setHeure_depart(Voyage.getHeure_depart());
        p.setHeure_arrive(Voyage.getHeure_arrive());
        p.setDestination(Voyage.getDestination());


        voyageRepository.save(p);
        return "redirect:/admin/listVoyage";
    }
    @GetMapping("/admin/editVoyage")
    public String editVoyage(@RequestParam(name = "id") Long id, Model model){
        Voyage Voyage=voyageRepository.findById(id).get();
        model.addAttribute("Voyage",Voyage);
        return "editVoyage";
    }


}
