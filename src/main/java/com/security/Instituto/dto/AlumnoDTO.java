package com.security.Instituto.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlumnoDTO {

    private String nombre;
    private String apellido;
    private List<String> cursos;

    public AlumnoDTO(String nombre, String apellido, List<String> cursos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cursos = cursos;
    }

    public AlumnoDTO(){

    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getCursos() {
        return cursos;
    }

    public void setCursos(List<String> cursos) {
        this.cursos = cursos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
