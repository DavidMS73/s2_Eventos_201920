/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MedioPagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuelillo
 */
@Stateless
public class MedioPagoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(MedioPagoLogic.class.getName());


    @Inject
    private MedioPagoPersistence persistence;

     public MedioPagoEntity createMedioPago(MedioPagoEntity pMedio)throws BusinessLogicException{
        if(pMedio.getNumeroRecibo() == null)
            throw new BusinessLogicException("No existe el número del recibo.");
        if(!validateNumero(pMedio.getNumeroRecibo()))
        throw new BusinessLogicException("No existe el número del recibo.");

        else{
            pMedio = persistence.create(pMedio);
            return pMedio;
        }
    }

     public MedioPagoEntity createMedioPagoReciboNull(MedioPagoEntity pMedio)throws BusinessLogicException{
        if(pMedio.getNumeroRecibo() == null)
            throw new BusinessLogicException("No existe el número del recibo");
        else{
            pMedio = persistence.create(pMedio);
            return pMedio;
        }
    }
     
     public MedioPagoEntity updateTarjeta(Long medioId, MedioPagoEntity pMedio) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el medio de pago con id = {0}", medioId);
        if(!validateNumero(pMedio.getNumeroRecibo()))
            throw new BusinessLogicException("El nuevo número de tarjeta es inválido.");
        
        MedioPagoEntity update = persistence.update(pMedio);
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el medio de pago con id = {0}", pMedio.getId());
        return pMedio;
    }
     
    public void deleteMedioPago(Long medioId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el medio de pago con id = {0}", medioId);
        persistence.delete(medioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el medio de pago con id = {0}", medioId);
    }
     
     private boolean validateNumero(String pNumero){
        return !(pNumero == null || pNumero.isEmpty());
    }
     
    public MedioPagoEntity getMedioPago(Long medioId) throws BusinessLogicException{
        MedioPagoEntity result = persistence.find(medioId);
        if(result == null)
            throw new BusinessLogicException("No existe el medio de pagio que se introdujo.");
        
        return result;
    }
    
    public List<MedioPagoEntity> getMedios(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los medios de pago");
        List<MedioPagoEntity> medios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los medios de pago");
        return medios;
    }
}
