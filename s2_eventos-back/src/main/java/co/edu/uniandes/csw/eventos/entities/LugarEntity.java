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
public class LugarEntity extends BaseEntity implements Serializable {

    private Integer capacidadAsistentes;
    private String ubicacionGeografica;

    /**
     * @return the capacidadAsistentes
     */
    public Integer getCapacidadAsistentes() {
        return capacidadAsistentes;
    }

    /**
     * @param capacidadAsistentes the capacidadAsistentes to set
     */
    public void setCapacidadAsistentes(Integer capacidadAsistentes) {
        this.capacidadAsistentes = capacidadAsistentes;
    }

    /**
     * @return the ubicacionGeografica
     */
    public String getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(String ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

}
