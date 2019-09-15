/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.DateStrategy;
import co.edu.uniandes.csw.eventos.podam.TarjetaStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Estudiante
 */
@Entity
public class TarjetaEntity extends MedioPagoEntity implements Serializable {

    private String tipoTarjeta;
    @PodamStrategyValue(TarjetaStrategy.class)
    private String numeroTarjeta;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date expiracion;

    private Integer cw;

    /**
     * Retorna el tipo de tarjeta.
     *
     * @return tipoTarjeta.
     */
    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    /**
     * Establece el tipo de tarjeta.
     *
     * @param pTipo el tipo de tarjeta que se estblecerá.
     */
    public void setTipoTarjeta(String pTipo) {
        this.tipoTarjeta = pTipo;
    }

    /**
     * Retorna el numero de tarjeta.
     *
     * @return numeroTarjeta.
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Establece un nuevo numero de tarjeta.
     *
     * @param pNumero el nuevo numero de tarjeta que se establecerá.
     */
    public void setNumeroTarjeta(String pNumero) {
        this.numeroTarjeta = pNumero;
    }

    /**
     * Retorna la fecha de expiración.
     *
     * @return expiración.
     */
    public Date getExpiracion() {
        return expiracion;
    }

    /**
     * Establece la fecha de expiración de un tarjeta.
     *
     * @param pExpiracion fecha de expiración que se establecerá.
     */
    public void setExpiracion(Date pExpiracion) {
        this.expiracion = pExpiracion;
    }

    /**
     * Retorna el cw.
     *
     * @return cw.
     */
    public Integer getCw() {
        return cw;
    }

    /**
     * Establece un nuevo cw.
     *
     * @param pCw cw que se establecerá.
     */
    public void setCw(Integer pCw) {
        this.cw = pCw;
    }
}
