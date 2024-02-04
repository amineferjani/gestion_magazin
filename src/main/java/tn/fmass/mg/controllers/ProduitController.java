package tn.fmass.mg.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.entities.Product;
import tn.fmass.mg.services.ProductService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ProduitController {

    private final ProductService ps;

    @GetMapping("produits/{id}")
    public ResponseEntity<List<Product>>  getProduitBoutique(@PathVariable long id){
        return ResponseEntity.ok(ps.getProduitBoutique(id));
    }
    @GetMapping("produits/{idb}/{idc}")
    public ResponseEntity<List<Product>>  getProduitBoutiqueCategorie(@PathVariable long idb,@PathVariable long idc){
        return ResponseEntity.ok(ps.getProduitBoutiqueCategorie(idb,idc));
    }
    @GetMapping("produit/{id}")
    public ResponseEntity<Product> getProduit(@PathVariable long id){
        return ResponseEntity.ok(ps.getProduit(id));
    }
    @PostMapping("produits/{idb}/{idc}")
    public ResponseEntity<Product> addProduit(@RequestPart @Valid Product product, @PathVariable long idb,
                                              @PathVariable long idc,
                                              @RequestPart(value = "images", required = false) List<MultipartFile> images){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ps.addProduit(product,idb,idc,images));
    }
    @PutMapping("produits/{idp}/{idb}/{idc}")
    public ResponseEntity<Product> updateProduit(@RequestPart @Valid Product product,@PathVariable long idp,
                                                 @PathVariable long idb,@PathVariable long idc,
                                                 @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                 @RequestPart(value = "deleteimg", required = false) List<String> deleteimg){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ps.updateProduit(product,idp,idb,idc,images,deleteimg));
    }
    @DeleteMapping("produits/{id}")
    public ResponseEntity<Boolean> deleteProduit(@PathVariable Long id){
        ps.deleteProduit(id);
        return ResponseEntity.ok(true);
    }
}
