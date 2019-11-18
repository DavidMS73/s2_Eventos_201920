/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.util.List;
import java.util.logging.Level;
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
public class LugarPersistence {
    
    /**
     * El logger de la persistencia
     */
    private static final Logger LOGGER = Logger.getLogger(LugarPersistence.class.getName());

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public LugarEntity create(LugarEntity lugarEntity) {
        LOGGER.log(Level.INFO, "Creando un lugar nuevo");
        em.persist(lugarEntity);
        LOGGER.log(Level.INFO, "Lugar creado");
        return lugarEntity;
    }

    /**
     * Busca si hay algun lugar con el id que se envìa de argumento
     * 
     * @param lugarId
     * @return un lugar
     */
    public LugarEntity find(Long lugarId) {
        LOGGER.log(Level.INFO, "Consultando el lugar con id={0}", lugarId);
        return em.find(LugarEntity.class, lugarId);
    }
    /**
    * Devuelve todos los lugares en la base de datos
    * 
    * @return lista con todos los lugares que encuentre en la base de datos
    */
    public List<LugarEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los eventos");
        Query q = em.createQuery("select u from LugarEntity u");
        return q.getResultList();
    }

    /**
     * Actualiza un lugar
     * 
     * @param lugar: lugar que viene con los nuevos cambios
     * @return un lugar con los cambios aplicados
     */
    public LugarEntity update(LugarEntity lugar) 
    {
        LOGGER.log(Level.INFO, "Actualizando el evento con id={0}", lugar.getId());
        return em.merge(lugar);
    }

    /**
     * Borra un lugar de la base de datos recibiendo como parametro el id de dicho lugar
     * 
     * @param lugarId 
     */
    public void delete(Long lugarId) 
    {
        LOGGER.log(Level.INFO, "Borrando el lugar con id={0}", lugarId);
        LugarEntity lug = em.find(LugarEntity.class, lugarId);
        em.remove(lug);
    }

    /**
     * Busca si hay algùn lugar con el nombre que se envia por paràmetro
     * 
     * @param name
     * @return null si no existe ningun lugar con el nombre del parametro. Si
     * existe alguno devuelve el primero
     */
    public LugarEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando lugares por nombre {0}", name);
        TypedQuery query = em.createQuery("select u from LugarEntity u where u.nombre = :name", LugarEntity.class);
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
