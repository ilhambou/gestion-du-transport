package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Categorie;
import com.stagefin.stagefin.repository.CategorieRepository;
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
public class CategorieController {
    @Autowired
    private CategorieRepository CategorieRepository;

    @GetMapping("/admin/listCategorie")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Categorie> pageCategorie = CategorieRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listCategories",pageCategorie.getContent());
        model.addAttribute("pages",new int[pageCategorie.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "Categories";
    }


    @GetMapping("/admin/deleteCategorie")
    public String deleteCategorie(@RequestParam(name = "id") Long id, String keyword, int page){
        CategorieRepository.deleteById(id);
        return "redirect:/admin/listCategorie?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formCategorie")
    public String formCategorie(Model model ){
        model.addAttribute("Categorie",new Categorie());
        return "formCategorie";
    }
    @PostMapping("/admin/saveCategorie")
    public String saveCategorie(@Valid Categorie Categorie, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formCategorie";
        CategorieRepository.save(Categorie);
        return "redirect:/admin/listCategorie";
    }

    @PostMapping("/admin/editCategorie/{id}")
    public String editCategorie(@Valid Categorie Categorie, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editCategorie";
        }

        Categorie p = CategorieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Categorie introuvable"));
        p.setNom(Categorie.getNom());
        p.setPrix(Categorie.getPrix());

        CategorieRepository.save(p);
        return "redirect:/admin/listCategorie";
    }
    @GetMapping("/admin/editCategorie")
    public String editCategorie(@RequestParam(name = "id") Long id, Model model){
        Categorie Categorie=CategorieRepository.findById(id).get();
        model.addAttribute("Categorie",Categorie);
        return "editCategorie";
    }


}
