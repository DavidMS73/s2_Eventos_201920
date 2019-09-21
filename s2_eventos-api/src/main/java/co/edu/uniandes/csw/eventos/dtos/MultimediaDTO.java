/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import java.io.Serializable;

/**
 *
 * @author Gabriel Gonzalez
 */
public class MultimediaDTO implements Serializable 
{
    private Long id;
    private String url;
    private String nombre;
    private String tipo;
    
    public MultimediaDTO()
    {
        //Constructor
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MultimediaDTO(MultimediaEntity entity)
    {
        setId(entity.getId());
        setNombre(entity.getNombre());
        setUrl(entity.getUrl());
        setTipo(entity.getTipo());
    }
    
    public MultimediaEntity toEntity()
    {
        MultimediaEntity entity = new MultimediaEntity();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setTipo(this.getTipo());
        entity.setUrl(this.getUrl());
        
        return entity;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
}