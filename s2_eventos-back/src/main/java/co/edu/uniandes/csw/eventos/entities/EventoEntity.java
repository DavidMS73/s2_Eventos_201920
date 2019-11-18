/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.DateStrategy1;
import co.edu.uniandes.csw.eventos.podam.DateStrategy2;
import co.edu.uniandes.csw.eventos.podam.IntegerPositiveStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * Entidad evento
 *
 * @author Germán David Martínez Solano
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable {

    /**
     * Atributo que modela las actividadesEvento del evento
     */
    @PodamExclude
    @OneToMany(mappedBy = "evento", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ActividadEventoEntity> actividadesEvento = new ArrayList<>();

    /**
     * Atributo que modela las actividadesEvento del evento
     */
    @PodamExclude
    @ManyToMany
    private List<LugarEntity> lugares = new ArrayList<>();

    /**
     * Atributo que modela las memorias
     */
    @PodamExclude
    @OneToMany(mappedBy = "evento", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MemoriaEntity> memorias = new ArrayList<>();

    /**
     * Atributo que modela los pago del evento
     */
    @PodamExclude
    @OneToMany(mappedBy = "evento", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PagoEntity> pagos = new ArrayList<>();

    /**
     * Atributo que modela los patrocinios
     */
    @PodamExclude
    @ManyToMany
    private List<PatrocinioEntity> patrocinios = new ArrayList<>();

    /**
     * Atributo que modela los usuarios
     */
    @PodamExclude
    @ManyToMany
    private List<UsuarioEntity> usuarios = new ArrayList<>();

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
    @PodamStrategyValue(DateStrategy1.class)
    private Date fechaInicio;

    /**
     * Atributo que modela la fecha final del evento
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy2.class)
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
     * Atributo que modela el valor del evento
     */
    private Long valor;

    /**
     * Atributo que modela la dirección de la imagen
     */
    private String imagen;

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
     * @return the memorias
     */
    public List<MemoriaEntity> getMemorias() {
        return memorias;
    }

    /**
     * @param memorias the memorias to set
     */
    public void setMemorias(List<MemoriaEntity> memorias) {
        this.memorias = memorias;
    }

    /**
     * @return the patrocinios
     */
    public List<PatrocinioEntity> getPatrocinios() {
        return patrocinios;
    }

    /**
     * @param patrocinios the patrocinios to set
     */
    public void setPatrocinios(List<PatrocinioEntity> patrocinios) {
        this.patrocinios = patrocinios;
    }

    /**
     * @return the pagos
     */
    public List<PagoEntity> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<PagoEntity> pagos) {
        this.pagos = pagos;
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

    @Override
    public boolean equals(Object obj) {
        if (! super.equals(obj)) {
            return false;
        }
        EventoEntity other = (EventoEntity) obj;
        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.actividadesEvento);
        hash = 13 * hash + Objects.hashCode(this.lugares);
        hash = 13 * hash + Objects.hashCode(this.memorias);
        hash = 13 * hash + Objects.hashCode(this.pagos);
        hash = 13 * hash + Objects.hashCode(this.patrocinios);
        hash = 13 * hash + Objects.hashCode(this.usuarios);
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.categoria);
        hash = 13 * hash + Objects.hashCode(this.descripcion);
        hash = 13 * hash + Objects.hashCode(this.fechaInicio);
        hash = 13 * hash + Objects.hashCode(this.fechaFin);
        hash = 13 * hash + Objects.hashCode(this.detallesAdicionales);
        hash = 13 * hash + Objects.hashCode(this.entradasRestantes);
        hash = 13 * hash + Objects.hashCode(this.valor);
        hash = 13 * hash + Objects.hashCode(this.imagen);
        return hash;
    }
}
