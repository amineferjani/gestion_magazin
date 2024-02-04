package tn.fmass.mg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fmass.mg.entities.Picture;

public interface PictureRepos extends JpaRepository<Picture, Long> {
}
