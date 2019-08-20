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
import java.util.Date;
public class Memoria extends BaseEntity implements Serializable{
    private String lugar;
    private Date fecha;
    
    public String getLugar(){
        return this.lugar;
    };
    public void setLugar(String newLugar){
        this.lugar=newLugar;
    }
    
    public Date getFecha(){
        return this.fecha;
    }
    public void setFecha(Date newFecha){
        this.fecha=newFecha;
    }
    
    
}
