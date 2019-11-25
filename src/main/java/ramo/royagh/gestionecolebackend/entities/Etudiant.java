package ramo.royagh.gestionecolebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom d'étudiant est obligatoire!")
    private String nom;

    @NotBlank(message = "Prénom d'étudiant est obligatoire!")
    private String prenom;

    @Size(min = 6, max = 12, message = "Identifiant entre 6 et 12 caractères!")
    @NotBlank(message = "Identifiant d'étudiant est obligatoire!")
    @Column(updatable = false, unique = true)
    private String etudiantId;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "etudiants_matieres",
            joinColumns = {@JoinColumn(name = "etudiantId")},
            inverseJoinColumns = {@JoinColumn(name = "matiereId")}
    )
    @JsonIgnore
    private List<Matiere> matieres;

}
