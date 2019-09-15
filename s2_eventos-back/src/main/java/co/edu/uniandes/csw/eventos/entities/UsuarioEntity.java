/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import co.edu.uniandes.csw.eventos.podam.CorreoStrategy;
import com.sun.prism.PixelFormat.DataType;
import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    private String nombre;

    @PodamStrategyValue(CorreoStrategy.class)
    private String correo;

    private String contrasena;

    private Boolean asiste;

    private DataType codigoQR;

    private String empresa;
    //  private PagoEntity pago;

    public UsuarioEntity() {
        //Constructor
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the asiste
     */
    public Boolean getAsiste() {
        return asiste;
    }

    /**
     * @param asiste the asiste to set
     */
    public void setAsiste(Boolean asiste) {
        this.asiste = asiste;
    }

    /**
     * @return the codigoQR
     */
    public DataType getCodigoQR() {
        return codigoQR;
    }

    /**
     * @param codigoQR the codigoQR to set
     */
    public void setCodigoQR(DataType codigoQR) {
        this.codigoQR = codigoQR;
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
