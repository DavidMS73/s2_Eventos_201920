/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;

/**
 *
 * @author Daniel Santiago Tenjo Leal
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    
    public PagoEntity()
    {
        
    }

    /**
     * @return the valor
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param pFecha the valor to set
     */
    public void setFecha(Date pFecha) {
        this.fecha = pFecha;
    }

}
