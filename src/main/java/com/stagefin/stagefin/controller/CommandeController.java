package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Categorie;
import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.entities.Commande;
import com.stagefin.stagefin.entities.Voyage;
import com.stagefin.stagefin.repository.CategorieRepository;
import com.stagefin.stagefin.repository.ClientRepository;
import com.stagefin.stagefin.repository.CommandeRepository;
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
public class CommandeController {
    @Autowired
    private CommandeRepository CommandeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private VoyageRepository voyageRepository;


    @GetMapping("/usr/listCommande")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Commande> pageCommande = CommandeRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listCommandes",pageCommande.getContent());
        model.addAttribute("pages",new int[pageCommande.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        //return "test.Site1";
        return "Commandes";
    }


    @GetMapping("/usr/deleteCommande")
    public String deleteCommande(@RequestParam(name = "id") Long id, String keyword, int page){
        CommandeRepository.deleteById(id);
        return "redirect:/usr/listCommande?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/usr/formCommande")
    public String formCommande(Model model ){
        model.addAttribute("Commande",new Commande());

        List<Client> client = clientRepository.findAll(); // Replace 'patientRepository' with the actual repository for the Patient entity
        model.addAttribute("listClient", client);

        List<Categorie> categorie = categorieRepository.findAll(); // Replace 'patientRepository' with the actual repository for the Patient entity
        model.addAttribute("listCategorie", categorie);

        List<Voyage> voyage = voyageRepository.findAll(); // Replace 'patientRepository' with the actual repository for the Patient entity
        model.addAttribute("listVoyage", voyage);






        return "formCommande";
    }
    @PostMapping("/usr/saveCommande")
    public String saveCommande(@Valid Commande Commande, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formCommande";
        CommandeRepository.save(Commande);
        return "redirect:/usr/listCommande";
    }

    @PostMapping("/usr/editCommande/{id}")
    public String editCommande(@Valid Commande Commande, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editCommande";
        }

        Commande p = CommandeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Commande introuvable"));
        p.setPoids(Commande.getPoids());
        p.setCategorie(Commande.getCategorie());

        p.setClient(Commande.getClient());
        p.setVoyage(Commande.getVoyage());


        CommandeRepository.save(p);
        return "redirect:/usr/listCommande";
    }
    @GetMapping("/usr/editCommande")
    public String editCommande(@RequestParam(name = "id") Long id, Model model){
        Commande Commande=CommandeRepository.findById(id).get();
        model.addAttribute("Commande",Commande);
        return "editCommande";
    }


}
