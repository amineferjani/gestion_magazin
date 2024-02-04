package tn.fmass.mg.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.fmass.mg.Utils.ImageManager;
import tn.fmass.mg.entities.Boutique;
import tn.fmass.mg.entities.Manager;
import tn.fmass.mg.exceptions.ResourceNotFoundException;
import tn.fmass.mg.repositories.BoutiqueRepos;
import tn.fmass.mg.repositories.ManagerRepos;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoutiqueService {
    private final BoutiqueRepos boutiqueRepo;
    private final ManagerRepos  managerRepo;
public List<Boutique> getAllBoutiques(){

    return boutiqueRepo.findAll();
}
public Boutique getBoutique(long id){
    if(!boutiqueRepo.existsById(id))
        throw new ResourceNotFoundException("Boutique avec id "+id+" inexistant !");
    return boutiqueRepo.findById(id).get();
}
    public Boutique addBoutique(Boutique b, Long id, MultipartFile logo){
        if(!managerRepo.existsById(id))
            throw new ResourceNotFoundException("Manager avec id "+id+" inexistant !");
        Manager manager=managerRepo.findById(id).get();
        b.setManager(manager);
        if(logo!=null){
        String imgurl=ImageManager.saveImage(logo,"logoboutiques");
        b.setLogo(imgurl);}
        else{
            b.setLogo("target\\classes\\static\\images\\logoboutiques\\logo.png");
        }
        return boutiqueRepo.save(b);
    }
    public Boutique updateBoutique(Boutique b,Long idb,Long idm, MultipartFile logo,String url){
        if(!boutiqueRepo.existsById(idb))
            throw new ResourceNotFoundException("Boutique avec id "+idb+" inexistant !");
        b.setId(idb);
        if(!managerRepo.existsById(idm))
            throw new ResourceNotFoundException("Manager avec id "+idm+" inexistant !");
        Manager manager=managerRepo.findById(idm).get();
        b.setManager(manager);
        if(url!=null){
            String cheminImage = url;
            File fichierImage = new File(cheminImage);
            fichierImage.delete();
        }
        if(logo!=null){
            String imgurl= ImageManager.saveImage(logo,"logoboutiques");
            b.setLogo(imgurl);}
    /*    else{
            b.setLogo("target\\classes\\static\\images\\logoboutiques\\logo.png");
        }*/

        return boutiqueRepo.save(b);
    }
    public void deleteBoutique(Long id){
        if(!boutiqueRepo.existsById(id))
            throw new ResourceNotFoundException("Boutique avec id "+id+" inexistant !");
        Boutique boutique=boutiqueRepo.findById(id).get();
        String cheminImage = boutique.getLogo();
        if(cheminImage!=null){
            File fichierImage = new File(cheminImage);
            fichierImage.delete();}
        boutiqueRepo.deleteById(id);
    }

}
