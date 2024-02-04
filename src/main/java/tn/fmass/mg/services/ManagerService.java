package tn.fmass.mg.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.fmass.mg.entities.Manager;
import tn.fmass.mg.exceptions.ResourceNotFoundException;
import tn.fmass.mg.repositories.BoutiqueRepos;
import tn.fmass.mg.repositories.CategorieRepos;
import tn.fmass.mg.repositories.ManagerRepos;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepos managerRepos;
    private final BoutiqueRepos boutiqueRepos;
    private final CategorieRepos categorieRepos;
    private final ProductService productService;

    public List<Manager> managerList(){
        return managerRepos.findAll();
    }
    public Manager addManager(Manager manager){
        return managerRepos.save(manager);
    }
    public Manager updateManager(Manager manager, long id){
        if(!managerRepos.existsById(id))
            throw new ResourceNotFoundException("Manager avec id "+id+" inexistant !");
        manager.setId(id);
        return managerRepos.save(manager);
    }
    public void deleteManager(long id){
     if(!managerRepos.existsById(id))
            throw new ResourceNotFoundException("Magager avec id "+id+" inexistant !");
        managerRepos.deleteById(id);
    }

    public HashMap<String, Object> auth(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("login",false);
        if(!managerRepos.existsByEmail(email)){
            return map;
        }

        else{
            Manager mngr=managerRepos.findByEmail(email);
            if(mngr.getPassword().equals(password)){
                map.put("login",true);
                map.put("manager",mngr);
                map.put("boutique",boutiqueRepos.findByManager(mngr));
                map.put("categorieList",categorieRepos.findAll());
                if(boutiqueRepos.findByManager(mngr)!=null)
                    map.put("produitsList",productService.getProduitBoutique(boutiqueRepos.findByManager(mngr).getId()));
                return map;
            }

            return map;
        }
    }
}
