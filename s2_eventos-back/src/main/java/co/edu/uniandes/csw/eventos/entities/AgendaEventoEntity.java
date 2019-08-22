/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;



/**
 *
 * @author Estudiante
 */
@Entity
public class AgendaEventoEntity extends BaseEntity implements Serializable{
    private Long identificacion;
    
   private Integer subdivisiones;
    
    public Long getIdentificacion(){
        return identificacion;
    }
    
    public Integer getSubdiviones(){
        return subdivisiones;
    }
    
    public void setIdentificacion(Long pId){
        this.identificacion = pId;
    }
    
    public void setSubdivisiones(Integer pSub){
        this.subdivisiones = pSub;
    }
}
