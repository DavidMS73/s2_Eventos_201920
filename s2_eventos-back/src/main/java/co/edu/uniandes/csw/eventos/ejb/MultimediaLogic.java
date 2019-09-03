/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */

@Stateless
public class MultimediaLogic 
{
    @Inject
    private MultimediaPersistence persistence;
    
    public MultimediaEntity createMultimedia(MultimediaEntity multimedia) throws BusinessLogicException
    {
        if(multimedia.getNombre() == null)
        {
            throw new BusinessLogicException("El nombre de la multimedia esta vacio");
        }
        
        multimedia = persistence.create(multimedia);
        return multimedia;
    }
    
    
}
