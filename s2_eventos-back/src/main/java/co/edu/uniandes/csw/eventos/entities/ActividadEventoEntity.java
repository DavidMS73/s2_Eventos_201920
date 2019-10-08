/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Germán David Martínez Solano
 */
@Entity
public class ActividadEventoEntity extends BaseEntity implements Serializable {
    
    /**
     * Atributo que modela los usuarios asociados a la actividad
    */
    @PodamExclude
    @ManyToMany
    @JoinTable(
        name = "usuario_asociated", 
        joinColumns = @JoinColumn(name = "actividadEvento_id"), 
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<UsuarioEntity> usuarios;

    /**
     * Atributo que modela el evento asociado a la actividad
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private EventoEntity evento;

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
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    public ActividadEventoEntity() {
        //Constructor
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
     * @return the usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }
    
    
}
