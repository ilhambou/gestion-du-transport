package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.repository.ClientRepository;
import com.stagefin.stagefin.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {
    @Autowired
    ClientRepository ClientRepository;


    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        long totalClients = ClientRepository.count();
        long totalMaleClients = ClientRepository.countBySexe("HOMME");
        long totalFemaleClients = ClientRepository.countBySexe("FEMME");

        long totalClientss = totalMaleClients + totalFemaleClients;

        double malePercentage = (double) totalMaleClients / totalClientss * 100;
        double femalePercentage = (double) totalFemaleClients / totalClientss* 100;


        model.addAttribute("totalClients", totalClients);


        model.addAttribute("totalMaleClients", totalMaleClients);
        model.addAttribute("totalFemaleClients", totalFemaleClients);
        model.addAttribute("malePercentage", malePercentage);
        model.addAttribute("femalePercentage", femalePercentage);



        return "dashboard";
    }
}
