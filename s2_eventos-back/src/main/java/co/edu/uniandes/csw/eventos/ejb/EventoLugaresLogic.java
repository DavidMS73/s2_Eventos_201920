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
    private LugarPersistence lugarPersistence;

    @Inject
    private EventoPersistence eventoPersistence;

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
    
     /**
     * Agregar un lugar al evento
     *
     * @param lugaresId El id del lugar a guardar
     * @param eventosId El id del evento en la cual se va a guardar el lugar.
     * @return el lugar creado.
     */
    public LugarEntity addLugar(Long lugaresId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un lugar al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        LugarEntity lugarEntity = lugarPersistence.find(lugaresId);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un lugar al evento con id = {0}", eventosId);
        return lugarEntity;
    }
}