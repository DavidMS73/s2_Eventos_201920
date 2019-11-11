/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad de Usuario y Evento
 * 
 * @author Germán David Martínez Solano
 */
@Stateless
public class UsuarioEventosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     * Asocia un Evento existente a un Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param eventosId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Usuario
     */
    public EventoEntity addEvento(Long usuariosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un evento al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getUsuarios().add(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un evento al usuario con id = {0}", usuariosId);
        return eventoPersistence.find(eventosId);
    }

    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @return Colección de instancias de EventoEntity asociadas a la instancia de
     * Usuario
     */
    public List<EventoEntity> getEventos(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getEventos();
    }

    /**
     * Obtiene una instancia de EventoEntity asociada a una instancia de Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param eventosId Identificador de la instancia de Evento
     * @return La entidad de Evento del usuario
     * @throws BusinessLogicException Si el evento no está asociado al usuario
     */
    public EventoEntity getEvento(Long usuariosId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0} del usuario con id = " + usuariosId, eventosId);
        List<EventoEntity> eventos = usuarioPersistence.find(usuariosId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0} del usuario con id = " + usuariosId, eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al usuario");
    }

    /**
     * Remplaza las instancias de Evento asociadas a una instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param eventos Colección de instancias de EventoEntity a asociar a instancia
     * de Usuario
     * @return Nueva colección de EventoEntity asociada a la instancia de Usuario
     */
    public List<EventoEntity> replaceEventos(Long usuarioId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asociados al usuario con id = {0}", usuarioId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getUsuarios().contains(usuarioEntity)) {
                    evento.getUsuarios().add(usuarioEntity);
                }
            } else {
                evento.getUsuarios().remove(usuarioEntity);
            }
        }
        usuarioEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asociados al usuario con id = {0}", usuarioId);
        return usuarioEntity.getEventos();
    }

    /**
     * Desasocia un Evento existente de un Usuario existente
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param eventosId Identificador de la instancia de Evento
     */
    public void removeEvento(Long usuariosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del usuario con id = {0}", usuariosId);
        UsuarioEntity authorEntity = usuarioPersistence.find(usuariosId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getUsuarios().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del usuario con id = {0}", usuariosId);
    }
}
