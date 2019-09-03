/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Germàn David Martìnez Solano
 */

@Stateless
public class EventoLogic {
    
    @Inject
    private EventoPersistence persistence;
    
    public EventoEntity createEvento(EventoEntity evento) throws BusinessLogicException {
        if(evento.getNombre() == null){
            throw new BusinessLogicException("El nombre de evento está vacío");
        }
        
        evento = persistence.create(evento);
        return evento;
    }
}
