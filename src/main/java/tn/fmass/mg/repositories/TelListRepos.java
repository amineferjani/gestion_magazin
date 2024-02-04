package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Tel;

public interface TelListRepos extends JpaRepository<Tel, Long> {
}
