package com.security.Instituto.service;

import com.security.Instituto.dto.CursoDTO;
import com.security.Instituto.model.Curso;
import com.security.Instituto.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService implements ICursoService{

    @Autowired
    private ICursoRepository cursoRep;

    @Override
    public CursoDTO convertToDTO(Curso curso) {
        List<String> alumnos = curso.getAlumnos().stream()
                .map(alumno -> alumno.getNombre() + " " + alumno.getApellido())
                .collect(Collectors.toList());
        return new CursoDTO(curso.getNombreCurso(), curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellido() , alumnos);
    }

    @Override
    public Curso saveCurso(Curso curso) {
        return cursoRep.save(curso);
    }

    @Override
    public Optional<Curso> findCursoById(Long id) {
        return cursoRep.findById(id);
    }

    @Override
    public void deleteCurso(Long id) {
        cursoRep.deleteById(id);
    }

    @Override
    public List<Curso> getAllCurso() {
        return cursoRep.findAll();
    }

    @Override
    public Curso updateCurso(Curso curso) {
        return cursoRep.save(curso);
    }
}
