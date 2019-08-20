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
 * @author Daniel Santiago Tenjo Leeal
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable{
    
    protected String valor;
    protected String titular;
    protected String códigoConfirmación;

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
     * @return the códigoConfirmación
     */
    public String getCódigoConfirmación() {
        return códigoConfirmación;
    }

    /**
     * @param códigoConfirmación the códigoConfirmación to set
     */
    public void setCódigoConfirmación(String códigoConfirmación) {
        this.códigoConfirmación = códigoConfirmación;
    }
    
}
