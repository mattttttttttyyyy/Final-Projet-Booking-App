package com.example.demo.controllers;

import com.example.demo.entities.CorporationEntity;
import com.example.demo.services.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corporation")
@CrossOrigin(origins = "http://localhost:4200")

public class CorporationController {
    @Autowired
    CorporationService service;

    @PostMapping("/add")
    public ResponseEntity<Long> addCorporation(@RequestBody CorporationEntity corporation) {
        return new ResponseEntity<>(service.createCorporation(corporation), org.springframework.http.HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<CorporationEntity> getAllCorporations() {
        return service.getAllCorporations();
    }

    @GetMapping("/{id}")
    public CorporationEntity getCorporationById(@PathVariable long id) {
        return service.getCorporationById(id);
    }

    @GetMapping("/byName")
    public int getCorporationByName(@RequestParam(name = "name") String name) {
        return service.getCorporationByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCorporation(@PathVariable long id) {
        service.deleteCorporation(id);
    }

    @PatchMapping("/update/{id}")
    public void updateCorporation(@PathVariable long id, @RequestBody CorporationEntity corporation) {
        service.updateCorporation(id, corporation);
    }

}
