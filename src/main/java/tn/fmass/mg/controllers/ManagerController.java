package tn.fmass.mg.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.fmass.mg.entities.Manager;
import tn.fmass.mg.services.ManagerService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1")
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("managers")
    public ResponseEntity<Manager> addManager(@Valid @RequestBody Manager m){
       return ResponseEntity.status(HttpStatus.CREATED)
                .body(managerService.addManager(m));
    }
    @PutMapping("managers/{id}")
    public ResponseEntity<Manager> updateManager(@Valid @RequestBody Manager m, long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(managerService.updateManager(m,id));
    }
    @GetMapping("managers")
    public ResponseEntity<List<Manager>> getAllManager(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(managerService.managerList());
    }
    @GetMapping("managers/auth")
    public ResponseEntity<HashMap<String, Object>> auth(@RequestParam String email,
                                                        @RequestParam String password){
        return ResponseEntity.ok(managerService.auth(email,password));
    }

}
