/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.TarjetaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Osorio
 */
@Stateless
public class TarjetaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(TarjetaLogic.class.getName());

    @Inject
    private TarjetaPersistence persistence;

    public TarjetaEntity createTarjeta(TarjetaEntity pTarjeta) throws BusinessLogicException {
        if (pTarjeta.getNumeroTarjeta() == null || pTarjeta.getNumeroTarjeta().length() != 16) 
            throw new BusinessLogicException("No existe el número de la tarjeta.");
        if(pTarjeta.getCw() == null)
            throw new BusinessLogicException("No existe el cw de la tarjeta.");
        if(pTarjeta.getTipoTarjeta() == null)
            throw new BusinessLogicException("El tipo de tarjeta no existe.");
        if(pTarjeta.getExpiracion() == null)
            throw new BusinessLogicException("La tarjeta no tiene fecha de expiración");
       
        pTarjeta = persistence.create(pTarjeta);
        return pTarjeta;
    }
    
    
    public TarjetaEntity updateTarjeta(Long tarjetaId, TarjetaEntity pTarjeta) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar tarjeta con id = {0}", tarjetaId);
        if(!validateNumero(pTarjeta.getNumeroTarjeta()))
            throw new BusinessLogicException("El nuevo número de tarjeta es inválido.");
        
        TarjetaEntity update = persistence.update(pTarjeta);
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar tarjeta con id = {0}", pTarjeta.getId());
        return pTarjeta;
    }
    
    
    public void deleteTarjeta(Long tarjetaId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", tarjetaId);
        persistence.delete(tarjetaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", tarjetaId);
    }
    
    public TarjetaEntity getTarjeta(Long tarjetaId) throws BusinessLogicException{
        TarjetaEntity result = persistence.find(tarjetaId);
        if(result == null)
            throw new BusinessLogicException("No existe el medio de pagio que se introdujo.");
        
        return result;
    }

    public TarjetaEntity createTarjetaNumeroInvalido(TarjetaEntity pTarjeta) throws BusinessLogicException {
        if (pTarjeta.getNumeroTarjeta().length() != 16) {
            throw new BusinessLogicException("No es un número válido de tarjeta.");
        } else {
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }

    public TarjetaEntity createTarjetaTipoNull(TarjetaEntity pTarjeta) throws BusinessLogicException {
        if (pTarjeta.getTipoTarjeta() == null) {
            throw new BusinessLogicException("No es un tipo válido de tarjeta.");
        } else {
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }

    public TarjetaEntity createTarjetaCWInvalido(TarjetaEntity pTarjeta) throws BusinessLogicException {
        if (pTarjeta.getCw() == null) {
            throw new BusinessLogicException("No es un cw válido de tarjeta.");
        } else {
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }

    public TarjetaEntity createTarjetaExpiracionInvalida(TarjetaEntity pTarjeta) throws BusinessLogicException {
        if (pTarjeta.getExpiracion() == null) {
            throw new BusinessLogicException("No es un cw válido de tarjeta.");
        } else {
            pTarjeta = persistence.create(pTarjeta);
            return pTarjeta;
        }
    }
    
    public List<TarjetaEntity> getTarjetas(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las tarjetas");
        List<TarjetaEntity> tarjetas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las tarjetas");
        return tarjetas;
    }
    
    
    private boolean validateNumero(String pNumero){
        return !(pNumero == null || pNumero.isEmpty() || !(pNumero.length() == 16));
    }
}
