/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.adapters.DateAdapter;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * EventoDTO Objeto de transferencia de datos de Eventos
 *
 * @author Germán David Martínez Solano
 */
public class EventoDTO implements Serializable {

    /**
     * Atributo que modela el id del evento
     */
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
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaInicio;

    /**
     * Atributo que modela la fecha final del evento
     */
    @XmlJavaTypeAdapter(DateAdapter.class)
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
     * Atributo que modela si un evento es pago o no
     */
    private Boolean esPago;

    /**
     * Atributo que modela el valor del evento
     */
    private Long valor;

    /**
     * Constructor a partir de la entidad
     *
     * @param entidad Entidad del Evento
     */
    public EventoDTO(EventoEntity entidad) {
        if (entidad != null) {
            this.id = entidad.getId();
            this.nombre = entidad.getNombre();
            this.categoria = entidad.getCategoria();
            this.descripcion = entidad.getDescripcion();
            this.esPago = entidad.getEsPago();
            this.fechaInicio = entidad.getFechaInicio();
            this.fechaFin = entidad.getFechaFin();
            this.valor = entidad.getValor();
            this.detallesAdicionales = entidad.getDetallesAdicionales();
            this.entradasRestantes = entidad.getEntradasRestantes();
        }
    }

    /**
     * Constructor por defecto
     */
    public EventoDTO() {
        // Constructor
    }

    public EventoEntity toEntity() {
        EventoEntity entidad = new EventoEntity();
        entidad.setId(this.id);
        entidad.setNombre(this.nombre);
        entidad.setCategoria(this.categoria);
        entidad.setDescripcion(this.descripcion);
        entidad.setEsPago(this.esPago);
        entidad.setFechaInicio(this.fechaInicio);
        entidad.setFechaFin(this.fechaFin);
        entidad.setValor(this.valor);
        entidad.setDetallesAdicionales(this.detallesAdicionales);
        entidad.setEntradasRestantes(this.entradasRestantes);

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
