package com.security.Instituto.dto;

import java.util.List;

public class CursoDTO {

    private String nombreCurso;
    private String profesor;
    private List<String> alumnos;

   public CursoDTO (String nombreCurso, String profesor, List<String> alumnos){
        this.nombreCurso = nombreCurso;
        this.profesor = profesor;
        this.alumnos = alumnos;
    }

    public CursoDTO(){

    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public List<String> getAlumnos() {
        return alumnos;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setAlumnos(List<String> alumnos) {
        this.alumnos = alumnos;
    }
}
