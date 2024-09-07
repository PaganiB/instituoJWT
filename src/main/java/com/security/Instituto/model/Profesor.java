package com.security.Instituto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "profesor")
    private Set<Curso> cursos;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UserSec user;

    public Profesor(){

    }

    public Profesor(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public String getApellido() {
        return apellido;
    }

    public Long getId() {
        return id;
    }

    public UserSec getUser() {
        return user;
    }
}
