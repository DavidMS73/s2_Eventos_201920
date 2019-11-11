/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Clase que implementa el recurso "usuarios/{id}/eventos"
 *
 * @author Germán David Martínez Solano
 */
@Produces("application/json")
@Consumes("application/json")
public class UsuarioEventosResource {

    /**
     * Logger del recurso
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioEventosResource.class.getName());

    /**
     * Lógica de la relación
     */
    @Inject
    private UsuarioEventosLogic usuarioEventoLogic;

    /**
     * Lógica del evento
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Asocia un evento existente con un usuario existente
     *
     * @param usuariosId El ID del usuario al cual se le va a asociar el evento
     * @param eventosId El ID del evento que se asocia
     * @return JSON {@link EventoDetailDTO} - El evento asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @POST
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("usuariosId") Long usuariosId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource addEvento: input: usuariosId {0} , eventosId {1}", new Object[]{usuariosId, eventosId});
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(usuarioEventoLogic.addEvento(usuariosId, eventosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource addEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los eventos que existen en un usuario.
     *
     * @param usuariosId El ID del autor del cual se buscan los libros
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en el
     * autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEventos: input: {0}", usuariosId);
        List<EventoDetailDTO> lista = eventosListEntity2DTO(usuarioEventoLogic.getEventos(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el evento con el ID recibido en la URL, relativo a un
     * usuario.
     *
     * @param usuariosId El ID del usuario del cual se busca el evento
     * @param eventosId El ID del evento que se busca
     * @return {@link EventoDetailDTO} - El evento encontrado en el usuario.
     * @throws BusinessLogicException si el evento no está asociado al usuario
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getBook(@PathParam("usuariosId") Long usuariosId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEvento: input: usuariosId {0} , eventosId {1}", new Object[]{usuariosId, eventosId});
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(usuarioEventoLogic.getEvento(usuariosId, eventosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de eventos de un usuario con la lista que se recibe en
     * el cuerpo
     *
     * @param usuariosId El ID del usuario al cual se le va a asociar el evento
     * @param eventos JSONArray {@link EventoDetailDTO} - La lista de eventos
     * que se desea guardar.
     * @return JSONArray {@link EventoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @PUT
    public List<EventoDetailDTO> replaceBooks(@PathParam("usuariosId") Long usuariosId, List<EventoDetailDTO> eventos) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource replaceEventos: input: usuariosId {0} , eventos {1}", new Object[]{usuariosId, eventos});
        for (EventoDetailDTO book : eventos) {
            if (eventoLogic.getEvento(book.getId()) == null) {
                throw new WebApplicationException("El recurso /books/" + book.getId() + " no existe.", 404);
            }
        }
        List<EventoDetailDTO> lista = eventosListEntity2DTO(usuarioEventoLogic.replaceEventos(usuariosId, eventosListDTO2Entity(eventos)));
        LOGGER.log(Level.INFO, "UsuarioEventosResource replaceEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el eventos y el usuario recibidos en la URL.
     *
     * @param usuariosId El ID del usuario al cual se le va a desasociar el
     * evento
     * @param eventosId El ID del evento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void removeBook(@PathParam("usuariosId") Long usuariosId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource deleteEvento: input: usuariosId {0} , eventosId {1}", new Object[]{usuariosId, eventosId});
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /books/" + eventosId + " no existe.", 404);
        }
        usuarioEventoLogic.removeEvento(usuariosId, eventosId);
        LOGGER.info("UsuarioEventosResource deleteEvento: output: void");
    }

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
