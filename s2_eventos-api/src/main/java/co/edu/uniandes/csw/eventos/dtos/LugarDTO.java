/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.io.Serializable;

/**
 *
 * @author Alberic Despres
 */
public class LugarDTO implements Serializable {

    private Long id;
    private Integer capacidadAsistentes;
    private String ubicacionGeografica;
    private String nombre;
    private String bloque;
    private String piso;
    private String salon;

    public LugarDTO(LugarEntity entidad) {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setCapacidadAsistentes(entidad.getCapacidadAsistentes());
        setUbicacionGeografica(entidad.getUbicacionGeografica());
        setBloque(entidad.getBloque());
        setPiso(entidad.getPiso());
        setSalon(entidad.getSalon());
    }

    public LugarDTO() {
        // Constructor
    }

    public LugarEntity toEntity() {
        LugarEntity entidad = new LugarEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setCapacidadAsistentes(this.getCapacidadAsistentes());
        entidad.setUbicacionGeografica(this.getUbicacionGeografica());
        entidad.setBloque(this.getBloque());
        entidad.setPiso(this.getPiso());
        entidad.setSalon(this.getSalon());

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
     * @return the capacidadAsistentes
     */
    public Integer getCapacidadAsistentes() {
        return capacidadAsistentes;
    }

    /**
     * @param capacidadAsistentes the capacidadAsistentes to set
     */
    public void setCapacidadAsistentes(Integer capacidadAsistentes) {
        this.capacidadAsistentes = capacidadAsistentes;
    }

    /**
     * @return the ubicacionGeografica
     */
    public String getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(String ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
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
     * @return the bloque
     */
    public String getBloque() {
        return bloque;
    }

    /**
     * @param bloque the bloque to set
     */
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    /**
     * @return the piso
     */
    public String getPiso() {
        return piso;
    }

    /**
     * @param piso the piso to set
     */
    public void setPiso(String piso) {
        this.piso = piso;
    }

    /**
     * @return the salon
     */
    public String getSalon() {
        return salon;
    }

    /**
     * @param salon the salon to set
     */
    public void setSalon(String salon) {
        this.salon = salon;
    }

}
