/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Stateless
public class LugarLogic {

    private static final Logger LOGGER = Logger.getLogger(LugarLogic.class.getName());

    @Inject
    private LugarPersistence persistence;

    public LugarEntity createLugar(LugarEntity lugar) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se comienza la creacion de lugares");
        if (lugar.getSalon() == null) {
            throw new BusinessLogicException("El nombre del salon esta vacio");
        }

        if (lugar.getBloque() == null) {
            throw new BusinessLogicException("Debe añadir un bloque");
        }

        if (lugar.getPiso() == null) {
            throw new BusinessLogicException("Debe añadir un piso");
        }

        if (lugar.getUbicacionGeografica() == null) {
            throw new BusinessLogicException("Debe añadir una ubicacion geografica");
        }

        if (lugar.getCapacidadAsistentes() == 0) {
            throw new BusinessLogicException("Debe añadir una capacidad mayor a cero");
        }

        lugar = persistence.create(lugar);
        return lugar;
    }

    public List<LugarEntity> getLugares() {
        LOGGER.log(Level.INFO, "Se empieza el proceso de buscar lugares.");
        List<LugarEntity> lugares = persistence.findAll();
        LOGGER.log(Level.INFO, "Se termina el proceso de buscar lugares");
        return lugares;
    }

    public LugarEntity getLugar(Long id) {
        LOGGER.log(Level.INFO, "Se empieza la busqueda de lugar con id = {0}", id);
        LugarEntity en = persistence.find(id);

        if (id == null) {
            LOGGER.log(Level.INFO, "No existe multimedia con id = {0}");
        }

        LOGGER.log(Level.INFO, "Termina el proceso de consultar el lugar con id = {0}");
        return en;
    }

    public LugarEntity updateLugar(Long id, LugarEntity entity) {
        LOGGER.log(Level.INFO, "Se inicia el proceso de actualizar la multimedia con id = {0}", id);
        LugarEntity en = persistence.update(entity);
        LOGGER.log(Level.INFO, "Se termina la actualizacion del lugar con id = {0}", entity.getId());
        return en;
    }

    public void deleteLugar(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se inicia el proceso de eliminar el lugar con id = {0}");
        List<EventoEntity> eventos = getLugar(id).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el lugar con id = " + id + " porque tiene eventos asociados");
        }
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina el proceso de elminar la multimedia con id = {0}");
    }
}
