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
@Table(name = "professeurs")
public class Professeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom du professeur est obligatoire!")
    private String nom;

    @NotBlank(message = "Prénom du professeur est obligatoire!")
    private String prenom;

    @Size(min = 6, max = 12, message = "Identifiant entre 6 et 12 caractères!")
    @NotBlank(message = "Identifiant du professeur est obligatoire!")
    @Column(updatable = false, unique = true)
    private String professeurId;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "professeurs_matieres",
            joinColumns = {@JoinColumn(name = "professeurId")},
            inverseJoinColumns = {@JoinColumn(name = "matiereId")}
    )
    @JsonIgnore
    private List<Matiere> matieres;
}
