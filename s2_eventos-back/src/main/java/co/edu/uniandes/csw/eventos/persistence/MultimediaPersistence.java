/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
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
public class MultimediaPersistence 
{
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public MultimediaEntity create(MultimediaEntity multimedia)
    {
        em.persist(multimedia);
        return multimedia;
    }
    
    public MultimediaEntity find(Long multimediaId)
    {
        return em.find(MultimediaEntity.class, multimediaId);
    }
    
    public List<MultimediaEntity> findAll()
    {
        TypedQuery<MultimediaEntity> query;
        query = em.createQuery("select u from MultimediaEntity u", MultimediaEntity.class);
        return query.getResultList();
    }
    
    public MultimediaEntity update(MultimediaEntity multimedia)
    {
        return em.merge(multimedia);
    }
    
    public void delete(Long multimediaId)
    {
        MultimediaEntity m = em.find(MultimediaEntity.class, multimediaId);
        em.remove(m);
    }
    
    public MultimediaEntity findByName(String name)
    {
        TypedQuery query = em.createQuery("select u from MultimediaEntity u where u.nombre = :name", MultimediaEntity.class);
        query = query.setParameter("name", name);
        List<MultimediaEntity> sameName = query.getResultList();
        MultimediaEntity result;
        
        if(sameName == null)
        {
            result = null;
        }
        
        else if(sameName.isEmpty())
        {
            result = null;
        }
        
        else
        {
            result = sameName.get(0);
        }
        
        return result;
    }
}
