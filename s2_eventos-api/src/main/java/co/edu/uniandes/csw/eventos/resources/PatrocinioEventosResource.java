/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioEventosLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "patrocinios/{patrociniosId}/eventos".
 *
 * @author Alberic Despres
 */
@Path("patrocinios/{patrociniosId: \\d+}/eventos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class PatrocinioEventosResource {

    private static final Logger LOGGER = Logger.getLogger(PatrocinioEventosResource.class.getName());

    @Inject
    private PatrocinioEventosLogic patrocinioEventosLogic;

    @Inject
    private EventoLogic eventoLogic;

    @Inject
    private PatrocinioLogic patrocinioLogic;

    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     */
    private List<EventoDetailDTO> eventosListEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Busca y devuelve todas los lugares que existen en el evento.
     *
     * @param patrociniosId Identificador del patrocinio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en el
     * patrocinio. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("patrociniosId") Long patrociniosId) {
        LOGGER.log(Level.INFO, "PatrocinioEventosResource getEventos: input: {0}", patrociniosId);
        List<EventoDetailDTO> eventosDTO = eventosListEntity2DTO(patrocinioEventosLogic.getEventos(patrociniosId));
        LOGGER.log(Level.INFO, "EventoResource getLugares: output: {0}", eventosDTO);
        return eventosDTO;
    }

    /**
     * Asocia un evento existente con un patrocinio existente
     *
     * @param patrociniosId El ID del patrocinio al cual se le va a asociar el
     * evento
     * @param eventosId El ID del evento que se asocia
     * @return JSON {@link EventoDetailDTO} - El evento asociado.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento o el
     * patrocinio.
     */
    @POST
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("eventosId") Long eventosId, @PathParam("patrociniosId") Long patrociniosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PatrocinioEventosResource addEvento: input: eventosId {0} , patrociniosId {1}", new Object[]{eventosId, patrociniosId});
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }

        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO eventoDTO = new EventoDetailDTO(patrocinioEventosLogic.addEvento(patrociniosId, eventosId));
        LOGGER.log(Level.INFO, "PatrocinioEventosResource addEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     * Busca y devuelve el evento de un patrocinio.
     *
     * @param patrociniosId Identificador del patrocinio que se esta buscando.
     * @param eventosId Identificador del evento que se esta buscando. Este
     * deben ser cadenas de dígitos.
     * @return JSON {@link EventoDetailDTO} - El Evento encontrada en el
     * patrocinio.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("patrociniosId") Long patrociniosId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PatrocinioEventosResource getEvento: input: {0}", eventosId);
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoEntity eventoEntity = patrocinioEventosLogic.getEvento(patrociniosId, eventosId);
        if (eventoEntity == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO eventoDTO = new EventoDetailDTO(eventoEntity);
        LOGGER.log(Level.INFO, "PatrocinioEventosResource getEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     * Actualiza la lista de eventos de un patrocinio con la lista que se recibe
     * en el cuerpo
     *
     * @param patrociniosId El ID del patrocinio al cual se le va a asociar el
     * evento
     * @param eventos JSONArray {@link EventoDetailDTO} - La lista de eventos
     * que se desea guardar.
     * @return JSONArray {@link EventoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @PUT
    public List<EventoDetailDTO> replaceEventos(@PathParam("patrociniosId") Long patrociniosId, List<EventoDetailDTO> eventos) {
        LOGGER.log(Level.INFO, "PatrocinioEventosResource replaceEventos: input: patrociniosId {0} , eventos {1}", new Object[]{patrociniosId, eventos});
        for (EventoDetailDTO evento : eventos) {
            if (eventoLogic.getEvento(evento.getId()) == null) {
                throw new WebApplicationException("El recurso /eventos/" + evento.getId() + " no existe.", 404);
            }
        }
        List<EventoDetailDTO> lista = eventosListEntity2DTO(patrocinioEventosLogic.replaceEventos(patrociniosId, eventosListDTO2Entity(eventos)));
        LOGGER.log(Level.INFO, "PatrocinioEventosResource replaceEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el evento y el patrocinio recibidos en la URL.
     *
     * @param patrociniosId El ID del patrocinio al cual se le va a desasociar
     * el evento
     * @param eventosId El ID del evento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento o el
     * patrocinio.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void removeEvento(@PathParam("patrociniosId") Long patrociniosId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "PatrocinioEventosResource removeEvento: input: patrociniosId {0} , eventosId {1}", new Object[]{patrociniosId, eventosId});
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        patrocinioEventosLogic.removeEvento(patrociniosId, eventosId);
        LOGGER.info("PatrocinioEventosResource removeEvento: output: void");
    }

    /**
     * Convierte una lista de EventoDetailDTO a una lista de EventoEntity.
     *
     * @param dtos Lista de EventoDetailDTO a convertir.
     * @return Lista de EventoEntity convertida.
     */
    private List<EventoEntity> eventosListDTO2Entity(List<EventoDetailDTO> dtos) {
        List<EventoEntity> list = new ArrayList<>();
        for (EventoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
