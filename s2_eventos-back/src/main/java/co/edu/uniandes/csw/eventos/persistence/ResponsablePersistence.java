/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.ResponsableEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class ResponsablePersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public ResponsableEntity create(ResponsableEntity responsable) {
        em.persist(responsable);
        return responsable;
    }
}