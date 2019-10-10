/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Entity
public class MultimediaEntity extends BaseEntity implements Serializable {

    private String url;
    private String nombre;
    private String tipo;
    private MemoriaEntity memoria;
    private ActividadEventoEntity actividadEvento;

    public MultimediaEntity() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String pUrl) {
        url = pUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String pNombre) {
        nombre = pNombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String pTipo) {
        tipo = pTipo;
    }

    /**
     * @return the actividadEvento
     */
    public ActividadEventoEntity getActividadEvento() {
        return actividadEvento;
    }

    /**
     * @param actividadEvento the actividadEvento to set
     */
    public void setActividadEvento(ActividadEventoEntity actividadEvento) {
        this.actividadEvento = actividadEvento;
    }

    
    public MemoriaEntity getMemoria()
    {
        return memoria;
    }
    
    public void setMemoria(MemoriaEntity pMemoria)
    {
        this.memoria = pMemoria;
    }
}
