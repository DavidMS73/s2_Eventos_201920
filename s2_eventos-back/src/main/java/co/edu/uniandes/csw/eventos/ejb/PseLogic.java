/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
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

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private PsePersistence persistence;

    public PseEntity createPse(PseEntity pse) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de PSE");

        if (pse.getCorreo() == null) {
            throw new BusinessLogicException("El correo está vacio");
        }
        if (!pse.getCorreo().contains("@")) {
            throw new BusinessLogicException("El correo debe existir ");
        }

        pse = persistence.create(pse);
        LOGGER.log(Level.INFO, "Termina proceso de creación de PSE");

        return pse;
    }

    public List<PseEntity> getPses() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los PSE");
        List<PseEntity> pses = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los PSE");
        return pses;
    }

    public PseEntity getPse(Long pseId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el PSE con id = {0}", pseId);
        PseEntity pseEntity = persistence.find(pseId);
        if (pseEntity == null) {
            LOGGER.log(Level.SEVERE, "El PSE con el id = {0} no existe", pseId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el PSE con id = {0}", pseId);

        return pseEntity;
    }

    public PseEntity updatePse(Long id, PseEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el PSE con id = {0}", id);
        PseEntity en = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el PSE con id = {0}", entity.getId());
        return en;
    }

    public void deletePse(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el PSE con id = {0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el PSE con id = {0}", id);
    }

}
