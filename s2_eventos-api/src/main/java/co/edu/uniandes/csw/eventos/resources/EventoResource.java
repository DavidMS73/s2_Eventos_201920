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
 * Clase que implementa el recurso evento
 *
 * @author Germán David Martínez Solano
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EventoResource {

    /**
     * Variable para acceder a la lógica del evento
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Logger del recurso
     */
    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    /**
     * Crea un nuevo evento con la información que se recibe en el cuerpo de la
     * petición y se regresa un objeto idéntico con un id auto-generado por la
     * base de datos
     *
     * @param evento (@link EventoDTO) - El evento que se desea guardar
     * @return JSON {@link EventoDTO} - El evento guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera si el evento ingresado es inválida.
     */
    @POST
    public EventoDTO createEvento(EventoDTO evento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource createEvento: input: {0}", evento);
        EventoEntity eventoEntity = evento.toEntity();
        eventoEntity = eventoLogic.createEvento(eventoEntity);
        EventoDTO nuevoEventoDTO = new EventoDTO(eventoEntity);
        LOGGER.log(Level.INFO, "EventoResource createEvento: output: {0}", nuevoEventoDTO);
        return nuevoEventoDTO;
    }

    /**
     * Busca y devuelve todos los eventos que existen en la app
     *
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos() {
        LOGGER.info("EventoResource getEventos: input: void");
        List<EventoDetailDTO> listaEventos = listEntity2DetailDTO(eventoLogic.getEventos());
        LOGGER.log(Level.INFO, "EventoResource getEventos: output: {0}", listaEventos);
        return listaEventos;
    }

    /**
     * Busca el evento con el id asociado recibido en la URL y lo devuelve.
     *
     * @param eventosId Identificador del evento que se está buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EventoDetailDTO} - El evento buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "EventoResource getEvento: input: {0}", eventosId);
        EventoEntity entidad = eventoLogic.getEvento(eventosId);
        if (entidad == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO eventoDetailDTO = new EventoDetailDTO(entidad);
        LOGGER.log(Level.INFO, "EventoResource getEvento: output: {0}", eventoDetailDTO);
        return eventoDetailDTO;
    }

    /**
     * Actualiza el evento con el id recibido en la URL con la información que
     * se recibe en el cuerpo de la petición.
     *
     * @param eventosId Identificador del evento que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param evento {@link EventoDTO} El evento que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el evento.
     */
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

    /**
     * Borra el evento con el id asociado recibido en la URL.
     *
     * @param eventosId Identificador del evento que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws BusinessLogicException - cuando el evento tiene un error en
     * lógica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void deleteEvento(@PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource deleteEvento: input: {0}", eventosId);
        EventoEntity entity = eventoLogic.getEvento(eventosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        eventoLogic.deleteEvento(eventosId);
        LOGGER.info("EventoResource deleteEvento: output: void");
    }

    /**
     * Conexión con el servicio de actividades para un evento.
     * {@link ActividadEventoResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de
     * /actividadesEvento que dependen del evento, es una redirección al
     * servicio que maneja el segmento de la URL que se encarga de las reseñas.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de Actividad para ese evento en particular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @Path("{eventosId: \\d+}/actividades")
    public Class<ActividadEventoResource> getActividadEventoResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + "/actividades no existe.", 404);
        }
        return ActividadEventoResource.class;
    }

    /**
     * Conexión con el servicio de usuarios para un evento.
     * {@link EventoUsuariosResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /usuarios que
     * dependen del evento, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los usuarios
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de usuarios para ese libro en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @Path("{eventosId: \\d+}/usuarios")
    public Class<EventoUsuariosResource> getEventoUsuariosResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.getEvento(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        return EventoUsuariosResource.class;
    }
    
    /**
     * Conexión con el servicio de patrocinios para un evento.
     * {@link EventoUsuariosResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /patrocinios que
     * dependen del evento, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los usuarios
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de usuarios para ese libro en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
//    @Path("{eventosId: \\d+}/usuarios")
//    public Class<EventoPatrociniosResource> getEventoUsuariosResource(@PathParam("eventosId") Long eventosId) {
//        if (eventoLogic.getEvento(eventosId) == null) {
//            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
//        }
//        return EventoUsuariosResource.class;
//    }

    /**
     * Convierte una lista de entidad a DTO
     *
     * Este método convierte una lista de objetos EventoEntity a una lista de
     * objetos EventoDetailDTO (JSON)
     *
     * @param entityList corresponde a la lista de eventos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de eventos en forma DTO (JSON)
     */
    private List<EventoDetailDTO> listEntity2DetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
}
