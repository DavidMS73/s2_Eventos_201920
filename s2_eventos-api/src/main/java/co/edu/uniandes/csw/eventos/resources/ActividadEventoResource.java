/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.ActividadEventoDTO;
import co.edu.uniandes.csw.eventos.ejb.ActividadEventoLogic;
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
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "actividadesEvento/".
 *
 * @author Albéric Despres
 */
@Path("actividadesEvento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ActividadEventoResource {

    /**
     * Logger del recurso
     */
    private static final Logger LOGGER = Logger.getLogger(ActividadEventoResource.class.getName());

    /**
     * Lógica de la actividad
     */
    @Inject
    private ActividadEventoLogic actividadEventoLogic;

    /**
     * Crea una nueva actividad con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto idéntico con un id auto-generado por
     * la base de datos.
     *
     * @param actividad {@link ActividadEventoDTO} - La actividad que se desea
     * guardar.
     * @return JSON {@link ActividadEventoDTO} - La actividad guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica.
     */
    @POST
    public ActividadEventoDTO createActividad(ActividadEventoDTO actividad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividad: input: {0}", actividad);
        ActividadEventoDTO nuevaActividadDTO = new ActividadEventoDTO(actividadEventoLogic.createActividadEvento(actividad.toEntity()));
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividad: output: {0}", nuevaActividadDTO);
        return nuevaActividadDTO;
    }

    /**
     * Busca y devuelve todas las actividades que existen en un evento.
     *
     * @param eventosId El ID del evento del cual se buscan las actividades
     * @return JSONArray {@link ActividadEventoDTO} - Las actividades
     * encontradas en el evento. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ActividadEventoDTO> getActividades(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "ActividadEventoResource getActividades: input: {0}", eventosId);
        List<ActividadEventoDTO> listaDTOs = listEntity2DTO(actividadEventoLogic.getActividadesEvento());
        LOGGER.log(Level.INFO, "EditorialBooksResource getActividades: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve la actividad con el ID recibido en la URL.
     *
     * @param actividadesId El ID de la actividad que se busca
     * @return {@link ActividadEventoDTO} - La actividad encontradas en el
     * libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @GET
    @Path("{actividadesId: \\d+}")
    public ActividadEventoDTO getActividad(@PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadEventoResource getActividad: input: {0}", actividadesId);
        ActividadEventoEntity entity = actividadEventoLogic.getActividadEvento(actividadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /actividadesEvento/" + actividadesId + " no existe.", 404);
        }
        ActividadEventoDTO actividadDTO = new ActividadEventoDTO(entity);
        LOGGER.log(Level.INFO, "ActividadEventoResource getActividad: output: {0}", actividadDTO);
        return actividadDTO;
    }

    /**
     * Actualiza una actividad con la información que se recibe en el cuerpo de
     * la petición y se regresa el objeto actualizado.
     *
     * @param actividadesId El ID de la actividad que se va a actualizar
     * @param actividad {@link ActividadEventoDTO} - La reseña que se desea
     * guardar.
     * @return JSON {@link ActividadEventoDTO} - La actividad actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @PUT
    @Path("{actividadesId: \\d+}")
    public ActividadEventoDTO updateActividad(@PathParam("actividadesId") Long actividadesId, ActividadEventoDTO actividad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadEventoResource updateActividad: input: actividadesId: {0} , actividad:{1}", new Object[]{actividadesId, actividad});
        if (actividadesId.equals(actividad.getId())) {
            throw new BusinessLogicException("Los ids de la actividad no coinciden.");
        }
        ActividadEventoEntity entity = actividadEventoLogic.getActividadEvento(actividadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /actividadesEvento/" + actividadesId + " no existe.", 404);
        }
        ActividadEventoDTO actividadDTO = new ActividadEventoDTO(actividadEventoLogic.updateActividadEvento(actividad.toEntity()));
        LOGGER.log(Level.INFO, "ActividadEventoResource updateActividad: output:{0}", actividadDTO);
        return actividadDTO;
    }

    /**
     * Borra la actividad con el id asociado recibido en la URL.
     *
     * @param actividadesId El ID de la actividad que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{actividadesId: \\d+}")
    public void deleteReview(@PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        ActividadEventoEntity entity = actividadEventoLogic.getActividadEvento(actividadesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /actividadesEvento/" + actividadesId + " no existe.", 404);
        }
        actividadEventoLogic.deleteActividadEvento(actividadesId);
    }

    /**
     * Convierte una lista de ActividadEventoEntity a una lista de
     * ActividadEventoDTO.
     *
     * @param entityList Lista de ActividadEventoEntity a convertir.
     * @return Lista de ActividadEventoDTO convertida.
     */
    private List<ActividadEventoDTO> listEntity2DTO(List<ActividadEventoEntity> entityList) {
        List<ActividadEventoDTO> list = new ArrayList();
        for (ActividadEventoEntity entity : entityList) {
            list.add(new ActividadEventoDTO(entity));
        }
        return list;
    }
}
