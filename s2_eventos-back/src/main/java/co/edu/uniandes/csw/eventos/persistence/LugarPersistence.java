/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Stateless
public class LugarPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public LugarEntity create(LugarEntity lugarEntity) {
        em.persist(lugarEntity);
        return lugarEntity;
    }

    public LugarEntity find(Long lugarId) {
        return em.find(LugarEntity.class, lugarId);
    }

    public List<LugarEntity> findAll() {
        TypedQuery<LugarEntity> query;
        query = em.createQuery("Select u from LugarEntity u", LugarEntity.class);
        return query.getResultList();
    }

    public LugarEntity update(LugarEntity lugar) {
        return em.merge(lugar);
    }

    public void delete(Long lugarId) {
        LugarEntity lug = em.find(LugarEntity.class, lugarId);
        em.remove(lug);
    }

    public LugarEntity findByName(String name) {
        TypedQuery query = em.createQuery("select u from LugarEntity u where u.salon = :name", LugarEntity.class);
        query = query.setParameter("name", name);
        List<LugarEntity> sameName = query.getResultList();
        LugarEntity result;
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
