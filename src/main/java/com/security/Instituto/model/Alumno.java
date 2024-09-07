package com.security.Instituto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(mappedBy = "alumnos")
    private Set<Curso> cursos;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UserSec user;

    private String nombre;
    private String apellido;

    public String getApellido() {
        return apellido;
    }

    public Long getId() {
        return id;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
