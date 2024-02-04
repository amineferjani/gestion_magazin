package tn.fmass.mg.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Categorie {
    @Id
    @GeneratedValue
    private long id;
    @Size(max=50)
    @NotBlank(message = "Le titre est obligatoire!")
    private String titre;
    private String description;
   // @NotBlank(message = "Le titre est obligatoire!")
    private String url_logo;
    @JsonIgnore
    @OneToMany(mappedBy = "categorie")
    private List<Product> produitList;
}
