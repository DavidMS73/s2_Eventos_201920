/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.LugarDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLugaresLogic;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "eventos/{id}/lugares".
 * 
 * @author Alberic Despres
 */
@Path("eventos/{eventosId: \\d+}/lugares")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class EventoLugaresResource {
    
    private static final Logger LOGGER = Logger.getLogger(EventoLugaresResource.class.getName());
       
    @Inject
    private EventoLugaresLogic eventoLugaresLogic;
    
    
    /**
     * Convierte una lista de LugarEntity a una lista de LugarDTO.
     *
     * @param entityList Lista de LugarEntity a convertir.
     * @return Lista de LugarDTO convertida.
     */
    private List<LugarDTO> lugaresEntityToDTO(List<LugarEntity> entityList) {
        List<LugarDTO> list = new ArrayList();
        for (LugarEntity entity : entityList) {
            list.add(new LugarDTO(entity));
        }
        return list;
    }
    
    /**
    * Busca y devuelve todas los lugares que existen en el evento.
    *
    * @param eventosId Identificador del evento que se esta buscando.
    * Este debe ser una cadena de dígitos.
    * @return JSONArray {@link LugarDTO} - Los lugares encontrados en el
    * evento. Si no hay ninguno retorna una lista vacía.
    */
    @GET
    public List<LugarDTO> getLugares(@PathParam("eventosId") Long eventosId) {
      LOGGER.log(Level.INFO, "EventoLugaresResource getLugares: input: {0}", eventosId);
      List<LugarDTO> lugaresDTO = lugaresEntityToDTO(eventoLugaresLogic.getLugares(eventosId));
      LOGGER.log(Level.INFO, "EventoResource getLugares: output: {0}", lugaresDTO);
      return lugaresDTO;
    }    
}

