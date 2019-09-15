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
 * @author Daniel Santiago Tenjo
 */
@Entity
public class PseEntity extends MedioPagoEntity implements Serializable {

    private String correo;

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param pCorreo the valor to set
     */
    public void setCorreo(String pCorreo) {
        this.correo = pCorreo;
    }

}
