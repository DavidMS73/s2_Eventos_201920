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
 * @author Daniel Santiago Tenjo Leal
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    protected String valor;
    protected String titular;
    protected String codigoConfirmacion;

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * @param titular the titular to set
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * @return the codigoConfirmacion
     */
    public String getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    /**
     * @param codigoConfirmacion the codigoConfirmacion to set
     */
    public void setCodigoConfirmacion(String codigoConfirmacion) {
        this.codigoConfirmacion = codigoConfirmacion;
    }


}
