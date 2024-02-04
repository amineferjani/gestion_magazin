package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Boutique;
import tn.fmass.mg.entities.Categorie;
import tn.fmass.mg.entities.Product;

import java.util.List;

public interface ProductRepos extends JpaRepository<Product, Long> {
    List<Product> findProduitByBoutique(Boutique b);
    List<Product> findProductByBoutiqueAndCategorie(Boutique b, Categorie c);
}
