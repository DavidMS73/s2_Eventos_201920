/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PagoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PagoLogic {

    @Inject
    private PagoPersistence persistence;

    public PagoEntity createPago(PagoEntity pago) throws BusinessLogicException {
        if (pago.getFecha() == null) {
            throw new BusinessLogicException("La fecha del pago está vacio");
        }

        pago = persistence.create(pago);
        return pago;
    }
    
    public List<PagoEntity> getPagos() {
        
        List<PagoEntity> lugares = persistence.findAll();
        
        return lugares;
    }
    
    public PagoEntity getPago(Long id) {
        
        PagoEntity en = persistence.find(id);

        
        return en;
    }
    
    
    public PagoEntity updatePago(Long id, PagoEntity entity) {
        
        PagoEntity en = persistence.update(entity);
        
        return en;
    }
    
    
    public void deleteLugar(Long id) throws BusinessLogicException {
        
        persistence.delete(id);
    }

}
