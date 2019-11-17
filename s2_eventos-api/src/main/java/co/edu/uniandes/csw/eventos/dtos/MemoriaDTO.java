/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.adapters.DateAdapter;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

    private String imagen;

    /**
     * Atributo que modela la fecha de la memoria
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;

    public MemoriaDTO(MemoriaEntity entidad) {
        if (entidad != null) {
            setId(entidad.getId());
            setLugar(entidad.getLugar());
            setFecha(entidad.getFecha());
            setImagen(entidad.getImagen());
            if (entidad.getEvento() != null) {
                this.setEvento(new EventoDTO(entidad.getEvento()));
            } else {
                this.setEvento(null);
            }
        }
    }

    public MemoriaDTO() {
        // Constructor
    }

    public MemoriaEntity toEntity() {
        MemoriaEntity entidad = new MemoriaEntity();
        entidad.setId(this.getId());
        entidad.setLugar(this.getLugar());
        entidad.setFecha(this.getFecha());
        entidad.setImagen(this.getImagen());
        if (this.getEvento() != null) {
            entidad.setEvento(this.getEvento().toEntity());
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
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
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
