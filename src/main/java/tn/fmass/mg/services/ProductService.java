package tn.fmass.mg.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.Utils.ImageManager;
import tn.fmass.mg.entities.Boutique;
import tn.fmass.mg.entities.Categorie;
import tn.fmass.mg.entities.Picture;
import tn.fmass.mg.entities.Product;
import tn.fmass.mg.exceptions.ResourceNotFoundException;
import tn.fmass.mg.repositories.BoutiqueRepos;
import tn.fmass.mg.repositories.CategorieRepos;
import tn.fmass.mg.repositories.PictureRepos;
import tn.fmass.mg.repositories.ProductRepos;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepos pr;
    private final BoutiqueRepos br;
    private final PictureRepos pictureRepos;
    private final CategorieRepos cr;

    public Product getProduit(long id){
        if(!pr.existsById(id))
            throw new ResourceNotFoundException("Produit avec id "+id+" inexistant !");
        return pr.findById(id).get();
    }

    public List<Product> getProduitBoutique(long id){
        if(!br.existsById(id))
            throw new ResourceNotFoundException("Boutique avec id "+id+" inexistant !");
        Boutique b=br.findById(id).get();
        return pr.findProduitByBoutique(b);
    }
    public List<Product> getProduitBoutiqueCategorie(long idb,long idc){
        if(!br.existsById(idb))
            throw new ResourceNotFoundException("Boutique avec id "+idb+" inexistant !");
        if(!cr.existsById(idc))
            throw new ResourceNotFoundException("Catégorie avec id "+idc+" inexistant !");
        Boutique b=br.findById(idb).get();
        Categorie c=cr.findById(idc).get();
        return pr.findProductByBoutiqueAndCategorie(b,c);
    }

    public Product addProduit(Product p, long idb, long idc,List<MultipartFile> images){
        Product produit=new Product();
        if(images!=null)
        System.out.println("c'est bon");
        if(!br.existsById(idb))
            throw new ResourceNotFoundException("Boutique avec id "+idb+" inexistant !");
        if(!cr.existsById(idc))
            throw new ResourceNotFoundException("Categorie avec id "+idc+" inexistant !");
        Boutique b=br.findById(idb).get();
        Categorie c=cr.findById(idc).get();
        p.setBoutique(b);
        p.setCategorie(c);
        produit=pr.save(p);
        List<String> imageUrls=null;
        if (images != null && !images.isEmpty()) {
            imageUrls = ImageManager.saveImages(images,idb,"logoproduits");
        }
        if(imageUrls!=null){
           // List<Picture> pictureList=new ArrayList<>();
            for (String urlImage : imageUrls) {
                Picture picture = new Picture();
                picture.setUrl_image(urlImage);
                picture.setProduct(p);
                pictureRepos.save(picture);
               // pictureList.add(picture);
            }
            //p.setPictureList(pictureList);
        }

        return produit;
    }
    public Product updateProduit(Product p,long idp,long idb, long idc,List<MultipartFile> images,List<String> imgList){
        if(!pr.existsById(idp))
            throw new ResourceNotFoundException("Produit avec id "+idp+" inexistant !");
        if(!cr.existsById(idc))
            throw new ResourceNotFoundException("Categorie avec id "+idc+" inexistant !");
        p.setId(idp);
        Categorie c=cr.findById(idc).get();
        p.setCategorie(c);
        if(imgList!=null) {
            for (String image : imgList) {
                File fichierImage = new File(image);
                fichierImage.delete();
            }
        }

        return addProduit(p,idb,idc,images);
    }
    public void deleteProduit(long id){
        if(!pr.existsById(id))
            throw new ResourceNotFoundException("Produit avec id "+id+" inexistant !");
        Product product=pr.findById(id).get();
        List<Picture> pictureList=product.getPictureList();
        for (Picture image : pictureList){
            String cheminImage = image.getUrl_image();
            File fichierImage = new File(cheminImage);
            fichierImage.delete();
        }
        pr.deleteById(id);
    }

}
