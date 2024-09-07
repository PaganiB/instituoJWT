package com.security.Instituto.controller;

import com.security.Instituto.dto.ProfesorDTO;
import com.security.Instituto.model.Profesor;
import com.security.Instituto.service.IProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profesores")

public class ProfesorController {

    @Autowired
    private IProfesorService profesorServ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProfesor(@RequestBody Profesor profesor){
            try{
                return ResponseEntity.ok(profesorServ.saveProfesor(profesor));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al crear el profesor: " + e.getMessage());
            }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')")
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> getAllProfesor(){

        List<ProfesorDTO> profesorDTOs = new ArrayList<>();
        List<Profesor> profesorList = profesorServ.getAllProfesor();
        ProfesorDTO profesorDTO = new ProfesorDTO();
        for(Profesor profesor : profesorList){
            ProfesorDTO profesorsDTO = profesorServ.convertToDTO(profesor);

            profesorDTOs.add(profesorsDTO);
            profesorDTO = new ProfesorDTO();
        }

        return ResponseEntity.ok(profesorDTOs);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfesor(@PathVariable Long id){
            Optional<Profesor> profesor = profesorServ.findProfesorById(id);

            if (profesor.isPresent()){
                ProfesorDTO profesorDTO = profesorServ.convertToDTO(profesor.get());
                return ResponseEntity.ok(profesorDTO);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
            }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping
    public ResponseEntity<?> editProfesor(@RequestBody Profesor profesor){
        try{
            //se obtiene la lista de todos los profesores existentes
            List<Profesor> profesorList = profesorServ.getAllProfesor();

            //si el profesor se encuentra en la lista se lo modifica
            if (profesorList.contains(profesor)){
                profesorServ.updateProfesor(profesor);
                return ResponseEntity.ok().body("Profesor actualizado.");
            }
            //caso contrario se muestra el siguiente mensaje:
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error actualizando al profesor");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteProfesor(@PathVariable Long id){
        try{
            Optional<Profesor> profesor = profesorServ.findProfesorById(id);
            if (profesor.isPresent()){
                profesorServ.deleteProfesor(id);

                return ResponseEntity.ok().body("Profesor borrado");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error tratando de borrar al profesor");
        }
    }
}
