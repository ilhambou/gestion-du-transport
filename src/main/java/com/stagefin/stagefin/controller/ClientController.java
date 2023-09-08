package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
public class ClientController {
    @Autowired
    private ClientRepository ClientRepository;

    @GetMapping("/admin/listclient")
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


    @GetMapping("/admin/deleteClient")
    public String deleteClient(@RequestParam(name = "id") Long id, String keyword, int page){
        ClientRepository.deleteById(id);
        return "redirect:/admin/listclient?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formClient")
    public String formClient(Model model ){
        model.addAttribute("Client",new Client());
        return "formClient";
    }
    @PostMapping("/admin/saveClient")
    public String saveClient(@Valid Client Client, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formClient";
        ClientRepository.save(Client);
        return "redirect:/admin/listclient";
    }

    @PostMapping("/admin/editClient/{id}")
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
        return "redirect:/admin/listclient";
    }
    @GetMapping("/admin/editClient")
    public String editClient(@RequestParam(name = "id") Long id, Model model){
        Client Client=ClientRepository.findById(id).get();
        model.addAttribute("Client",Client);
        return "editClient";
    }


}
