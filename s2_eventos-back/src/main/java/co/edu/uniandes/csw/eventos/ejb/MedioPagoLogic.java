/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MedioPagoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuelillo
 */
@Stateless
public class MedioPagoLogic {
    
    @Inject
    private MedioPagoPersistence persistence;
    
     public MedioPagoEntity createMedioPago(MedioPagoEntity pMedio)throws BusinessLogicException{
        if(pMedio.getNumeroRecibo() == null)
            throw new BusinessLogicException("No existe el número de la tarjeta.");
        else{
            pMedio = persistence.create(pMedio);
            return pMedio;
        }
    }
     
     public MedioPagoEntity createMedioPagoReciboNull(MedioPagoEntity pMedio)throws BusinessLogicException{
        if(pMedio.getNumeroRecibo() == null)
            throw new BusinessLogicException("No existe el número de la tarjeta.");
        else{
            pMedio = persistence.create(pMedio);
            return pMedio;
        }
    }
}
