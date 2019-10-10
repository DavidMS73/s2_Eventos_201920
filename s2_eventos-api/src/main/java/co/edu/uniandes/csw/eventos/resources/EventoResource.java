/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
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

/**
 *
 * @author Germán David Martínez Solano
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EventoResource {

    @Inject
    private EventoLogic eventoLogic;

    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    @POST
    public EventoDTO createEvento(EventoDTO evento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource createEvento: input: {0}", evento);
        EventoEntity eventoEntity = evento.toEntity();
        eventoEntity = eventoLogic.createEvento(eventoEntity);
        EventoDTO nuevoEventoDTO = new EventoDTO(eventoEntity);
        LOGGER.log(Level.INFO, "EventoResource createEvento: output: {0}", nuevoEventoDTO);
        return nuevoEventoDTO;
    }

    private List<EventoDetailDTO> listEntity2DetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

    @GET
    public List<EventoDetailDTO> getEventos() {
        LOGGER.info("EventoResource getEventos: input: void");
        List<EventoDetailDTO> listaEventos = listEntity2DetailDTO(eventoLogic.getEventos());
        LOGGER.log(Level.INFO, "EventoResource getEventos: output: {0}", listaEventos);
        return listaEventos;
    }

    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("eventosId") Long eventosId) {
        EventoEntity entidad = eventoLogic.getEvento(eventosId);
        if (entidad == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        return new EventoDetailDTO(entidad);
    }

    @PUT
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("eventosId") Long eventosId, EventoDetailDTO evento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource updateEvento: input: id: {0} , evento: {1}", new Object[]{eventosId, evento});
        evento.setId(eventosId);
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(eventoLogic.updateEvento(eventosId, evento.toEntity()));
        LOGGER.log(Level.INFO, "EventoResource updateEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{eventosId: \\d+}")
    public void deleteBook(@PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource deleteEvento: input: {0}", eventosId);
        EventoEntity entity = eventoLogic.getEvento(eventosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        eventoLogic.deleteEvento(eventosId);
        LOGGER.info("EventoResource deleteEvento: output: void");
    }
}
