/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoUsuariosLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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
 * Clase que implementa el recurso "eventos/{id}/usuarios"
 *
 * @author Germán David Martínez Solano
 */
@Produces("application/json")
@Consumes("application/json")
public class EventoUsuariosResource {

    /**
     * Logger del recurso
     */
    private static final Logger LOGGER = Logger.getLogger(EventoUsuariosResource.class.getName());

    /**
     * Lógica de la relación
     */
    @Inject
    private EventoUsuariosLogic eventoUsuarioLogic;

    /**
     * Lógica de usuario
     */
    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Asocia un usuario existente con un evento existente
     *
     * @param usuariosId El ID del usuario que se va a asociar
     * @param eventosId El ID del evento al cual se le va a asociar el usuario
     * @return JSON {@link UsuarioDetailDTO} - El usuario asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @POST
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO addUsuario(@PathParam("eventosId") Long eventosId, @PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "EventoUsuariosResource addUsuario: input: eventosId {0} , usuariosId {1}", new Object[]{eventosId, usuariosId});
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(eventoUsuarioLogic.addUsuario(eventosId, usuariosId));
        LOGGER.log(Level.INFO, "EventoUsuariosResource addUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los usuarios que existen en un evento.
     *
     * @param eventosId El ID del evento del cual se buscan los usuarios
     * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados en
     * el evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "EventoUsuariosResource getUsuarios: input: {0}", eventosId);
        List<UsuarioDetailDTO> lista = usuariosListEntity2DTO(eventoUsuarioLogic.getUsuarios(eventosId));
        LOGGER.log(Level.INFO, "EventoUsuariosResource getUsuarios: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el usuario con el ID recibido en la URL, relativo a un
     * evento.
     *
     * @param usuariosId El ID del usuario que se busca
     * @param eventosId El ID del evento del cual se busca el usuario
     * @return {@link UsuarioDetailDTO} - El usuario encontrado en el evento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("eventosId") Long eventosId, @PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "EventoUsuariosResource getUsuario: input: eventosId {0} , usuariosId {1}", new Object[]{eventosId, usuariosId});
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(eventoUsuarioLogic.getUsuario(eventosId, usuariosId));
        LOGGER.log(Level.INFO, "EventoUsuariosResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de usuarios de un evento con la lista que se recibe en
     * el cuerpo.
     *
     * @param eventosId El ID del evento al cual se le va a asociar la lista de
     * usuarios
     * @param usuarios JSONArray {@link UsuarioDetailDTO} - La lista de usuarios
     * que se desea guardar.
     * @return JSONArray {@link UsuarioDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @PUT
    public List<UsuarioDetailDTO> replaceUsuarios(@PathParam("eventosId") Long eventosId, List<UsuarioDetailDTO> usuarios) {
        LOGGER.log(Level.INFO, "EventoUsuariosResource replaceUsuarios: input: eventosId {0} , usuarios {1}", new Object[]{eventosId, usuarios});
        for (UsuarioDetailDTO usuario : usuarios) {
            if (usuarioLogic.getUsuario(usuario.getId()) == null) {
                throw new WebApplicationException("El recurso /usuarios/" + usuario.getId() + " no existe.", 404);
            }
        }
        List<UsuarioDetailDTO> lista = usuariosListEntity2DTO(eventoUsuarioLogic.replaceUsuarios(eventosId, usuariosListDTO2Entity(usuarios)));
        LOGGER.log(Level.INFO, "EventoUsuariosResource replaceUsuarios: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el usuario y el evento recibidos en la URL.
     *
     * @param eventosId El ID del evento al cual se le va a desasociar el
     * usuario
     * @param usuariosId El ID del usuario que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void removeUsuario(@PathParam("eventosId") Long eventosId, @PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "EventoUsuariosResource removeUsuario: input: eventosId {0} , usuariosId {1}", new Object[]{eventosId, usuariosId});
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        eventoUsuarioLogic.removeUsuario(eventosId, usuariosId);
        LOGGER.info("EventoUsuariosResource removeUsuario: output: void");
    }

    /**
     * Convierte una lista de UsuarioEntity a una lista de UsuarioDetailDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDetailDTO convertida.
     */
    private List<UsuarioDetailDTO> usuariosListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de UsuarioDetailDTO a una lista de UsuarioEntity.
     *
     * @param dtos Lista de UsuarioDetailDTO a convertir.
     * @return Lista de UsuarioEntity convertida.
     */
    private List<UsuarioEntity> usuariosListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
