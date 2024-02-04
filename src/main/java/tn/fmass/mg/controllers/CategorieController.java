package tn.fmass.mg.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.entities.Categorie;
import tn.fmass.mg.services.CategorieService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CategorieController {

    private final CategorieService cs;
    @GetMapping("categories")
    public ResponseEntity<List<Categorie>> getAllCategories(){
        return ResponseEntity.ok(cs.getAllCategories());
    }
    @GetMapping("categories/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable long id){
        return ResponseEntity.ok(cs.getCategorie(id));
    }
    @PostMapping("categories")
    public ResponseEntity<Categorie> addCategorie(@Valid @RequestPart("categorie") Categorie categorie,
                                                  @RequestPart(value = "logo",
                                                          required = true) MultipartFile logo){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cs.addCategorie(categorie,logo));
    }
    @PutMapping("categories/{id}")
    public ResponseEntity<Categorie> updateCategorie(@RequestPart("categorie") Categorie c,@PathVariable long id,
                                                     @RequestPart(value = "logo",
                                                             required = false) MultipartFile logo,
                                                     @RequestPart(value="url_old_logo",required = false) String url){
        return ResponseEntity.ok(cs.updateCategorie(c,id,logo,url));
    }
    @DeleteMapping("categories/{id}")
    public ResponseEntity<Boolean> deleteCategorie(@PathVariable Long id){
        cs.deleteCategorie(id);
        return ResponseEntity.ok(true);
    }
}
