/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Tenjo
 */
public class PseDTO implements Serializable {

    private String correo;

    private Long id;

    private UsuarioDTO usuario;

    public PseDTO(PseEntity entidad) {
        if (entidad != null) {
            setId(entidad.getId());
            setCorreo(entidad.getCorreo());
            if (entidad.getUsuario() != null) {
                this.usuario = (new UsuarioDTO(entidad.getUsuario()));
            } else {
                this.usuario = null;
            }
        }
    }

    public PseDTO() {
        // Constructor
    }

    public PseEntity toEntity() {
        PseEntity entidad = new PseEntity();
        entidad.setCorreo(this.getCorreo());
        entidad.setId(this.getId());
        if (this.getUsuario() != null) {
            entidad.setUsuario(this.getUsuario().toEntity());
        }

        return entidad;
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

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

}
