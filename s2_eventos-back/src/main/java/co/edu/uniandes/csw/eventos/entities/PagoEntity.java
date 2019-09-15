/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Daniel Santiago Tenjo Leal
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    private Date fecha;

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
