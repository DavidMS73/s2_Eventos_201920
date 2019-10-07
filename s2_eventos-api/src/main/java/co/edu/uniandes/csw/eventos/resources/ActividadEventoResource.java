/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.ActividadEventoDTO;
import co.edu.uniandes.csw.eventos.ejb.ActividadEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoActividadesEventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "eventos/{id}/actividadesEvento".
 * 
 * @author Albéric Despres 
 */
@Path("eventos/{eventosId: \\d+}/actividadesEvento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ActividadEventoResource {
    
    private static final Logger LOGGER = Logger.getLogger(ActividadEventoResource.class.getName());
   
    @Inject
    private ActividadEventoLogic actividadEventoLogic;
    
    @Inject
    private EventoActividadesEventoLogic eventoActividadesEventoLogic;
    
    
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
    public List<ActividadEventoDTO> getActividades(@PathParam("eventosId") Long eventosId) {
      LOGGER.log(Level.INFO, "EventoResource getActividades: input: {0}", eventosId);
      List<ActividadEventoDTO> actividadesDTO = actividadesEntityToDTO(actividadEventoLogic.getActividadesEvento(eventosId));
      LOGGER.log(Level.INFO, "EventoResource getActividades: output: {0}", actividadesDTO);
      return actividadesDTO;
    }
    
    /**
    * Busca y devuelve la actividad de un evento.
    *
    * @param eventosId Identificador del evento que se esta buscando.
    * @param actividadesId Identificador de la actividad que se esta buscando.
    * Este deben ser cadenas de dígitos.
    * @return JSON {@link ActividadEventoDTO} - La actividadEvento encontrada en el
    * evento.
    */
    @GET
    @Path("{actividadesId: \\d+}")
    public ActividadEventoDTO getActividad(@PathParam("eventosId") Long eventosId, @PathParam("actividadesId") Long actividadesId) {
        LOGGER.log(Level.INFO, "ActividadEventoResource getActividad: input: {0}", actividadesId);
        ActividadEventoEntity actividadEntity = actividadEventoLogic.getActividadEvento(eventosId, actividadesId);
        if (actividadEntity == null) {
            throw new WebApplicationException("El recurso /actividadesEvento/" + actividadesId + " no existe.", 404);
        }
        ActividadEventoDTO actividadDTO = new ActividadEventoDTO(actividadEntity);
        LOGGER.log(Level.INFO, "ActividadEventoResource getActividad: output: {0}", actividadDTO);
        return actividadDTO;
    }
    
    //solo sirve para la funcion addActividadEvento()
    public ActividadEventoDTO createActividadEvento(@PathParam("eventosId") Long eventosId, ActividadEventoDTO actividadEvento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividadEvento: input: {0}", actividadEvento);
        ActividadEventoEntity actividadEntity = actividadEvento.toEntity();
        actividadEntity = actividadEventoLogic.createActividadEvento(eventosId, actividadEntity);
        ActividadEventoDTO nuevaActividadDTO = new ActividadEventoDTO(actividadEntity);
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividadEvento: output: {0}", nuevaActividadDTO);
        return nuevaActividadDTO;
    }
    
     /**
     * Crea y asocia una actividad con un evento existente
     *
     * @param eventosId El ID del evento al cual se le va a asociar la actividad
     * @param actividadEvento la actividad a crear y asociar al evento
     * @return JSON {@link ActividadEventoDTO} - La actividad asociada.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @POST //HAY QUE ARREGLAR ESTO PORQUE NO LE LLEGA UN ID DE LA ACTIVIDAD SINO LA ACTIVIDAD :)
    public ActividadEventoDTO addActividadEvento(ActividadEventoDTO actividadEvento, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        //se crea la actividad
        LOGGER.log(Level.INFO, "ActividadEventoResource addActividadEvento: input: {0}", actividadEvento);
        ActividadEventoEntity actividadEntity = actividadEvento.toEntity();
        actividadEntity = actividadEventoLogic.createActividadEvento(eventosId, actividadEntity);
        ActividadEventoDTO nuevaActividadDTO = new ActividadEventoDTO(actividadEntity);
        eventoActividadesEventoLogic.addActividad(nuevaActividadDTO.getId(), eventosId);
        LOGGER.log(Level.INFO, "ActividadEventoResource addActividadEvento: output: {0}", nuevaActividadDTO);
        return nuevaActividadDTO;
    }
    
    /**
     * Elimina la conexión entre la actividad y el evento recibidos en la URL y elimina la actividad.
     *
     * @param eventosId El ID del evento al cual se le va a desasociar la actividad
     * @param actividadesId El ID de la actividad que se desasocia
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @DELETE
    @Path("{actividadesId: \\d+}")
    public void removeActividadEvento(@PathParam("eventosId") Long eventosId, @PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoactividadesEventoResource removeActividadEvento: input: eventosId {0} , actividadesId {1}", new Object[]{eventosId, actividadesId});     
        if (actividadEventoLogic.getActividadEvento(eventosId, actividadesId) == null) {
            throw new WebApplicationException("El recurso /actividadesEvento/" + actividadesId + " no existe.", 404);
        }
        
        //se borra la actividad
        actividadEventoLogic.deleteActividadEvento(eventosId, actividadesId);
        
        //se desasocia la actividad del evento
        eventoActividadesEventoLogic.removeActividad(eventosId, actividadesId);
        
        LOGGER.info("EventoactividadesEventoResource removeActividadEvento: output: void");
    }
}
