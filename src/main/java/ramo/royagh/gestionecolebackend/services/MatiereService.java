package ramo.royagh.gestionecolebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ramo.royagh.gestionecolebackend.entities.Etudiant;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.entities.Professeur;
import ramo.royagh.gestionecolebackend.exceptions.IdException;
import ramo.royagh.gestionecolebackend.repositories.MatiereRepository;

import java.util.List;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public Matiere saveOrUpdateMatiere(Matiere matiere){

        try {
            // Upper casing the studentId
            matiere.setMatiereId(matiere.getMatiereId().toUpperCase());
            return matiereRepository.save(matiere);
        }catch (Exception e){
            throw new IdException("Matiere ID: "+matiere.getMatiereId().toUpperCase()+" existe déjà!");
        }

    }

    public Matiere findByMatiereId(String matiereId){

        Matiere matiere = matiereRepository.findByMatiereId(matiereId.toUpperCase());

        if (matiere == null){
            throw new IdException("Matiere ID: "+matiereId+" n'existe pas!");
        }

        return matiere;
    }

    public Iterable<Matiere> findAllMatieres(){
        return matiereRepository.findAll();
    }

    public void deleteByMatiereId(String matiereId){
        Matiere matiere = findByMatiereId(matiereId);
        matiereRepository.delete(matiere);
    }

    public List<Etudiant> findEtudiantsOfMatiere(String matiereId){
        Matiere matiere = findByMatiereId(matiereId);
        return matiere.getEtudiants();
    }

    public List<Professeur> findProfesseursOfMatiere(String matiereId){
        Matiere matiere = findByMatiereId(matiereId);
        return matiere.getProfesseurs();
    }

}
