/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import javax.persistence.Entity;
/**
 *
 * @author Alberic Despres
 */
@Entity
public class ResponsableEntity extends UsuarioEntity {
    private String codigoUniandes;
    
     /**
     * @return the codigoUniandes
     */
    public String getCodigoUniandes(){
        return this.codigoUniandes;
    };
    
     /**
     * @param newCodigo the codigo to set
     */
    public void setCodigoUniandes(String newCodigo){
        this.codigoUniandes=newCodigo;
    }
    
}
