/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PagoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Danielito Tenjo
 */
@Stateless
public class PagoLogic {
    
    @Inject
    private PagoPersistence persistence;
    
    public PagoEntity createPago(PagoEntity pago) throws BusinessLogicException
    {
        if(pago.getFecha()==null)
        {
            throw new BusinessLogicException("La fecha del pago está vacio");
        }
        
        pago=persistence.create(pago);
        return pago;
    }
    
    
}
