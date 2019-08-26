/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class PatrocinadorPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public PatrocinadorEntity create(PatrocinadorEntity patrocinador) {
        em.persist(patrocinador);
        return patrocinador;
    }
}