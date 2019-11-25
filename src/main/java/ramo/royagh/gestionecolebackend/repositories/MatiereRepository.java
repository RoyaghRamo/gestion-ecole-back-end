package ramo.royagh.gestionecolebackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ramo.royagh.gestionecolebackend.entities.Matiere;

@Repository
public interface MatiereRepository extends CrudRepository<Matiere, Long> {

    @Override
    Iterable<Matiere> findAll();

    Matiere findByMatiereId(String matiereId);

}
