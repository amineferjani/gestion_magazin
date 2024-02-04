package tn.fmass.mg.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.Utils.ImageManager;
import tn.fmass.mg.entities.Categorie;
import tn.fmass.mg.exceptions.ResourceNotFoundException;
import tn.fmass.mg.repositories.CategorieRepos;

import java.io.File;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepos rc;

    public List<Categorie> getAllCategories(){
        return rc.findAll();
    }

    public Categorie getCategorie(long id){
        if(!rc.existsById(id))
            throw new ResourceNotFoundException("Catégorie avec id "+id+" inexistant !");
        return rc.findById(id).get();
    }
    public Categorie addCategorie(Categorie categorie,MultipartFile logo){

        String imgurl=ImageManager.saveImage(logo,"logocategories");
        categorie.setUrl_logo(imgurl);
        return rc.save(categorie);
    }

    public Categorie updateCategorie(Categorie c,long id,MultipartFile logo,String url){
        if(!rc.existsById(id))
            throw new ResourceNotFoundException("Catégorie avec id "+id+" inexistant !");
        c.setId(id);
        if(url!=null){
            String cheminImage = url;
            File fichierImage = new File(cheminImage);
            fichierImage.delete();
        }
        if(logo!=null){
        String imgurl= ImageManager.saveImage(logo,"logocategories");
        c.setUrl_logo(imgurl);}
        return rc.save(c);
    }
    public void deleteCategorie(long id){
        if(!rc.existsById(id))
            throw new ResourceNotFoundException("Catégorie avec id "+id+" inexistant !");
        Categorie categorie=rc.findById(id).get();
        String cheminImage = categorie.getUrl_logo();
        if(cheminImage!=null){
        File fichierImage = new File(cheminImage);
        fichierImage.delete();}
        rc.deleteById(id);
    }

}
