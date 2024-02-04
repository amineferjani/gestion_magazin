package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Manager;

public interface ManagerRepos extends JpaRepository<Manager, Long> {
    Boolean existsByEmail(String email);
    Manager findByEmail(String email);
}
