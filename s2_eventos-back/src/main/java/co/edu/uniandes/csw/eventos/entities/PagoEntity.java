/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Santiago Tenjo Leal
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
  /*  
    @PodamExclude
    @ManyToOne
    private EventoEntity evento;
    */
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

    /**
     * @return the evento
     *//*
    public EventoEntity getEvento() {
        return evento;
    }*/

    /**
     * @param evento the evento to set
     *//*
    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
    */
    

}
