/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Germán David Martínez Solano
 */
public class EventoDTO implements Serializable {

    private Long id;
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
    private Date fechaInicio;

    /**
     * Atributo que modela la fecha final del evento
     */
    private Date fechaFin;

    /**
     * Atributo que modela los detalles adicionales del evento
     */
    private String detallesAdicionales;

    /**
     * Atributo que modela las entradas restantes del evento
     */
    private Integer entradasRestantes;

    /**
     * Atributo que modela el tipo de evento
     */
    private String tipo;

    /**
     * Atributo que modela si un evento es pago o no
     */
    private Boolean esPago;

    /**
     * Atributo que modela el valor del evento
     */
    private Long valor;

    public EventoDTO() {
        // Constructor
    }

    public EventoDTO(EventoEntity entidad) {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setCategoria(entidad.getCategoria());
        setDescripcion(entidad.getDescripcion());
        setEsPago(entidad.getEsPago());
        setFechaInicio(entidad.getFechaInicio());
        setFechaFin(entidad.getFechaFin());
        setValor(entidad.getValor());
        setDetallesAdicionales(entidad.getDetallesAdicionales());
        setEntradasRestantes(entidad.getEntradasRestantes());
    }

    public EventoEntity toEntity() {
        EventoEntity entidad = new EventoEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setCategoria(this.getCategoria());
        entidad.setDescripcion(this.getDescripcion());
        entidad.setEsPago(this.getEsPago());
        entidad.setFechaInicio(this.getFechaInicio());
        entidad.setFechaFin(this.getFechaFin());
        entidad.setValor(this.getValor());
        entidad.setDetallesAdicionales(this.getDetallesAdicionales());
        entidad.setEntradasRestantes(this.getEntradasRestantes());

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

}
