package ramo.royagh.gestionecolebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ramo.royagh.gestionecolebackend.entities.Etudiant;
import ramo.royagh.gestionecolebackend.entities.Matiere;
import ramo.royagh.gestionecolebackend.exceptions.IdException;
import ramo.royagh.gestionecolebackend.repositories.EtudiantRepository;
import ramo.royagh.gestionecolebackend.repositories.MatiereRepository;

import java.util.List;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant){
        try {
            // Upper casing the studentId
            etudiant.setEtudiantId(etudiant.getEtudiantId().toUpperCase());
            return etudiantRepository.save(etudiant);
        }catch (Exception e){
            throw new IdException("Etudiant ID: "+etudiant.getEtudiantId().toUpperCase()+" existe déjà!");
        }

    }

    public Etudiant findByEtudiantId(String etudiantId){
        Etudiant etudiant = etudiantRepository.findByEtudiantId(etudiantId.toUpperCase());

        if (etudiant == null){
            throw new IdException("Etudiant ID: "+etudiantId+" n'est pas trouvé!");
        }

        return etudiant;
    }

    public Iterable<Etudiant> findAllEtudiants(){
        return etudiantRepository.findAll();
    }

    public void deleteByEtudiantId(String etudiantId){
        Etudiant etudiant = findByEtudiantId(etudiantId);
        etudiantRepository.delete(etudiant);
    }

    public Etudiant addMatiereToEtudiant(String etudiantId, String matiereId){

        Matiere matiere = matiereRepository.findByMatiereId(matiereId);
        Etudiant etudiant = findByEtudiantId(etudiantId);

        if(etudiant.getMatieres().contains(matiere)){
            throw new IdException("L'étudiant ID: "+etudiantId+" est déjà inscrit à la matiere:" +matiereId);
        }

        try {
            etudiant.getMatieres().add(matiere);
            matiere.getEtudiants().add(etudiant);
            etudiantRepository.save(etudiant);
            matiereRepository.save(matiere);
        }catch (Exception e){
            if(etudiant==null){
                throw new IdException("L'étudiant ID: "+etudiantId+" n'existe pas!");
            }
            if (matiere == null){
                throw new IdException("Matiere ID: "+matiereId+" n'existe pas!");
            }
        }

        return etudiant;
    }

    public List<Matiere> findMatieresByEtudiantId(String etudiantId){

        Etudiant etudiant = findByEtudiantId(etudiantId);
        // We don't need to put the if statement because we already have it in the findByEtudiantId method

        return etudiant.getMatieres();

    }

    public void deleteMatiereOfEtudiant(String etudiantId, String matiereId){
        Etudiant etudiant = findByEtudiantId(etudiantId);
        Matiere matiere = matiereRepository.findByMatiereId(matiereId);

        try {
            etudiant.getMatieres().remove(matiere);
            matiere.getEtudiants().remove(etudiant);
            etudiantRepository.save(etudiant);
            matiereRepository.save(matiere);
        }catch (Exception e){
            if(etudiant==null){
                throw new IdException("L'étudiant "+etudiantId+" n'existe pas!");
            }
            if (matiere == null){
                throw new IdException("Matiere ID: "+matiereId+" n'est pas trouvé!");
            }
        }
    }

}
