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
@Table(name = "matieres")
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom de la matière est obligatoire!")
    private String nom;

    @Size(min = 6, max = 12, message = "Identifiant entre 6 et 12 caractères!")
    @NotBlank(message = "Identifiant de la matiere est obligatoire!")
    @Column(updatable = false, unique = true)
    private String matiereId;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "matieres")
    @JsonIgnore
    private List<Etudiant> etudiants;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "matieres")
    @JsonIgnore
    private List<Professeur> professeurs;

}
