/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoPatrociniosLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
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
 * Clase que implementa el recurso "eventos/{id}/patrocinios"
 *
 * @author Germán David Martínez Solano
 */
@Produces("application/json")
@Consumes("application/json")
public class EventoPatrociniosResource {

    /**
     * Logger del recurso
     */
    private static final Logger LOGGER = Logger.getLogger(EventoPatrociniosResource.class.getName());

    /**
     * Lógica de la relación
     */
    @Inject
    private EventoPatrociniosLogic eventoPatrociniosLogic;

    /**
     * Lógica de patrocinio
     */
    @Inject
    private PatrocinioLogic patrocinioLogic;

    /**
     * Asocia un patrocinio existente con un evento existente
     *
     * @param patrociniosId El ID del patrocinio que se va a asociar
     * @param eventosId El ID del evento al cual se le va a asociar el
     * patrocinio
     * @return JSON {@link PatrocinioDetailDTO} - El usuario asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el patrocinio.
     */
    @POST
    @Path("{patrociniosId: \\d+}")
    public PatrocinioDetailDTO addPatrocinio(@PathParam("eventosId") Long eventosId, @PathParam("patrociniosId") Long patrociniosId) {
        LOGGER.log(Level.INFO, "EventoPatrociniosResource addPatrocinio: input: eventosId {0} , patrociniosId {1}", new Object[]{eventosId, patrociniosId});
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        PatrocinioDetailDTO detailDTO = new PatrocinioDetailDTO(eventoPatrociniosLogic.addPatrocinio(eventosId, patrociniosId));
        LOGGER.log(Level.INFO, "EventoPatrociniosResource addPatrocinio: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los usuarios que existen en un evento.
     *
     * @param eventosId El ID del evento del cual se buscan los patrocinios
     * @return JSONArray {@link PatrocinioDetailDTO} - Los patrocinios
     * encontrados en el evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PatrocinioDetailDTO> getPatrocinios(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "EventoPatrociniosResource getPatrocinios: input: {0}", eventosId);
        List<PatrocinioDetailDTO> lista = patrociniosListEntity2DTO(eventoPatrociniosLogic.getPatrocinios(eventosId));
        LOGGER.log(Level.INFO, "EventoPatrociniosResource getPatrocinios: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el usuario con el ID recibido en la URL, relativo a un
     * evento.
     *
     * @param patrociniosId El ID del patrocinio que se busca
     * @param eventosId El ID del evento del cual se busca el patrocinio
     * @return {@link PatrocinioDetailDTO} - El patrocinio encontrado en el
     * evento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @GET
    @Path("{patrociniosId: \\d+}")
    public PatrocinioDetailDTO getPatrocinio(@PathParam("eventosId") Long eventosId, @PathParam("patrociniosId") Long patrociniosId) {
        LOGGER.log(Level.INFO, "EventoPatrociniosResource getPatrocinio: input: eventosId {0} , patrociniosId {1}", new Object[]{eventosId, patrociniosId});
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        PatrocinioDetailDTO detailDTO = new PatrocinioDetailDTO(eventoPatrociniosLogic.getPatrocinio(eventosId, patrociniosId));
        LOGGER.log(Level.INFO, "EventoPatrociniosResource getPatrocinio: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de patrocinios de un libro con la lista que se recibe
     * en el cuerpo.
     *
     * @param eventosId El ID del evento al cual se le va a asociar la lista de
     * patrocinios
     * @param patrocinios JSONArray {@link PatrocinioDetailDTO} - La lista de
     * patrocinios que se desea guardar.
     * @return JSONArray {@link PatrocinioDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el patrocinio.
     */
    @PUT
    public List<PatrocinioDetailDTO> replacePatrocinios(@PathParam("eventosId") Long eventosId, List<PatrocinioDetailDTO> patrocinios) {
        LOGGER.log(Level.INFO, "EventoPatrociniosResource replacePatrocinios: input: eventosId {0} , usuarios {1}", new Object[]{eventosId, patrocinios});
        for (PatrocinioDetailDTO patrocinio : patrocinios) {
            if (patrocinioLogic.getPatrocinio(patrocinio.getId()) == null) {
                throw new WebApplicationException("El recurso /patrocinios/" + patrocinio.getId() + " no existe.", 404);
            }
        }
        List<PatrocinioDetailDTO> lista = patrociniosListEntity2DTO(eventoPatrociniosLogic.replacePatrocinios(eventosId, patrociniosListDTO2Entity(patrocinios)));
        LOGGER.log(Level.INFO, "EventoPatrociniosResource replacePatrocinios: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el patrocinio y el evento recibidos en la URL.
     *
     * @param eventosId El ID del evento al cual se le va a desasociar el
     * patrocinio
     * @param patrociniosId El ID del patrocinio que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el patrocinio.
     */
    @DELETE
    @Path("{patrociniosId: \\d+}")
    public void removePatrocinio(@PathParam("eventosId") Long eventosId, @PathParam("patrociniosId") Long patrociniosId) {
        LOGGER.log(Level.INFO, "EventoPatrociniosResource removePatrocinio: input: eventosId {0} , patrociniosId {1}", new Object[]{eventosId, patrociniosId});
        if (patrocinioLogic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        eventoPatrociniosLogic.removePatrocinio(eventosId, patrociniosId);
        LOGGER.info("EventoPatrociniosResource removePatrocinio: output: void");
    }

    /**
     * Convierte una lista de PatrocinioEntity a una lista de
     * PatrocinioDetailDTO.
     *
     * @param entityList Lista de PatrocinioEntity a convertir.
     * @return Lista de PatrocinioDetailDTO convertida.
     */
    private List<PatrocinioDetailDTO> patrociniosListEntity2DTO(List<PatrocinioEntity> entityList) {
        List<PatrocinioDetailDTO> list = new ArrayList<>();
        for (PatrocinioEntity entity : entityList) {
            list.add(new PatrocinioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de PatrocinioDetailDTO a una lista de
     * PatrocinioEntity.
     *
     * @param dtos Lista de PatrocinioDetailDTO a convertir.
     * @return Lista de PatrocinioEntity convertida.
     */
    private List<PatrocinioEntity> patrociniosListDTO2Entity(List<PatrocinioDetailDTO> dtos) {
        List<PatrocinioEntity> list = new ArrayList<>();
        for (PatrocinioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
