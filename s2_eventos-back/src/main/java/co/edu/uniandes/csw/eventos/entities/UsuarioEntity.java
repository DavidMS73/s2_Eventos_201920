/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.CorreoStrategy;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    private String nombre;

    @PodamStrategyValue(CorreoStrategy.class)
    private String correo;

    private String contrasena;

    private String asiste;

    private String codigoQR;

    private String empresa;

    @PodamExclude
    @OneToOne
    private EventoEntity evento;

    @PodamExclude
    @ManyToMany
    private List<EventoEntity> eventosInscritos;

    @PodamExclude
    @ManyToMany
    private List<EventoEntity> eventosInvitadosEspeciales;

    public UsuarioEntity() {
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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the asiste
     */
    public String getAsiste() {
        return asiste;
    }

    /**
     * @param asiste the asiste to set
     */
    public void setAsiste(String asiste) {
        this.asiste = asiste;
    }

    /**
     * @return the codigoQR
     */
    public String getCodigoQR() {
        return codigoQR;
    }

    /**
     * @param codigoQR the codigoQR to set
     */
    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the eventoResponsable
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * @param eventoResponsable the eventoResponsable to set
     */
    public void setEvento(EventoEntity eventoResponsable) {
        this.evento = eventoResponsable;
    }

    /**
     * @return the eventosInscritos
     */
    public List<EventoEntity> getEventosInscritos() {
        return eventosInscritos;
    }

    /**
     * @param eventosInscritos the eventosInscritos to set
     */
    public void setEventosInscritos(List<EventoEntity> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }

    /**
     * @return the eventosInvitadosEspeciales
     */
    public List<EventoEntity> getEventosInvitadosEspeciales() {
        return eventosInvitadosEspeciales;
    }

    /**
     * @param eventosInvitadosEspeciales the eventosInvitadosEspeciales to set
     */
    public void setEventosInvitadosEspeciales(List<EventoEntity> eventosInvitadosEspeciales) {
        this.eventosInvitadosEspeciales = eventosInvitadosEspeciales;
    }
}
