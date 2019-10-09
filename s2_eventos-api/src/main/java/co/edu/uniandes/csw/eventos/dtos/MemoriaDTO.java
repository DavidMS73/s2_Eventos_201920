/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Albéric Despres
 */
public class MemoriaDTO implements Serializable {

    private Long id;
    /**
     * Atributo que modela el lugar de la memoria
     */
    private String lugar;

    private EventoDTO evento;

    /**
     * Atributo que modela la fecha de la memoria
     */
    private Date fecha;

   

    public MemoriaDTO() {
        // Constructor
    }

    public MemoriaDTO(MemoriaEntity entidad) {
        setId(entidad.getId());
        setLugar(entidad.getLugar());
        setFecha(entidad.getFecha());
        if(entidad.getEvento()!=null){
            this.evento = new EventoDTO(entidad.getEvento());
        }
        else{
            this.evento=null;
        }
                
    }

    public MemoriaEntity toEntity() {
        MemoriaEntity entidad = new MemoriaEntity();
        entidad.setId(this.getId());
        entidad.setLugar(this.getLugar());
        entidad.setFecha(this.getFecha());
        if(this.evento !=null){
           entidad.setEvento(this.evento.toEntity());
        }
        return entidad;
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
    
    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
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
        return fecha;
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
    public EventoDTO getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

}
