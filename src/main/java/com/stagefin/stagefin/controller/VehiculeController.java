package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Vehicule;
import com.stagefin.stagefin.repository.VehiculeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Controller
public class VehiculeController {
    @Autowired
    private VehiculeRepository VehiculeRepository;


    @GetMapping("/admin/listVehicule")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Vehicule> pageVehicule = VehiculeRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listVehicules",pageVehicule.getContent());
        model.addAttribute("pages",new int[pageVehicule.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        //return "test.Site1";
        return "Vehicules";
    }


    @GetMapping("/admin/deleteVehicule")
    public String deleteVehicule(@RequestParam(name = "id") Long id, String keyword, int page){
        VehiculeRepository.deleteById(id);
        return "redirect:/admin/listVehicule?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formVehicule")
    public String formVehicule(Model model ){
        model.addAttribute("Vehicule",new Vehicule());
        return "formVehicule";
    }
    @PostMapping("/admin/saveVehicule")
    public String saveVehicule(@Valid Vehicule Vehicule, BindingResult bindingResult
                               ){
        if (bindingResult.hasErrors()) return "formVehicule";


        VehiculeRepository.save(Vehicule);
        return "redirect:/admin/listVehicule";
    }



    @PostMapping("/admin/editVehicule/{id}")
    public String editVehicule(@Valid Vehicule Vehicule, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editVehicule";
        }

        Vehicule p = VehiculeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Vehicule introuvable"));
        p.setModele(Vehicule.getModele());
        p.setCarburant(Vehicule.getCarburant());
        p.setMarque(Vehicule.getMarque());
        p.setDate_fabrication(Vehicule.getDate_fabrication());
        p.setNom(Vehicule.getNom());


        VehiculeRepository.save(p);
        return "redirect:/admin/listVehicule";
    }
    @GetMapping("/admin/editVehicule")
    public String editVehicule(@RequestParam(name = "id") Long id, Model model){
        Vehicule Vehicule=VehiculeRepository.findById(id).get();
        model.addAttribute("Vehicule",Vehicule);
        return "editVehicule";
    }


}
