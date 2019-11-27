package ramo.royagh.gestionecolebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom d'utilisateur est obligatoire!")
    @Column(unique = true)
    @Size
    private String username;
    @NotBlank(message = "Veuillez insérer votre nom")
    private String nom;
    @NotBlank(message = "Veuillez insérer votre prenom")
    private String prenom;
    @NotBlank(message = "Le mot de passe est obligatoire!")
    private String password;
    @Transient
    // We're going to have a little class that is going to make sure that
    // these two password fields match before we persist the user and its password
    // but we won't persist this attribute.
    private String confirmPassword;
    private Role role;
    private Date created_At;
    private Date updated_At;
    @PrePersist
    protected void onCreate() {this.created_At= new Date();}
    @PreUpdate
    protected void onUpdate() {this.updated_At = new Date();}
    //UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {return password;}

    @Override
    public String getUsername() {return username;}

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {return true;}
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
