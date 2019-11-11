/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PatrocinioDTO Objeto de transferencia de datos de Patrocinios
 *
 * @author Daniel Betancurth Dorado
 */
public class PatrocinioDTO implements Serializable {

    /**
     * Atributo que modela el nombre del patrocinio
     */
    private String empresa;

    /**
     * Atributo que modela el tipo del patrocinio
     */
    private String tipo;

    /**
     * Atributo que modela el identificador del patrocinio
     */
    private Long id;

    /**
     * Constructor a partir de la entidad
     *
     * @param p Entidad del Patrocinio
     */
    public PatrocinioDTO(PatrocinioEntity p) {
        if (p != null) {
            setEmpresa(p.getEmpresa());
            setTipo(p.getTipo());
            setId(p.getId());
        }
    }

    /**
     * Constructor por defecto
     */
    public PatrocinioDTO() {
        //Constructor
    }

    /**
     * Método para tranformar el DTO a una entidad
     *
     * @return Entidad del Patrocinio
     */
    public PatrocinioEntity toEntity() {
        PatrocinioEntity patrocinio = new PatrocinioEntity();
        patrocinio.setEmpresa(this.getEmpresa());
        patrocinio.setTipo(this.getTipo());
        patrocinio.setId(this.getId());
        return patrocinio;
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
