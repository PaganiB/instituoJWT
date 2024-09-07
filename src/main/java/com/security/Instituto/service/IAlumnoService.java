package com.security.Instituto.service;

import com.security.Instituto.dto.AlumnoDTO;
import com.security.Instituto.model.Alumno;

import java.util.List;
import java.util.Optional;

public interface IAlumnoService {
    public Alumno saveAlumno(Alumno alumno);
    public Optional<Alumno> findAlumnoById(Long id);
    public void deleteAlumno(Long id);
    public List<Alumno> getAllAlumno();
    public Alumno updateAlumno(Alumno alumno);
    public AlumnoDTO convertToDTO(Alumno alumno);
}
