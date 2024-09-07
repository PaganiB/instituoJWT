package com.security.Instituto.service;

import com.security.Instituto.dto.CursoDTO;
import com.security.Instituto.model.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    public Curso saveCurso(Curso curso);
    public Optional<Curso> findCursoById(Long id);
    public void deleteCurso(Long id);
    public List<Curso> getAllCurso();
    public Curso updateCurso(Curso curso);
    public CursoDTO convertToDTO(Curso curso);
}
