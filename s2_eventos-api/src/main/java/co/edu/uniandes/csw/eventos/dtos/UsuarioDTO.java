/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.podam.CorreoStrategy;
import java.io.Serializable;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * UsuarioDTO Objeto de transferencia de datos de Usuarios
 *
 * @author Daniel Betancurth Dorado
 */
public class UsuarioDTO implements Serializable {

    /**
     * Atributo que modela el nombre del usuario
     */
    private String nombre;

    /**
     * Atributo que modela el correo de un usuario
     */
    private String correo;

    /**
     * Atributo que modela la contraseña de un usuario
     */
    private String contrasena;

    /**
     * Atributo que modela si un usuario asiste al evento
     */
    private String asiste;

    private String codigoQR;

    private String empresa;

    private Long id;

    public UsuarioDTO(UsuarioEntity usuario) {
        if (usuario != null) {
            setId(usuario.getId());
            setNombre(usuario.getNombre());
            setEmpresa(usuario.getEmpresa());
            setCorreo(usuario.getCorreo());
            setContrasena(usuario.getContrasena());
            setCodigoQR(usuario.getCodigoQR());
            setAsiste(usuario.getAsiste());
        }
    }

    /**
     * Constructor vacio
     */
    public UsuarioDTO() {
        //Constructor
    }

    public UsuarioEntity toEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(this.getId());
        usuario.setAsiste(this.getAsiste());
        usuario.setCodigoQR(this.getCodigoQR());
        usuario.setContrasena(this.getContrasena());
        usuario.setCorreo(this.getCorreo());
        usuario.setEmpresa(this.getEmpresa());
        usuario.setNombre(this.getNombre());
        return usuario;
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
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the asiste
     */
    public String getAsiste() {
        return asiste;
    }

    /**
     * @param asiste the asiste to set
     */
    public void setAsiste(String asiste) {
        this.asiste = asiste;
    }

    /**
     * @return the codigoQR
     */
    public String getCodigoQR() {
        return codigoQR;
    }

    /**
     * @param codigoQR the codigoQR to set
     */
    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
