package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Employee;
import com.stagefin.stagefin.repository.EmployeeRepository;
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
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String sidebar() {
        return "template1";
    }
    @GetMapping("/admin/list")
    public String list(Model model,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size,
                       @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Employee> pageEmployee = employeeRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listEmployees",pageEmployee.getContent());
        model.addAttribute("pages",new int[pageEmployee.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        //return "test.Site1";
        return "employees";
    }


    @GetMapping("/admin/deleteEmployee")
    public String deleteEmployee(@RequestParam(name = "id") Long id, String keyword, int page){
        employeeRepository.deleteById(id);
        return "redirect:/admin/list?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formEmployee")
    public String formEmployee(Model model ){
        model.addAttribute("employee",new Employee());
        return "formEmployee";
    }
    @PostMapping("/admin/saveEmployee")
    public String saveEmployee(@Valid Employee employee, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formEmployee";
        employeeRepository.save(employee);
        return "redirect:/admin/list";
    }

    @PostMapping("/admin/editEmployee/{id}")
    public String editEmployee(@Valid Employee employee, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()) {
            return "editEmployee";
        }

        Employee p = employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("employee introuvable"));
        p.setNom(employee.getNom());
        p.setPrenom(employee.getPrenom());
        p.setDateNaissance(employee.getDateNaissance());
        p.setEmail(employee.getEmail());
        p.setTel(employee.getTel());
        p.setCIN(employee.getCIN());
        p.setSexe(employee.getSexe());

        employeeRepository.save(p);
        return "redirect:/admin/list";
    }
    @GetMapping("/admin/editEmployee")
    public String editEmployee(@RequestParam(name = "id") Long id, Model model){
        Employee employee=employeeRepository.findById(id).get();
        model.addAttribute("employee",employee);
        return "editEmployee";
    }


}
