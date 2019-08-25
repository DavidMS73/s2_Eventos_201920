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
 * @author Estudiante
 */
@Entity
public class AgendaEventoEntity extends BaseEntity implements Serializable {

    private Long identificacion;

    private Integer subdivisiones;

    /**
     * @return the identificacion
     */
    public Long getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the subdivisiones
     */
    public Integer getSubdivisiones() {
        return subdivisiones;
    }

    /**
     * @param subdivisiones the subdivisiones to set
     */
    public void setSubdivisiones(Integer subdivisiones) {
        this.subdivisiones = subdivisiones;
    }
}
