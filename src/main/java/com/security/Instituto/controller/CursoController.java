package com.security.Instituto.controller;

import com.security.Instituto.dto.CursoDTO;
import com.security.Instituto.model.Curso;
import com.security.Instituto.model.Profesor;
import com.security.Instituto.service.ICursoService;
import com.security.Instituto.service.IProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private IProfesorService profesorServ;

    @Autowired
    private ICursoService cursoServ;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCurso(@RequestBody Curso curso){
        try{
            cursoServ.saveCurso(curso);
            CursoDTO cursoDTO = cursoServ.convertToDTO(curso);
            return ResponseEntity.ok(cursoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creando el curso: " + e.getMessage());
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')  or hasRole('ROLE_PROFESOR') or hasRole('ROLE_ESTUDIANTE')")
    @GetMapping
    public ResponseEntity<List<CursoDTO>> getAllCurso(){
        List<CursoDTO> cursoDTOS = new ArrayList<>();
        List<Curso> cursoList = cursoServ.getAllCurso();
        CursoDTO cursoDTO = new CursoDTO();

        for (Curso curso : cursoList){
            cursoDTO = cursoServ.convertToDTO(curso);
            cursoDTOS.add(cursoDTO);
            cursoDTO = new CursoDTO();
        }

        return ResponseEntity.ok(cursoDTOS);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')  or hasRole('ROLE_ESTUDIANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCurso(@PathVariable Long id){
        Optional<Curso> curso = cursoServ.findCursoById(id);

        if (curso.isPresent()){
            CursoDTO cursoDTO = cursoServ.convertToDTO(curso.get());
            return ResponseEntity.ok(cursoDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')")
    @PatchMapping
    public ResponseEntity<?> editCurso(@RequestBody Curso curso){
        try{
            // Obtener el profesor asociado al curso
            Optional<Profesor> profesor = profesorServ.findProfesorById(curso.getProfesor().getId());
            if (profesor == null || profesor.get().getUser() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso o Profesor no válido");
            }

            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String user = authentication.getName();

            if (profesor.get().getUser().getUsername().equals(user)){
                cursoServ.updateCurso(curso);

                return ResponseEntity.ok().body("Curso actualizado");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El profesor no corresponde al curso");
            }

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error actualizando el curso");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
        try {
            Optional<Curso> curso = cursoServ.findCursoById(id);

            if (curso.isPresent()) {
                return ResponseEntity.ok().body("Curso borrado");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error intentando borrar el curso");
        }
    }

}
