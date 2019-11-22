/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PseLogic {

    private static final Logger LOGGER = Logger.getLogger(PseLogic.class.getName());

    @Inject
    private PsePersistence persistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public PseEntity createPse(Long usuariosId, PseEntity pse) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de PSE");

        if (pse.getCorreo() == null) {
            throw new BusinessLogicException("El correo está vacio");
        }
        if (!pse.getCorreo().contains("@")) {
            throw new BusinessLogicException("El correo debe existir ");
        }

        UsuarioEntity usuario = usuarioPersistence.find(usuariosId);
        pse.setUsuario(usuario);

        pse = persistence.create(pse);
        LOGGER.log(Level.INFO, "Termina proceso de creación de PSE");

        return pse;
    }

    public List<PseEntity> getPses(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los PSE");
        UsuarioEntity u = usuarioPersistence.find(usuariosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los PSE");
        return u.getPse();
    }

    public PseEntity getPse(Long usuarioId, Long pseId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el PSE con id = {0}", pseId);
        return persistence.find(usuarioId, pseId);
    }

    public PseEntity updatePse(Long usuarioId, PseEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar PSE del usuario con id = {0}", usuarioId);
        UsuarioEntity u = usuarioPersistence.find(usuarioId);
        entity.setUsuario(u);
        persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el PSE con id = {0}", entity.getId());
        return entity;
    }

    public void deletePse(Long usuariosId, Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el PSE con id = {0}", id);
        PseEntity vieja = getPse(usuariosId, id);
        if (vieja == null) {
            throw new BusinessLogicException("El pse con id = " + id + " no existe en la cuenta del usario con id = " + usuariosId);
        }
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el PSE con id = {0}", id);
    }

}
