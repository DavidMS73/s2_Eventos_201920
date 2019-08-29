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
    private String nombre;
    private String bloque;
    private String piso;
    private String salon;

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
    
    /**
     * @return the bloque
     */
    public String getBloque() {
        return bloque;
    }

    /**
     * @param pBloque the bloque to set
     */
    public void setBloque(String pBloque) {
        this.bloque = pBloque;
    }
    
    /**
     * @return the salon
     */
    public String getSalon() {
        return salon;
    }

    /**
     * @param pSalon the salon to set
     */
    public void setSalon(String pSalon) {
        this.salon = pSalon;
    }
    
    /**
     * @return the piso
     */
    public String getPiso() {
        return piso;
    }

    /**
     * @param pPiso the piso to set
     */
    public void setPiso(String pPiso) 
    {
        this.piso = pPiso;
    }

}
