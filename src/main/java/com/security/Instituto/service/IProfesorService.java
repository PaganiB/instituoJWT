package com.security.Instituto.service;

import com.security.Instituto.dto.ProfesorDTO;
import com.security.Instituto.model.Profesor;

import java.util.List;
import java.util.Optional;

public interface IProfesorService {

    public Profesor saveProfesor(Profesor profesor);
    public Optional<Profesor> findProfesorById(Long id);
    public void deleteProfesor(Long id);
    public List<Profesor> getAllProfesor();
    public Profesor updateProfesor(Profesor profesor);
    public ProfesorDTO convertToDTO(Profesor profesor);
}
