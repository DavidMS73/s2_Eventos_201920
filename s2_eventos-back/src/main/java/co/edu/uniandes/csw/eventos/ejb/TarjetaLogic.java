/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.TarjetaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuelillo el pillo
 */
@Stateless
public class TarjetaLogic {
    @Inject
    private TarjetaPersistence persistence;
    
    public TarjetaEntity createTarjeta(TarjetaEntity pTarjeta)throws BusinessLogicException{
        if(pTarjeta.getNumeroTarjeta() == null)
            throw new BusinessLogicException("No existe el número de la tarjeta.");
        else{
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
    
    public TarjetaEntity createTarjetaNumeroInvalido(TarjetaEntity pTarjeta) throws BusinessLogicException{
        if(pTarjeta.getNumeroTarjeta().length() != 16)
            throw new BusinessLogicException("No es un número válido de tarjeta.");
        else{
             pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
    
    public TarjetaEntity createTarjetaTipoNull(TarjetaEntity pTarjeta) throws BusinessLogicException{
        if(pTarjeta.getTipoTarjeta() == null)
            throw new BusinessLogicException("No es un tipo válido de tarjeta.");
        else{
             pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
    
    public TarjetaEntity createTarjetaCWInvalido(TarjetaEntity pTarjeta) throws BusinessLogicException{
        if(pTarjeta.getCw() == null)
            throw new BusinessLogicException("No es un cw válido de tarjeta.");
        else{
             pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
    
    public TarjetaEntity createTarjetaExpiracionInvalida(TarjetaEntity pTarjeta) throws BusinessLogicException{
        if(pTarjeta.getExpiracion() == null)
            throw new BusinessLogicException("No es un cw válido de tarjeta.");
        else{
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
}
