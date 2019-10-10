/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import java.util.Date;

/**
 *
 * @author Santiago Tenjo Leal
 */
public class PagoDTO {


    private Date fecha;
    
    private Long id;


    public PagoDTO() {
        // Constructor
    }
    public PagoDTO(PagoEntity entidad) {
        setFecha(entidad.getFecha());
        setId(entidad.getId());

    }
     public PagoEntity toEntity() {
         PagoEntity entidad = new PagoEntity();
         entidad.setFecha(this.getFecha());
         entidad.setId(this.getId());

         return entidad;
     }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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


