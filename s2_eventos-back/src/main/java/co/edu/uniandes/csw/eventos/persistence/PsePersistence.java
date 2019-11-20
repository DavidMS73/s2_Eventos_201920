
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Pse. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PsePersistence {
    
     /**
     * El logger de la persistencia
     */
    private static final Logger LOGGER = Logger.getLogger(TarjetaPersistence.class.getName());

    /**
     * Contexto de persistencia
     */
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param pse objeto pse que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PseEntity create(PseEntity pse) {
        LOGGER.log(Level.INFO, "Creando un pse nuevo");
        em.persist(pse);
        LOGGER.log(Level.INFO, "Pse nuevo creado");
        return pse;
    }

    /**
     * Busca si hay algún pse con el id que se envía de argumento, en el usuario con el id asignado
     *
     * @param pseId: id correspondiente al pse buscado.
     * @param usuarioId: id correspondiente al usuario que tiene el pse buscado.
     * @return un evento.
     */
    public PseEntity find(Long usuarioId , Long pseId) {
        LOGGER.log(Level.INFO, "Consultando el pse del usuario con id = {0}", pseId);
        TypedQuery<PseEntity> q = em.createQuery("select p from PseEntity p where(p.usuario.id = :usuarioId) and (p.id = :pseId)", PseEntity.class);
        q.setParameter("usuarioId", usuarioId);
        q.setParameter("pseId", pseId);
        
        List<PseEntity> results = q.getResultList();
        PseEntity pse = null;
        if (results != null && !results.isEmpty()) {
            pse = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el pse del usuario con id = {0}", pseId);
        return pse;
    }

    /**
     * Devuelve todos los pse de la base de datos.
     *
     * @return una lista con todos los pse que encuentre en la base de datos
     */
    public List<PseEntity> findAll() {
        TypedQuery<PseEntity> query = em.createQuery("select u from PseEntity u", PseEntity.class);
        return query.getResultList();
    }

    /**
     * Actualiza un pse.
     *
     * @param pse: el pse que viene con los nuevos cambios.
     * @return un pse con los cambios aplicados.
     */
    public PseEntity update(PseEntity pse) {
        LOGGER.log(Level.INFO, "Actualizando el pse con id = {0}", pse.getId());
        return em.merge(pse);
    }

    /**
     * Borra un pse de la base de datos recibiendo como argumento el id del pse
     *
     * @param pseId: id correspondiente al pse a borrar.
     */
    public void delete(Long pseId) {
        LOGGER.log(Level.INFO, "Borrando el pse con id={0}", pseId);
        PseEntity entity = em.find(PseEntity.class, pseId);
        em.remove(entity);
    }

    /**
     * Busca si hay algún pse con el nombre que se envía de argumento
     *
     * @param name: Nombre del pse que se está buscando
     * @return null si no existe ningún pse con el nombre del argumento. Si
     * existe alguna devuelve la primera.
     */
    public PseEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando el pse con numero = {0}", name);
        
        TypedQuery query = em.createQuery("select u from PseEntity u where u.correo = :name", PseEntity.class);
        query = query.setParameter("name", name);
        List<PseEntity> sameName = query.getResultList();
        PseEntity result;
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
