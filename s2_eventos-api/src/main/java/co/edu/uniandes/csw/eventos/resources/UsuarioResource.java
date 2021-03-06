/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UsuarioDTO;
import co.edu.uniandes.csw.eventos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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
 * @author Daniel Betancurth Dorado
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    @Inject
    private UsuarioLogic uLogic;

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /usuarios/";

    /**
     * Parte del mensaje
     */
    private String msg2 = " no existe.";

    @POST
    public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource createEditorial: input: {0}", usuario);
        UsuarioEntity usuarioEntity = usuario.toEntity();
        usuarioEntity = uLogic.createUsuario(usuarioEntity);
        UsuarioDTO nuevoEditorialDTO = new UsuarioDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource createEditorial: output: {0}", nuevoEditorialDTO);
        return nuevoEditorialDTO;
    }

    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DTO(uLogic.getUsuarios());
        LOGGER.log(Level.INFO, "UsuarioResource getUsuarios: output: {0}", listaUsuarios);
        return listaUsuarios;
    }

    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = uLogic.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2, 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    @GET
    @Path("{nombre: [a-zA-Z][a-zA-Z]*}")
    public UsuarioDetailDTO getUsuarioCorreo(@PathParam("nombre") String correo) {
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", correo);
        UsuarioEntity usuarioEntity = uLogic.getUsuarioUsername(correo);
        if (usuarioEntity == null) {
            throw new WebApplicationException(msg1 + correo + msg2, 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDetailDTO usuario) {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: usuariosId: {0} , usuario: {1}", new Object[]{usuariosId, usuario});
        usuario.setId(usuariosId);
        if (uLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2, 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(uLogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (uLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2, 404);
        }
        uLogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }

    @Path("{usuariosId: \\d+}/eventos")
    public Class<UsuarioEventosResource> getUsuarioEventosResource(@PathParam("usuariosId") Long usuariosId) {
        if (uLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2, 404);
        }
        return UsuarioEventosResource.class;
    }

    /**
     * Conexión con el servicio de tarjetas para un usuario.
     * {@link TarjetaResource}
     *
     * Este método conecta la ruta de /tarjetas con las rutas de /usuarios que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las tarjetas.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de tarjeta para ese evento en particular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @Path("{usuariosId: \\d+}/tarjetas")
    public Class<TarjetaResource> getTarjetaResource(@PathParam("usuariosId") Long usuariosId) {
        if (uLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(msg1 + usuariosId + "/tarjetas" + msg2, 404);
        }
        return TarjetaResource.class;
    }

    /**
     * Conexión con el servicio de pse para un usuario. {@link PseResource}
     *
     * Este método conecta la ruta de /pse con las rutas de /usuarios que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de pse.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de pse para ese evento en particular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @Path("{usuariosId: \\d+}/pse")
    public Class<PseResource> getPseResource(@PathParam("usuariosId") Long usuariosId) {
        if (uLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(msg1 + usuariosId + "/pse" + msg2, 404);
        }
        return PseResource.class;
    }

    private List<UsuarioDetailDTO> listEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
}
