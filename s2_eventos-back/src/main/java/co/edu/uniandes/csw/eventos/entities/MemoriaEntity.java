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
 * @author Alberic Despres
 */
import java.util.Date;

@Entity
public class MemoriaEntity extends BaseEntity implements Serializable{
    private String lugar;
    
    private Date fecha;
   
    /**
     * @return the lugar
     */
    public String getLugar(){
        return this.lugar;
    }
    
     /**
     * @param newLugar the lugar to set
     */
    public void setLugar(String newLugar){
        this.lugar=newLugar;
    }
    
    /**
     * @return the fecha
     */
    public Date getFecha(){
        return this.fecha;
    }
    
     /**
     * @param newFecha the fecha to set
     */
    public void setFecha(Date newFecha){
        this.fecha=newFecha;
    }
    
    
    
}
