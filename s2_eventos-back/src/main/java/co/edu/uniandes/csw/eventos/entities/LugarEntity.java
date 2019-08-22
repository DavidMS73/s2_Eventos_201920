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
public class LugarEntity extends BaseEntity implements Serializable
{
   private Integer capacidadAsistentes;
   private String ubicacionGeografica;
   
   /**
    * 
    * @return the capacidadAsistentes
    */
   public Integer getCapacidad()
   {
      return capacidadAsistentes; 
   }
   
   /**
    * 
    * @param pCapacidad the capacidadAsistentes to set
    */
   public void setTipo(Integer pCapacidad)
   {
     this.capacidadAsistentes = pCapacidad;
   }
   
   /**
    * 
    * @return the ubicacionGeografica
    */
   public String getUbicacion()
   {
       return ubicacionGeografica;
   }
   
   /**
    * 
    * @param pUbicacion the ubicacionGeografica to set
    */
   public void setUbicacion(String pUbicacion)
   {
       this.ubicacionGeografica = pUbicacion;
   }
    
}
