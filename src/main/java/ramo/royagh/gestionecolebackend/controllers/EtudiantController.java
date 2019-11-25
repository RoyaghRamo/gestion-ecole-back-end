package ramo.royagh.gestionecolebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ramo.royagh.gestionecolebackend.entities.Etudiant;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.services.EtudiantService;
import ramo.royagh.gestionecolebackend.services.MapValidationErrorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gestion-ecole/etudiant/")
@CrossOrigin
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewEtudiant(@Valid @RequestBody Etudiant etudiant,
            /*This analyses the object and determines whether or not there are errors*/
                                                     BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null){return errorMap;}

        Etudiant etudiant1 = etudiantService.saveOrUpdateEtudiant(etudiant);
        return new ResponseEntity<>(etudiant1, HttpStatus.CREATED);
    }

    @GetMapping("{etudiantId}")
    public ResponseEntity<?> getEtudiantById(@PathVariable String etudiantId){

        Etudiant etudiant = etudiantService.findByEtudiantId(etudiantId);

        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }

    @GetMapping("all")
    public Iterable<Etudiant> getAllEtudiants(){
        return etudiantService.findAllEtudiants();
    }

    @DeleteMapping("{etudiantId}")
    public ResponseEntity<?> deleteEtudiant(@PathVariable String etudiantId){
        etudiantService.deleteByEtudiantId(etudiantId);
        return new ResponseEntity<>("L'étudiant avec l'ID: " + etudiantId + " a été supprimé avec succès!", HttpStatus.OK);
    }

    // Operations on Matiere

    @PatchMapping("{etudiantId}/matieres/{matiereId}")
    public ResponseEntity<?> addMatiereToEtudiant(@PathVariable String etudiantId, @PathVariable String matiereId){
        Etudiant etudiant = etudiantService.addMatiereToEtudiant(etudiantId, matiereId);
        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }

    @GetMapping("{etudiantId}/matieres")
    public ResponseEntity<List<Matiere>> getMatieresByEtudiantId(@PathVariable String etudiantId){
        List<Matiere> matieres = etudiantService.findMatieresByEtudiantId(etudiantId);
        return new ResponseEntity<>(matieres, HttpStatus.OK);
    }

    @DeleteMapping("{etudiantId}/matieres/{matiereId}")
    public ResponseEntity<?> deleteMatiereOfEtudiant(@PathVariable String etudiantId, @PathVariable String matiereId){
        etudiantService.deleteMatiereOfEtudiant(etudiantId, matiereId);
        return new ResponseEntity<>("L'étudiant ID: "+etudiantId+" n'est plus inscrit à la matière ID: "+matiereId, HttpStatus.OK);
    }

}
