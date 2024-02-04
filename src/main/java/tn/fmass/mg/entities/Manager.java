package tn.fmass.mg.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.fmass.mg.models.Etat;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(max=50)
    @NotBlank(message = "Le nom est obligatoire!")
    private String name;
    @Size(max=20)
    @NotBlank(message = "Le mot de passe est obligatoire!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Size(max=50)
    @Column(unique=true)
    @NotBlank(message = "L'email est obligatoire!")
    @Email(message = "Format d'email invalide!")
    private String email;
    @Size(max=20)
    @NotBlank(message = "Le téléphone est obligatoire!")
    @Column(unique=true)
    private String telephone;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date created_date;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date last_updated_date;
}
