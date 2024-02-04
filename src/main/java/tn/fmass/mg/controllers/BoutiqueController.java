package tn.fmass.mg.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.entities.Boutique;
import tn.fmass.mg.services.BoutiqueService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class BoutiqueController {
    private final BoutiqueService boutiqueService;
    @PostMapping("boutiques/{id}")
    public ResponseEntity<Boutique> addBoutique(@RequestPart @Valid Boutique boutique, @PathVariable Long id,
                                                @RequestPart(value = "logo",
                                                        required = false) MultipartFile logo){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(boutiqueService.addBoutique(boutique,id,logo));
    }
    @GetMapping ("boutiques")
    public ResponseEntity<List<Boutique>> getAllBoutiques(){
        return ResponseEntity.ok(boutiqueService.getAllBoutiques());
    }
    @GetMapping ("boutiques/{id}")
    public ResponseEntity<Boutique> getBoutique(@PathVariable long id){
        return ResponseEntity.ok(boutiqueService.getBoutique(id));
    }
    @PutMapping("boutiques/{idb}/{idm}")
    public ResponseEntity<Boutique> updateBoutique(@RequestPart @Valid Boutique boutique, @PathVariable Long idb,
                                                   @PathVariable Long idm,
                                                   @RequestPart(value = "logo",
                                                           required = false) MultipartFile logo,
                                                   @RequestPart(value="url_old_logo",required = false) String url){
        return ResponseEntity.ok(boutiqueService.updateBoutique(boutique,idb,idm,logo,url));
    }
    @DeleteMapping("boutiques/{id}")
    public ResponseEntity<Boolean> deleteBoutique(@PathVariable Long id){
        boutiqueService.deleteBoutique(id);
        return ResponseEntity.ok(true);
    }
}
