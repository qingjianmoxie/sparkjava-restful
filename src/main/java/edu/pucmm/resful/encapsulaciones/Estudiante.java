package edu.pucmm.resful.encapsulaciones;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by vacax on 26/06/17.
 */
@Root
public class Estudiante {

    @Element
    private int matricula;
    @Element
    private String nombre;
    @Element(required = false)
    private String correo;
    @Element(required = false)
    private String carrera;

    public Estudiante() {
    }   
    

    public Estudiante(int matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }

    public Estudiante(int matricula, String nombre, String correo, String carrera) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correo = correo;
        this.carrera = carrera;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
