/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PseLogic {

    @Inject
    private PsePersistence persistence;

    public PseEntity createPse(PseEntity pse) throws BusinessLogicException {
        if (pse.getCorreo() == null) {
            throw new BusinessLogicException("El correo está vacio");
        }
        if (pse.getCorreo().contains("@") == false) {
            throw new BusinessLogicException("El correo debe ser valido");
        }

        pse = persistence.create(pse);
        return pse;
    }
    
    public List<PseEntity> getPses() {
        
        List<PseEntity> pses = persistence.findAll();
        
        return pses;
    }
    
    public PseEntity getPse(Long pseId)
    {
        PseEntity pseEntity = persistence.find(pseId);
        
        return pseEntity;
    }
    
    
    public PseEntity updatePse(Long id, PseEntity entity) {
        
        PseEntity en = persistence.update(entity);
        return en;
    }
    
    
    public void deletePse(Long id) throws BusinessLogicException {
        if(persistence.find(id)==null)
        {
            throw new BusinessLogicException("No se puede borrar el pse porque no existe");
        }
        persistence.delete(id);
       
    }

}
