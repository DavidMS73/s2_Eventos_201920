/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class EventoUsuariosLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoPatrociniosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public UsuarioEntity addUsuario(Long eventosId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un usuario al evento con id = {0}", eventosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getUsuarios().add(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un usuario al evento con id = {0}", eventosId);
        return usuarioPersistence.find(usuariosId);
    }

    public List<UsuarioEntity> getUsuarios(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los usuarios asociados al evento con id = {0}", usuariosId);
        return eventoPersistence.find(usuariosId).getUsuarios();
    }

    public UsuarioEntity getUsuario(Long eventosId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un usuario del evento con id = {0}", eventosId);
        List<UsuarioEntity> usuarios = eventoPersistence.find(eventosId).getUsuarios();
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        int index = usuarios.indexOf(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un usuario del evento con id = {0}", eventosId);
        if (index >= 0) {
            return usuarios.get(index);
        }
        return null;
    }

    public List<UsuarioEntity> replaceUsuarios(Long eventosId, List<UsuarioEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los usuarios del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setUsuarios(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los usuarios del evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getUsuarios();
    }

    public void removeInscrito(Long eventosId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un usuario del evento con id = {0}", eventosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getUsuarios().remove(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un usuario del evento con id = {0}", eventosId);
    }
}
