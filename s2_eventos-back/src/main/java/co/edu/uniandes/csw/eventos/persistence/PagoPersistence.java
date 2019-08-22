/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Santiago Tenjo Leal
 */
@Stateless
public class PagoPersistence {
    @PersistenceContext(unitName="eventosPU")
    protected EntityManager em;
    public PagoEntity create(PagoEntity pago)
    {
        em.persist(pago);
       // throw new java.lang.UnsupportedOperationException("Not supported yet");
       return pago;
    }
    public EntityManager getEm()
    {
        return em;
    }
}
