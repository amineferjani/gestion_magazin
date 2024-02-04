package tn.fmass.mg.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import tn.fmass.mg.models.Stock;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue
    private long id;
    @Size(max=50)
    @NotBlank(message = "Le nom est obligatoire!")
    private String name;
    private String description;
    private String marque;
    private String section;
    private double pourcentage_promo;
    private double price;
    @Enumerated(EnumType.STRING)
    private Etat promo;
    private double new_price;
    @Enumerated(EnumType.STRING)
    private Stock stock;
    @CreatedDate
    private Date created_date;
    @LastModifiedDate
    private Date last_updated_date;
    @OneToMany(mappedBy = "product")
    private List<Picture> pictureList;
    @ManyToOne
    @JsonIgnore
    private Categorie categorie;
    @ManyToOne
    @JsonIgnore
    private Boutique boutique;
}
