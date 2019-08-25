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
@Entity
public class AsistenteEntity extends UsuarioEntity implements Serializable{
    private byte[] codigoQR;
    
     /**
     * @return the codigoQR
     */
    public byte[] getCodigoQR(){
        return this.codigoQR;
    }
    
     /**
     * @param newCodigoQR the codigoQR to set
     */
    public void setCodigoQR(byte[] newCodigoQR){
        this.codigoQR=newCodigoQR;
    }
    
    
}
