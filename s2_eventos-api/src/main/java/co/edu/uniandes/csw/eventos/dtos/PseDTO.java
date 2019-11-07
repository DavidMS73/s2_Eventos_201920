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
 * @author Estudiante
 */
public class PseDTO implements Serializable {

    private String correo;

    private Long id;

    public PseDTO(PseEntity entidad) {
        setCorreo(entidad.getCorreo());
        setId(entidad.getId());
    }

    public PseDTO() {

    }

    public PseEntity toEntity() {
        PseEntity entidad = new PseEntity();
        entidad.setCorreo(this.getCorreo());
        entidad.setId(this.getId());

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

}
