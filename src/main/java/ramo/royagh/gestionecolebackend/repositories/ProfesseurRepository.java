package ramo.royagh.gestionecolebackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ramo.royagh.gestionecolebackend.entities.Professeur;

@Repository
public interface ProfesseurRepository extends CrudRepository<Professeur, Long> {

    @Override
    Iterable<Professeur> findAll();

    Professeur findByProfesseurId(String professeurId);

}
