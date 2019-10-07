/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Lugar.
 *
 * @author Albéric Despres
 *
 */
@Stateless
public class EventoLugaresLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLugaresLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private LugarPersistence lugarPersistence;

    /**
     * Agregar un lugar al evento
     *
     * @param lugaresId El id del lugar a guardar
     * @param eventosId El id del evento en la cual se va a guardar el lugar.
     * @return el lugar creado.
     */
    public LugarEntity addLugar(Long eventosId, Long lugaresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un lugar al evento con id = {0}", eventosId);
        LugarEntity lugarEntity = lugarPersistence.find(lugaresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getLugares().add(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un lugar al evento con id = {0}", eventosId);
        return lugarPersistence.find(lugaresId);
    }

    /**
     * Retorna todos los lugares asociados a un evento
     *
     * @param eventosId El ID del evento buscada
     * @return La lista de lugares del evento
     */
    public List<LugarEntity> getLugares(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los lugares asociados al evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getLugares();
    }

    public LugarEntity getLugar(Long eventosId, Long lugaresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un lugar del evento con id = {0}", eventosId);
        List<LugarEntity> lugares = eventoPersistence.find(eventosId).getLugares();
        LugarEntity lugarEntity = lugarPersistence.find(lugaresId);
        int index = lugares.indexOf(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un lugar del evento con id = {0}", eventosId);
        if (index >= 0) {
            return lugares.get(index);
        }
        return null;
    }

    public List<LugarEntity> replaceLugares(Long eventosId, List<LugarEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los lugares del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setLugares(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los lugares del evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getLugares();
    }

    public void removeLugar(Long eventosId, Long lugaresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un lugar del evento con id = {0}", eventosId);
        LugarEntity authorEntity = lugarPersistence.find(lugaresId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getLugares().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un lugar del evento con id = {0}", eventosId);
    }
}
