/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class UsuarioPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public UsuarioEntity create(UsuarioEntity usuario)
    {
        em.persist(usuario);
       // throw new java.lang.UnsupportedOperationException("Not supported yet");
       return usuario;
    }
}
