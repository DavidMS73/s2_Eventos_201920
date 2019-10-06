/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class UsuarioPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public UsuarioEntity create(UsuarioEntity usuario) {
        em.persist(usuario);
        return usuario;
    }

    public UsuarioEntity find(Long usuariosId) {
        return em.find(UsuarioEntity.class, usuariosId);
    }

    public List<UsuarioEntity> findAll() {
        Query query = em.createQuery("select u from UsuarioEntity u");
        return query.getResultList();
    }

    public UsuarioEntity update(UsuarioEntity usuario) {
        return em.merge(usuario);
    }

    public void delete(Long usuarioId) {
        UsuarioEntity entity = em.find(UsuarioEntity.class, usuarioId);
        em.remove(entity);
    }

    public UsuarioEntity findByEmail(String name) {
        TypedQuery query = em.createQuery("select u from UsuarioEntity u where u.correo = :name", UsuarioEntity.class);
        query = query.setParameter("name", name);
        List<UsuarioEntity> sameName = query.getResultList();
        UsuarioEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        return result;
    }
}
