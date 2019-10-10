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

    public UsuarioEntity addInscrito(Long eventosId, Long inscritosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un inscrito al evento con id = {0}", eventosId);
        UsuarioEntity inscritoEntity = usuarioPersistence.find(inscritosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getInscritos().add(inscritoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un inscrito al evento con id = {0}", eventosId);
        return usuarioPersistence.find(inscritosId);
    }

    public UsuarioEntity addInvitadoEspecial(Long eventosId, Long iEspecialId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un inscrito al evento con id = {0}", eventosId);
        UsuarioEntity inscritoEntity = usuarioPersistence.find(iEspecialId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getInscritos().add(inscritoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un inscrito al evento con id = {0}", eventosId);
        return usuarioPersistence.find(iEspecialId);
    }

    public List<UsuarioEntity> getInscritos(Long inscritosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los inscritos asociados al evento con id = {0}", inscritosId);
        return eventoPersistence.find(inscritosId).getInscritos();
    }

    public List<UsuarioEntity> getInvitadosEspeciales(Long invitadosEspecialesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los invitados especiales asociados al evento con id = {0}", invitadosEspecialesId);
        return eventoPersistence.find(invitadosEspecialesId).getInvitadosEspeciales();
    }

    public UsuarioEntity getInscrito(Long eventosId, Long inscritosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        List<UsuarioEntity> inscritos = eventoPersistence.find(eventosId).getInscritos();
        UsuarioEntity usuarioEntity = usuarioPersistence.find(inscritosId);
        int index = inscritos.indexOf(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        if (index >= 0) {
            return inscritos.get(index);
        }
        return null;
    }

    public UsuarioEntity getInvitadoEspecial(Long eventosId, Long iEspecialesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        List<UsuarioEntity> iEspeciales = eventoPersistence.find(eventosId).getInvitadosEspeciales();
        UsuarioEntity usuarioEntity = usuarioPersistence.find(iEspecialesId);
        int index = iEspeciales.indexOf(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        if (index >= 0) {
            return iEspeciales.get(index);
        }
        return null;
    }
    public List<UsuarioEntity> replaceInscritos(Long eventosId, List<UsuarioEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los inscritos del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setInscritos(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los inscritos del evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getInscritos();
    }
    public List<UsuarioEntity> replaceInvitadosEspeciales(Long eventosId, List<UsuarioEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los invitados especiales del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setInvitadosEspeciales(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los invitados especiales del evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getInvitadosEspeciales();
    }
    public void removeInscrito(Long eventosId, Long inscritosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un inscrito del evento con id = {0}", eventosId);
        UsuarioEntity inscritoEntity = usuarioPersistence.find(inscritosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getInscritos().remove(inscritoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un inscrito del evento con id = {0}", eventosId);
    }
    public void removeInvitadoEspecial(Long eventosId, Long iEspecialId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un invitado especial del evento con id = {0}", eventosId);
        UsuarioEntity iEspecialEntity = usuarioPersistence.find(iEspecialId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getInvitadosEspeciales().remove(iEspecialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un invitado especial del evento con id = {0}", eventosId);
    }
}
