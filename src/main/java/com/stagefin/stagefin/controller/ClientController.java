package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Categorie;
import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.entities.Commande;
import com.stagefin.stagefin.repository.ClientRepository;
import com.stagefin.stagefin.repository.CommandeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class ClientController {
    @Autowired
    private ClientRepository ClientRepository;

    @GetMapping("/usr/download/showTotal")
    public ResponseEntity<Resource> downloadShowTotal() throws IOException {
        // Load the "showTotal.html" file from the classpath
        Resource resource = new ClassPathResource("templates/showTotal.html");

        // Check if the resource exists
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=showTotal.html");

            // Set the content type to HTML
            MediaType mediaType = MediaType.TEXT_HTML;

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(resource);
        } else {
            throw new NoSuchElementException("File not found");
        }
    }



    @GetMapping("/usr/showTotal/{id}")
    public String showTotal(@PathVariable(name = "id") Long id, Model model) {
        Client client = ClientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Client introuvable"));
        double totalBill = 0.0;
        for (Commande commande : client.getCommande()) {
            totalBill += commande.getCategorie().getPrix();
        }

        model.addAttribute("client", client);
        model.addAttribute("totalBill", totalBill);

        return "showTotal";
    }



    @GetMapping("/usr/listclient")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Client> pageClient = ClientRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listClients",pageClient.getContent());
        model.addAttribute("pages",new int[pageClient.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        //return "test.Site1";
        return "Clients";
    }


    @GetMapping("/usr/deleteClient")
    public String deleteClient(@RequestParam(name = "id") Long id, String keyword, int page){
        ClientRepository.deleteById(id);
        return "redirect:/usr/listclient?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/usr/formClient")
    public String formClient(Model model ){
        model.addAttribute("Client",new Client());
        return "formClient";
    }
    @PostMapping("/usr/saveClient")
    public String saveClient(@Valid Client Client, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formClient";
        ClientRepository.save(Client);
        return "redirect:/usr/listclient";
    }

    @PostMapping("/usr/editClient/{id}")
    public String editClient(@Valid Client Client, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editClient";
        }

        Client p = ClientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Client introuvable"));
        p.setNom(Client.getNom());
        p.setPrenom(Client.getPrenom());
        p.setDateNaissance(Client.getDateNaissance());
        p.setEmail(Client.getEmail());
        p.setTel(Client.getTel());
        p.setCIN(Client.getCIN());
        p.setSexe(Client.getSexe());

        ClientRepository.save(p);
        return "redirect:/usr/listclient";
    }
    @GetMapping("/usr/editClient")
    public String editClient(@RequestParam(name = "id") Long id, Model model){
        Client Client=ClientRepository.findById(id).get();
        model.addAttribute("Client",Client);
        return "editClient";
    }

    


}
