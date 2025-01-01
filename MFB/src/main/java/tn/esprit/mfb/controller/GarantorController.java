package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.Garantor;
import tn.esprit.mfb.serviceInterface.IGarantorService;

@RestController
@RequestMapping("Garantor")
@AllArgsConstructor
@CrossOrigin("*")

public class GarantorController {
    private final IGarantorService garantorService;
    @PostMapping("/create")
    public Garantor create(@RequestBody Garantor garantor){
        return garantorService.add(garantor);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id)
    {
        garantorService.deleteG(id);
    }
    @GetMapping("/read/{id}")
    public Garantor read(@PathVariable Long id){
        return garantorService.retriveG(id);
    }
}

