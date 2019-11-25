package ramo.royagh.gestionecolebackend.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.entities.Professeur;
import ramo.royagh.gestionecolebackend.services.ProfesseurService;
import ramo.royagh.gestionecolebackend.services.MapValidationErrorService;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/gestion-ecole/professeur/")
@CrossOrigin
public class ProfesseurController {
    @Autowired
    private ProfesseurService professeurService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @PostMapping("")
    public ResponseEntity<?> createNewProfesseur(@Valid @RequestBody Professeur professeur,
            /*This analyses the object and determines whether or not there are errors*/
                                                     BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null){return errorMap;}
        Professeur professeur1 = professeurService.saveOrUpdateProfesseur(professeur);
        return new ResponseEntity<>(professeur1, HttpStatus.CREATED);
    }
    @GetMapping("{professeurId}")
    public ResponseEntity<?> getProfesseurById(@PathVariable String professeurId){
        Professeur professeur = professeurService.findByProfesseurId(professeurId);
        return new ResponseEntity<>(professeur, HttpStatus.OK);
    }
    @GetMapping("all")
    public Iterable<Professeur> getAllProfesseurs(){
        return professeurService.findAllProfesseurs();
    }
    @DeleteMapping("{professeurId}")
    public ResponseEntity<?> deleteProfesseur(@PathVariable String professeurId){
        professeurService.deleteByProfesseurId(professeurId);
        return new ResponseEntity<>("Le professeur avec l'ID: "+professeurId+" a été supprimé avec succès!", HttpStatus.OK);
    }

    // Operations on Matiere

    @PatchMapping("{professeurId}/matieres/{matiereId}")
    public ResponseEntity<Professeur> addMatiereToProfesseur(@PathVariable String professeurId, @PathVariable String matiereId){
        Professeur professeur = professeurService.addMatiereToProfesseur(professeurId, matiereId);
        return new ResponseEntity<>(professeur, HttpStatus.OK);
    }

    @GetMapping("{professeurId}/matieres")
    public ResponseEntity<List<Matiere>> findMatieresByProfesseurId(@PathVariable String professeurId){
        List<Matiere> matieres = professeurService.findMatieresByProfesseurId(professeurId);
        return new ResponseEntity<>(matieres, HttpStatus.OK);
    }

    @DeleteMapping("{professeurId}/matieres/{matiereId}")
    public ResponseEntity<?> deleteMatiereOfProfesseur(@PathVariable String professeurId, @PathVariable String matiereId){
        professeurService.deleteMatiereOfProfesseur(professeurId, matiereId);
        return new ResponseEntity<>("Le professeur ID: "+professeurId+" n'enseigne plus la matière ID: "+matiereId, HttpStatus.OK);
    }

}
