package com.security.Instituto.controller;

import com.security.Instituto.dto.AlumnoDTO;
import com.security.Instituto.model.Alumno;
import com.security.Instituto.service.IAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    //inyeccion de dependencias de la intefaz IAlumnoService
    @Autowired
    private IAlumnoService alumnServ;

    //metodo POST para crear las entidades Alumno
    //solo los usuarios con rol ADMIN pueden usar este metodo
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAlumno(@RequestBody Alumno alumno){
        try {
            return ResponseEntity.ok(alumnServ.saveAlumno(alumno));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error intentado crear el alumno: " + e.getMessage());
        }

    }

    //metodo GET para leer al alumno según su id
    //los usuarios con rol ADMIN, ESTUDIANTE o PROFESOR puede acceder a este metodo
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ESTUDIANTE') or hasRole('ROLE_PROFESOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlumno(@PathVariable Long id){
        try{
            Optional<Alumno> alumno = alumnServ.findAlumnoById(id);

            if (alumno.isPresent()){
                AlumnoDTO alumnoDTO = alumnServ.convertToDTO(alumno.get());

                return ResponseEntity.ok(alumnoDTO);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error tratando de encontrar al alumno " + e.getMessage());
        }
    }

    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ESTUDIANTE') or hasRole('ROLE_PROFESOR')")
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos(){
        List<Alumno> listaAlumnos = alumnServ.getAllAlumno();
        List<AlumnoDTO> alumnoDTOList = new ArrayList<>();
        AlumnoDTO alumnoDTO = new AlumnoDTO();

        for (Alumno alumno : listaAlumnos){
            AlumnoDTO alumDTO = alumnServ.convertToDTO(alumno);
            alumnoDTOList.add(alumDTO);
            new AlumnoDTO();
        }

        return ResponseEntity.ok(alumnoDTOList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping
    public ResponseEntity<?> editAlumno(@RequestBody Alumno alumno){
        try{
            alumnServ.updateAlumno(alumno);

            return ResponseEntity.ok().body("Estudiante actualizado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error actualizando al estudiante");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumnno(@PathVariable Long id){
        try{
            Optional<Alumno> alumno = alumnServ.findAlumnoById(id);
            if (alumno.isPresent()){
                alumnServ.deleteAlumno(id);

                return ResponseEntity.ok().body("Estudiante borrado");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error tratando de borrar al estudiante");
        }
    }
}
