package com.security.Instituto.service;

import com.security.Instituto.dto.ProfesorDTO;
import com.security.Instituto.model.Profesor;
import com.security.Instituto.repository.IProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfesorService implements IProfesorService{

    @Autowired
    private IProfesorRepository profesorRep;


    @Override
    public ProfesorDTO convertToDTO(Profesor profesor) {
        List<String> cursos = profesor.getCursos().stream()
                .map(curso -> curso.getNombreCurso())
                .collect(Collectors.toList());
        return new ProfesorDTO(profesor.getNombre() , profesor.getApellido(), cursos);
    }

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return profesorRep.save(profesor);
    }

    @Override
    public Optional<Profesor> findProfesorById(Long id) {
        return profesorRep.findById(id);
    }

    @Override
    public void deleteProfesor(Long id) {
        profesorRep.deleteById(id);
    }

    @Override
    public List<Profesor> getAllProfesor() {
        List<Profesor> profesorList = profesorRep.findAll();

        return  profesorList;
    }

    @Override
    public Profesor updateProfesor(Profesor profesor) {
        return profesorRep.save(profesor);
    }
}
