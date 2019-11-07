/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class ActividadEventoDTO implements Serializable {

    private Long id;
    
    /**
     * Atributo que modela el evento asociado a la actividad
     */
    private EventoDTO evento;
    
    /**
     * Atributo que modela el nombre de la actividad del evento
     */
    private String nombre;

    /**
     * Atributo que modela la descripción de la actividad del evento
     */
    private String descripcion;

    /**
     * Atributo que modela la hora de inicio de la actividad del evento
     */
    private String horaInicio;

    /**
     * Atributo que modela la hora de fin de la actividad del evento
     */
    private String horaFin;

    /**
     * Atributo que modela la fecha de la actividad del evento
     */
    private Date fecha;

    public ActividadEventoDTO(ActividadEventoEntity entidad) {
        setId(entidad.getId());
        if(entidad.getEvento() == null){
            setEvento(null);
        }
        else{
            setEvento(new EventoDTO(entidad.getEvento()));
        }
        setNombre(entidad.getNombre());
        setDescripcion(entidad.getDescripcion());
        setHoraInicio(entidad.getHoraInicio());
        setHoraFin(entidad.getHoraFin());
        setFecha(entidad.getFecha());
    }
    
    public ActividadEventoDTO() {
        // Constructor
    }
    
    public ActividadEventoEntity toEntity() {
        ActividadEventoEntity entidad = new ActividadEventoEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setDescripcion(this.getDescripcion());
        entidad.setHoraInicio(this.getHoraInicio());
        entidad.setHoraFin(this.getHoraFin());
        entidad.setFecha(this.getFecha());
        if (this.getEvento() != null) {
            entidad.setEvento(this.getEvento().toEntity());
        }

        return entidad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the horaInicio
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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
