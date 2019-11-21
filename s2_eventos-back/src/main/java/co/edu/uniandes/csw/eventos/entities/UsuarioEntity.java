
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.CorreoStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * Entidad usuario
 *
 * @author Daniel Betancurth Dorado
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    /**
     * @return the pse
     */
    public List<PseEntity> getPse() {
        return pse;
    }

    /**
     * @param pse the pse to set
     */
    public void setPse(List<PseEntity> pse) {
        this.pse = pse;
    }

    /**
     * Atributo que modela el nombre del usuario
     */
    private String nombre;

    /**
     * Atributo que modela el correo del usuario
     */
    @PodamStrategyValue(CorreoStrategy.class)
    private String correo;

    /**
     * Atributo que modela la contraseña del usuario
     */
    private String contrasena;

    /**
     * Atributo que modela el códigoQR del usuario
     */
    private String codigoQR;

    /**
     * Atributo que modela el tipo de usuario
     */
    private String tipo;

    /**
     * Atributo que modela la relación Usuario - Tarjetas
     */
    @PodamExclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TarjetaEntity> tarjetas;
    
    
     /**
     * Atributo que modela la relación Usuario - Pses
     */
    @PodamExclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PseEntity> pse;

    /**
     * Atributo que modela la relación Usuarios - Eventos
     */
    @PodamExclude
    @ManyToMany(mappedBy = "usuarios")
    private List<EventoEntity> eventos = new ArrayList<>();

    /**
     * Constructor por defecto
     */
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
     * @return the eventos
     */
    public List<EventoEntity> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }

    /**
     * @return the tarjetas
     */
    public List<TarjetaEntity> getTarjetas() {
        return tarjetas;
    }

    /**
     * @param tarjetas the tarjetas to set
     */
    public void setTarjetas(List<TarjetaEntity> tarjetas) {
        this.tarjetas = tarjetas;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
