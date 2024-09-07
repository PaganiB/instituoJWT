package com.security.Instituto.service;

import com.security.Instituto.dto.AlumnoDTO;
import com.security.Instituto.model.Alumno;
import com.security.Instituto.repository.IAlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoService implements IAlumnoService{

    @Autowired
    private IAlumnoRepository alumnoRep;

    @Override
    public AlumnoDTO convertToDTO(Alumno alumno) {
        List<String> cursos = alumno.getCursos().stream()
                .map(curso -> curso.getNombreCurso())
                .collect(Collectors.toList());

        return new AlumnoDTO(alumno.getNombre(), alumno.getApellido(), cursos);
    }

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        return alumnoRep.save(alumno);
    }

    @Override
    public Optional<Alumno> findAlumnoById(Long id) {
        return alumnoRep.findById(id);
    }

    @Override
    public void deleteAlumno(Long id) {
        alumnoRep.deleteById(id);
    }

    @Override
    public List<Alumno> getAllAlumno() {
        return alumnoRep.findAll();
    }

    @Override
    public Alumno updateAlumno(Alumno alumno) {
        return alumnoRep.save(alumno);
    }
}
