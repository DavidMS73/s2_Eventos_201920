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
public class PseDTO implements Serializable{
    
    private String correo;
    
    public PseDTO()
    {

    }

    public PseDTO(PseEntity entidad) {
        setCorreo(entidad.getCorreo());

    }
     public PseEntity toEntity() {
         PseEntity entidad = new PseEntity();
         entidad.setCorreo(this.getCorreo());

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
    
    
}
