/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.DateStrategy;
import co.edu.uniandes.csw.eventos.podam.IntegerPositiveStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Germán David Martínez Solano
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela las actividadesEvento del evento
     */
    @PodamExclude
    @OneToMany(mappedBy = "evento")
    private List<ActividadEventoEntity> actividadesEvento = new ArrayList<ActividadEventoEntity>();
    
    /**
     * Atributo que modela las actividadesEvento del evento
     */
    @PodamExclude
    @OneToMany
    private List<LugarEntity> lugares = new ArrayList<LugarEntity>();
    @OneToOne
    private UsuarioEntity responsable;
    
    /**
     * Atributo que modela el nombre del evento
     */
    private String nombre;

    /**
     * Atributo que modela la categoría del evento
     */
    private String categoria;

    /**
     * Atributo que modela la descripción del evento
     */
    private String descripcion;

    /**
     * Atributo que modela la fecha inicial del evento
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;

    /**
     * Atributo que modela la fecha final del evento
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaFin;

    /**
     * Atributo que modela los detalles adicionales del evento
     */
    private String detallesAdicionales;

    /**
     * Atributo que modela las entradas restantes del evento
     */
    @PodamStrategyValue(IntegerPositiveStrategy.class)
    private Integer entradasRestantes;

    /**
     * Atributo que modela si un evento es pago o no
     */
    private Boolean esPago;

    /**
     * Atributo que modela el valor del evento
     */
    private Long valor;

    /*@PodamExclude
    @OneToMany(mappedBy = "evento")
    private List<ActividadEventoEntity> actividadesEvento = new ArrayList<ActividadEventoEntity>();*/
    public EventoEntity() {
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
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the detallesAdicionales
     */
    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }

    /**
     * @param detallesAdicionales the detallesAdicionales to set
     */
    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

    /**
     * @return the entradasRestantes
     */
    public Integer getEntradasRestantes() {
        return entradasRestantes;
    }

    /**
     * @param entradasRestantes the entradasRestantes to set
     */
    public void setEntradasRestantes(Integer entradasRestantes) {
        this.entradasRestantes = entradasRestantes;
    }

    /**
     * @return the esPago
     */
    public Boolean getEsPago() {
        return esPago;
    }

    /**
     * @param esPago the esPago to set
     */
    public void setEsPago(Boolean esPago) {
        this.esPago = esPago;
    }

    /**
     * @return the valor
     */
    public Long getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Long valor) {
        this.valor = valor;
    }

    /**
     * @return the actividadesEvento
     */
    public List<ActividadEventoEntity> getActividadesEvento() {
        return actividadesEvento;
    }
    /**
     * @param actividadesEvento the actividadesEvento to set
     */
    public void setActividadesEvento(List<ActividadEventoEntity> actividadesEvento) {
        this.actividadesEvento = actividadesEvento;
    }

    /**
     * @return the lugares
     */
    public List<LugarEntity> getLugares() {
        return lugares;
    }

    /**
     * @param lugares the lugares to set
     */
    public void setLugares(List<LugarEntity> lugares) {
        this.lugares = lugares;
    }

    /**
     * @return the responsable
     */
    public UsuarioEntity getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(UsuarioEntity responsable) {
        this.responsable = responsable;
    }
    
    
}
