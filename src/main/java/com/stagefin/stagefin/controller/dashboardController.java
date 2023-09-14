package com.stagefin.stagefin.controller;

import com.stagefin.stagefin.entities.Client;
import com.stagefin.stagefin.entities.Commande;
import com.stagefin.stagefin.repository.ClientRepository;
import com.stagefin.stagefin.repository.CommandeRepository;
import com.stagefin.stagefin.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class dashboardController {
    @Autowired
    ClientRepository ClientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CommandeRepository commandeRepository;


    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        long totalClients = ClientRepository.count();
        long totalEmployees = employeeRepository.count();
        long totalCommandes = commandeRepository.count();



        long totalMaleClients = ClientRepository.countBySexe("HOMME");
        long totalFemaleClients = ClientRepository.countBySexe("FEMME");

        long totalClientss = totalMaleClients + totalFemaleClients;

        double malePercentage = (double) totalMaleClients / totalClientss * 100;
        double femalePercentage = (double) totalFemaleClients / totalClientss* 100;


        model.addAttribute("totalClients", totalClients);
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalCommades", totalCommandes);




        model.addAttribute("totalMaleClients", totalMaleClients);
        model.addAttribute("totalFemaleClients", totalFemaleClients);
        model.addAttribute("malePercentage", malePercentage);
        model.addAttribute("femalePercentage", femalePercentage);

        List<Commande> commandes = commandeRepository.findAll();

        double totalCommandePrice = 0.0;
        for (Commande commande : commandes) {
            totalCommandePrice += commande.getCategorie().getPrix();
        }

        model.addAttribute("totalCommandePrice", totalCommandePrice);


        Date currentDate = new Date();
        long totalClientsOlderThan25 = ClientRepository.countClientsOlderThan25(currentDate);
        long totalClientsYoungerThan25 = ClientRepository.countClientsYoungerThan25(currentDate);

        long totalClientsInAgeGroups = totalClientsOlderThan25 + totalClientsYoungerThan25;

        double olderThan25Percentage = (double) totalClientsOlderThan25 / totalClientsInAgeGroups * 100;
        double youngerThan25Percentage = (double) totalClientsYoungerThan25 / totalClientsInAgeGroups * 100;

        model.addAttribute("totalClientsOlderThan25", totalClientsOlderThan25);
        model.addAttribute("totalClientsYoungerThan25", totalClientsYoungerThan25);
        model.addAttribute("olderThan25Percentage", olderThan25Percentage);
        model.addAttribute("youngerThan25Percentage", youngerThan25Percentage);








        return "dashboard";
    }


}
