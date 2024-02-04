package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Categorie;

public interface CategorieRepos extends JpaRepository<Categorie, Long> {
}
