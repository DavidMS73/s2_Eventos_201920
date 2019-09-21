/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.ActividadEventoDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoActividadesEventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

/**
 * Clase que implementa el recurso "eventos/{id}/actividadesEvento".
 * 
 * @author Albéric Despres 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoActividadesEventoResource {
    
    private static final Logger LOGGER = Logger.getLogger(EventoActividadesEventoResource.class.getName());
   
    @Inject
    private EventoActividadesEventoLogic eventoActividadesLogic;
    
    
    /**
     * Convierte una lista de ActividadEventoEntity a una lista de ActividadEventoDTO.
     *
     * @param entityList Lista de ActividadEventoEntity a convertir.
     * @return Lista de ActividadEventoDTO convertida.
     */
    private List<ActividadEventoDTO> actividadesEntityToDTO(List<ActividadEventoEntity> entityList) {
        List<ActividadEventoDTO> list = new ArrayList();
        for (ActividadEventoEntity entity : entityList) {
            list.add(new ActividadEventoDTO(entity));
        }
        return list;
    }
    
    /**
    * Busca y devuelve todas las actividadesEventos que existen en el evento.
    *
    * @param eventosId Identificador del evento que se esta buscando.
    * Este debe ser una cadena de dígitos.
    * @return JSONArray {@link ActividadEventoDTO} - Las actividadesEvento encontrados en el
    * evento. Si no hay ninguno retorna una lista vacía.
    */
    @GET
    public List<ActividadEventoDTO> getBooks(@PathParam("eventosId") Long eventosId) {
      LOGGER.log(Level.INFO, "EventoResource getActividades: input: {0}", eventosId);
      List<ActividadEventoDTO> actividadesDTO = actividadesEntityToDTO(eventoActividadesLogic.getActividades(eventosId));
      LOGGER.log(Level.INFO, "EventoResource getActividades: output: {0}", actividadesDTO);
      return actividadesDTO;
    }
}
