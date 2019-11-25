package ramo.royagh.gestionecolebackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestion-ecole")
@CrossOrigin
public class GestionecolebackendController {

    @GetMapping(value = "")
    public String hello(){
        return "Bienvenu!\nApplication de gestion d'école";
    }

}
