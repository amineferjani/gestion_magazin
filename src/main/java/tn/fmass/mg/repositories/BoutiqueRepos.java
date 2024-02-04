package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Boutique;
import tn.fmass.mg.entities.Manager;

public interface BoutiqueRepos extends JpaRepository<Boutique, Long> {
    Boutique findByManager(Manager manager);
}
