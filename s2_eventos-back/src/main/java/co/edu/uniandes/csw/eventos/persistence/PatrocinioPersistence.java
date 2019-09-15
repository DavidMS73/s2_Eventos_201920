/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Btancurth Dorado
 */
@Stateless
public class PatrocinioPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public PatrocinioEntity create(PatrocinioEntity patrocinio) {
        em.persist(patrocinio);
        return patrocinio;
    }

    public PatrocinioEntity find(Long patrocinioId) {
        return em.find(PatrocinioEntity.class, patrocinioId);
    }

    public List<PatrocinioEntity> findAll() {
        TypedQuery<PatrocinioEntity> query = em.createQuery("select u from PatrocinioEntity u", PatrocinioEntity.class);
        return query.getResultList();
    }

    public PatrocinioEntity update(PatrocinioEntity patrocinio) {
        return em.merge(patrocinio);
    }

    public void delete(Long patrocinioId) {
        PatrocinioEntity entity = em.find(PatrocinioEntity.class, patrocinioId);
        em.remove(entity);
    }

    public PatrocinioEntity findByName(String name) {
        TypedQuery query = em.createQuery("select u from PatrocinioEntity u where u.empresa = :name", PatrocinioEntity.class);
        query = query.setParameter("name", name);
        List<PatrocinioEntity> sameName = query.getResultList();
        PatrocinioEntity result;
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
