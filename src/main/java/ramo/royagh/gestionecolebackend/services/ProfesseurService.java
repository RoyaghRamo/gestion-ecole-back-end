package ramo.royagh.gestionecolebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.entities.Professeur;
import ramo.royagh.gestionecolebackend.exceptions.IdException;
import ramo.royagh.gestionecolebackend.repositories.MatiereRepository;
import ramo.royagh.gestionecolebackend.repositories.ProfesseurRepository;

import java.util.List;

@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    public Professeur saveOrUpdateProfesseur(Professeur professeur){

        try {
            // Upper casing the studentId
            professeur.setProfesseurId(professeur.getProfesseurId().toUpperCase());
            return professeurRepository.save(professeur);
        }catch (Exception e){
            throw new IdException("Professeur ID: "+professeur.getProfesseurId().toUpperCase()+" existe déjà!");
        }

    }

    public Professeur findByProfesseurId(String professeurId){

        Professeur professeur = professeurRepository.findByProfesseurId(professeurId.toUpperCase());

        if (professeur == null){
            throw new IdException("Professeur ID: "+professeurId+" n'est pas trouvé!");
        }

        return professeur;
    }

    public Iterable<Professeur> findAllProfesseurs(){
        return professeurRepository.findAll();
    }

    public void deleteByProfesseurId(String professeurId){
        Professeur professeur = findByProfesseurId(professeurId);
        professeurRepository.delete(professeur);
    }

    public Professeur addMatiereToProfesseur(String professeurId, String matiereId){

        Matiere matiere = matiereRepository.findByMatiereId(matiereId.toUpperCase());
        Professeur professeur = findByProfesseurId(professeurId);

        if(professeur.getMatieres().contains(matiere)){
            throw new IdException("Le professeur: "+professeurId+" enseigne déjà cette matiere :"+matiereId);
        }

        try{
            matiere.getProfesseurs().add(professeur);
            professeur.getMatieres().add(matiere);
            professeurRepository.save(professeur);
            matiereRepository.save(matiere);
        }catch (Exception e){
            if (professeur==null){
                throw new IdException("Professeur ID: "+professeurId+" n'existe pas!");
            }
            if (matiere == null){
                throw new IdException("Matiere ID: "+matiereId+" n'existe pas!");
            }
        }

        return professeur;
    }

    public List<Matiere> findMatieresByProfesseurId(String professeurId){

        Professeur professeur = findByProfesseurId(professeurId);
        // We don't need to put the if statement because we already have it in the findByProfesseurId method
        return professeur.getMatieres();

    }

    public void deleteMatiereOfProfesseur(String professeurId, String matiereId){
        Professeur professeur = findByProfesseurId(professeurId);
        Matiere matiere = matiereRepository.findByMatiereId(matiereId);

        try {
            professeur.getMatieres().remove(matiere);
            matiere.getProfesseurs().remove(professeur);
            matiereRepository.save(matiere);
            professeurRepository.save(professeur);
        }catch (Exception e){
            if(professeur==null){
                throw new IdException("Professeur ID: "+professeurId+" n'existe pas!");
            }
            if (matiere == null){
                throw new IdException("Matiere ID: "+matiereId+" n'existe pas!");
            }
        }
    }

}
