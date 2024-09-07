package com.security.Instituto.dto;

import java.util.List;

public class ProfesorDTO {

    private String nombre;
    private String apellido;
    private List<String> cursos;

    public ProfesorDTO(String nombre, String apellido, List<String> cursos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cursos = cursos;
    }

    public ProfesorDTO() {

    }

    public List<String> getCursos() {
        return cursos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCursos(List<String> cursos) {
        this.cursos = cursos;
    }
}
