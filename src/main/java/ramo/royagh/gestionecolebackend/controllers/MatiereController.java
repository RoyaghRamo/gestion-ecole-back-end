package ramo.royagh.gestionecolebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ramo.royagh.gestionecolebackend.entities.Etudiant;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.entities.Professeur;
import ramo.royagh.gestionecolebackend.services.EtudiantService;
import ramo.royagh.gestionecolebackend.services.MatiereService;
import ramo.royagh.gestionecolebackend.services.MapValidationErrorService;
import ramo.royagh.gestionecolebackend.services.ProfesseurService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gestion-ecole/matiere/")
@CrossOrigin
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewMatiere(@Valid @RequestBody Matiere matiere,
            /*This analyses the object and determines whether or not there are errors*/
                                                     BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null){return errorMap;}

        Matiere matiere1 = matiereService.saveOrUpdateMatiere(matiere);
        return new ResponseEntity<>(matiere1, HttpStatus.CREATED);
    }

    @GetMapping("{matiereId}")
    public ResponseEntity<?> getMatiereById(@PathVariable String matiereId){

        Matiere matiere = matiereService.findByMatiereId(matiereId);

        return new ResponseEntity<>(matiere, HttpStatus.OK);
    }

    @GetMapping("all")
    public Iterable<Matiere> getAllMatieres(){
        return matiereService.findAllMatieres();
    }

    @DeleteMapping("{matiereId}")
    public ResponseEntity<?> deleteMatiere(@PathVariable String matiereId){
        matiereService.deleteByMatiereId(matiereId);
        return new ResponseEntity<>("L'étudiant avec l'ID: "+matiereId+" a été supprimé avec succès!", HttpStatus.OK);
    }

    // Operations on Etudiants

    @GetMapping("{matiereId}/etudiants")
    public ResponseEntity<?> getEtudiantsOfMatiere(@PathVariable String matiereId){
        List<Etudiant> etudiants = matiereService.findEtudiantsOfMatiere(matiereId);
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Operations on Professeur

    @GetMapping("{matiereId}/professeurs")
    public ResponseEntity<?> getProfesseursOfMatiere(@PathVariable String matiereId){
        List<Professeur> professeurs = matiereService.findProfesseursOfMatiere(matiereId);
        return new ResponseEntity<>(professeurs, HttpStatus.OK);
    }

}
