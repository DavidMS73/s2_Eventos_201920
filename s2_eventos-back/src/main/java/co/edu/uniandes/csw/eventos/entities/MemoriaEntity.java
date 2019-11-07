/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import javax.persistence.FetchType;

/**
 *
 * @author Alberic Despres
 */
@Entity
public class MemoriaEntity extends BaseEntity implements Serializable {

    private String lugar;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    @PodamExclude
    @ManyToOne
    private EventoEntity evento;

    @PodamExclude
    @OneToOne(mappedBy = "memoria", fetch = FetchType.LAZY)
    private MultimediaEntity multimedia;

    /**
     * @return the lugar
     */
    public String getLugar() {
        return this.lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return this.fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the evento
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(EventoEntity evento) {
        this.evento = evento;

    }

    /**
     * @return the multimedia
     */
    public MultimediaEntity getMultimedia() {
        return multimedia;
    }

    /**
     * @param multimedia the multimedia to set
     */
    public void setMultimedia(MultimediaEntity multimedia) {
        this.multimedia = multimedia;
    }

}
