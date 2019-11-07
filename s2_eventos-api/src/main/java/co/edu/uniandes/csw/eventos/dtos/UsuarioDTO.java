/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.podam.CorreoStrategy;
import java.io.Serializable;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Daniel Betancurth Dorado
 */
public class UsuarioDTO implements Serializable {

    private String nombre;

    @PodamStrategyValue(CorreoStrategy.class)
    private String correo;

    private String contrasena;

    private String asiste;

    private String codigoQR;

    private String empresa;

    private Long id;

    private TarjetaDTO tarjeta;

    public UsuarioDTO(UsuarioEntity usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.empresa = usuario.getEmpresa();
        this.correo = usuario.getCorreo();
        this.contrasena = usuario.getContrasena();
        this.codigoQR = usuario.getCodigoQR();
        this.asiste = usuario.getAsiste();
        if (usuario.getTarjeta() != null) {
            this.tarjeta = new TarjetaDTO(usuario.getTarjeta());
        } else {
            this.tarjeta = null;
        }

    }

    /**
     * Constructor vacio
     */
    public UsuarioDTO() {

    }

    public UsuarioEntity toEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(this.getId());
        usuario.setAsiste(this.getAsiste());
        usuario.setCodigoQR(this.getCodigoQR());
        usuario.setContrasena(this.getContrasena());
        usuario.setCorreo(this.getCorreo());
        usuario.setEmpresa(this.getEmpresa());
        usuario.setNombre(this.getNombre());
        if (this.tarjeta != null) {
            usuario.setTarjeta(this.tarjeta.toEntity());
        }
        return usuario;
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
    public String getAsiste() {
        return asiste;
    }

    /**
     * @param asiste the asiste to set
     */
    public void setAsiste(String asiste) {
        this.asiste = asiste;
    }

    /**
     * @return the codigoQR
     */
    public String getCodigoQR() {
        return codigoQR;
    }

    /**
     * @param codigoQR the codigoQR to set
     */
    public void setCodigoQR(String codigoQR) {
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

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public TarjetaDTO getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaDTO tarjeta) {
        this.tarjeta = tarjeta;
    }
}
