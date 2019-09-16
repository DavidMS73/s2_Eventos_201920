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
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    private EventoLogic logica;

    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    @POST
    public EventoDTO createEvento(EventoDTO evento) throws BusinessLogicException {

        EventoEntity eventoEntity = evento.toEntity();
        eventoEntity = logica.createEvento(eventoEntity);
        return new EventoDTO(eventoEntity);
    }

    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("eventosId") Long eventosId) {
        EventoEntity entidad = logica.getEvento(eventosId);
        if (entidad == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        return new EventoDetailDTO(entidad);
    }
}
