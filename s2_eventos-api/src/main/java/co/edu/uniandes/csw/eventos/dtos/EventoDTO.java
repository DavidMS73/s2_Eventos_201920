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

    private String nombre;

    private String categoria;

    private String descripcion;

    private Date fechaInicio;

    private Date fechaFin;

    private String detallesAdicionales;

    private Integer entradasRestantes;

    private String tipo;

    private Boolean esPago;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }

    public Integer getEntradasRestantes() {
        return entradasRestantes;
    }

    public void setEntradasRestantes(Integer entradasRestantes) {
        this.entradasRestantes = entradasRestantes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getEsPago() {
        return esPago;
    }

    public void setEsPago(Boolean esPago) {
        this.esPago = esPago;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
