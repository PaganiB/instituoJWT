package com.security.Instituto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String nombreCurso;

    @ManyToOne
    @JoinColumn(name = "profesor_id" , nullable = false)
    private Profesor profesor;

    @ManyToMany
    @JoinTable(name = "curso_alumno", joinColumns = @JoinColumn(name =  "curso_id"), inverseJoinColumns = @JoinColumn(name = "alumno_id"))
    private Set<Alumno> alumnos;

    public Long getId() {
        return id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

}
