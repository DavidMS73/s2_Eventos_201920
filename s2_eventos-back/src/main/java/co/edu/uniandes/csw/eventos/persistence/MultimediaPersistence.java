/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Stateless
public class MultimediaPersistence {

    private static final Logger LOGGER = Logger.getLogger(MultimediaPersistence.class.getName());
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public MultimediaEntity create(MultimediaEntity multimedia) {
        em.persist(multimedia);
        return multimedia;
    }

    public MultimediaEntity find(Long multimediasId) {
        return em.find(MultimediaEntity.class, multimediasId);
    }

    public List<MultimediaEntity> findAll() {
        Query query = em.createQuery("select u from MultimediaEntity u");
        return query.getResultList();
    }

    public MultimediaEntity update(MultimediaEntity multimedia) {
        return em.merge(multimedia);
    }

    public void delete(Long multimediaId) {
        MultimediaEntity entity = em.find(MultimediaEntity.class, multimediaId);
        em.remove(entity);
    }

    public MultimediaEntity findByName(String name) {
        TypedQuery query = em.createQuery("select u from MultimediaEntity u where u.nombre = :name", MultimediaEntity.class);
        query = query.setParameter("name", name);
        List<MultimediaEntity> sameName = query.getResultList();
        MultimediaEntity result;

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
