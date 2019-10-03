/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Betancurth Dorado
 */
public class PatrocinioDTO implements Serializable
{
    private String empresa;
    
    private String tipo;
    
    private long id;
    
    public PatrocinioDTO()
    {
    }
     public PatrocinioDTO(PatrocinioEntity p)
    {
        this.empresa=p.getEmpresa();
        this.tipo=p.getTipo();
        this.id=p.getId();
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }
    public PatrocinioEntity toEntity()
    {
        PatrocinioEntity patrocinio= new PatrocinioEntity();
        patrocinio.setEmpresa(this.getEmpresa());
        patrocinio.setTipo(this.getTipo());
        patrocinio.setId(this.getId());
        return patrocinio;
    }
    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
     
    
}
