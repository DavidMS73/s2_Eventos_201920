/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;


import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Samuelillo el pillo.
 */
public class TarjetaDTO implements Serializable{
    private Long id;
    
    private String tipoTarjeta;
  
    private String numeroTarjeta;
    
    private Date expiracion;

    private Integer cw;

    public TarjetaDTO(TarjetaEntity newEntity){
        setId(newEntity.getId());
        setTipoTarjeta(newEntity.getTipoTarjeta());
        setNumeroTarjeta(newEntity.getNumeroTarjeta());
        setExpiracion(newEntity.getExpiracion());
        setCw(newEntity.getCw());
    }
    
    public TarjetaEntity toEntity(){
        TarjetaEntity newEntity = new TarjetaEntity();
        
        newEntity.setId(this.getId());
        newEntity.setNumeroTarjeta(this.getNumeroTarjeta());
        newEntity.setTipoTarjeta(this.getTipoTarjeta());
        newEntity.setExpiracion(this.getExpiracion());
        newEntity.setCw(this.getCw());
        
        return newEntity;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    public Integer getCw() {
        return cw;
    }

    public void setCw(Integer cw) {
        this.cw = cw;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long pId){
        this.id = pId;
    }
    
}
