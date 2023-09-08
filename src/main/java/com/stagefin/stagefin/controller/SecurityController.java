package com.stagefin.stagefin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
    @GetMapping("/notAuthorized")
    public String notAutozired()
    {
        return "notAuthorized";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
   /*@PostMapping("/login")
    public String loginPost() {
        // Rediriger vers la page "template1" après une connexion réussie
        return "redirect:/template1";
    }
*/

}
