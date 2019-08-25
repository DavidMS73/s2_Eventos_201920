/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Estudiante
 */

@Stateless
public class LugarPersistence {
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public LugarEntity create(LugarEntity lugarEntity){
        em.persist(lugarEntity);
        return lugarEntity;
    }
}
