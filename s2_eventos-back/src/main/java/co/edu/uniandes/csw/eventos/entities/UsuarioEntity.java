/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    private Long identificador;
    private String nombre;
    private String correo;
    private String contrasena;

    public UsuarioEntity() {
        //Constructor
    }

    /**
     * @return the id
     */
    public Long getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the id to set
     */
    public void setIdentificador(Long identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contrasena) {
        this.contrasena = contrasena;
    }

}
