/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class EventoPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public EventoEntity create(EventoEntity evento){
        //throw new java.lang.UnsupportedOperationException("Not supported yet.");
        em.persist(evento);
        
        return evento;
    }
}
