package ramo.royagh.gestionecolebackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ramo.royagh.gestionecolebackend.entities.Etudiant;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant, Long> {

    @Override
    Iterable<Etudiant> findAll();

    Etudiant findByEtudiantId(String etudiantId);

}
