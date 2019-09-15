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
 * @author Daniel Betancurth Dorado
 */
@Entity
public class PatrocinioEntity extends BaseEntity implements Serializable {

    private String empresa;

    public PatrocinioEntity() {
        //Constructor
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
}
