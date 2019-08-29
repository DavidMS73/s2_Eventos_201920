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
 * @author Gabriel Jose Gonzalez Pereira
 */

@Entity
public class MultimediaEntity extends BaseEntity implements Serializable
{
    private String url;
    private String nombre;
    private String tipo;
    
    public MultimediaEntity ()
    {
        
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String pUrl)
    {
        url = pUrl;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String pNombre)
    {
        nombre = pNombre;
    }
    
    public String getTipo()
    {
        return tipo;
    }
    
    public void setTipo(String pTipo)
    {
        tipo = pTipo;
    }
    
}
