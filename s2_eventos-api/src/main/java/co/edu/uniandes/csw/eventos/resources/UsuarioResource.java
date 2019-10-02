/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UsuarioDTO;
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
private UsuarioLogic ulogic;
@POST
public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws BusinessLogicException
{
    LOGGER.log(Level.INFO, "UsuarioResource createEditorial: input: {0}", usuario);
        UsuarioEntity usuarioEntity = usuario.toEntity();
        usuarioEntity = ulogic.createUsuario(usuarioEntity);
        UsuarioDTO nuevoEditorialDTO = new UsuarioDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "usuarioResource createEditorial: output: {0}", nuevoEditorialDTO);
    return nuevoEditorialDTO;
}
 @GET
    public List<UsuarioDTO> getUsuarios() {
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDTO> listaUsuarios = listEntity2DTO(ulogic.getUsuarios());
        LOGGER.log(Level.INFO, "UsuariosResource getAuthors: output: {0}", listaUsuarios);
        return listaUsuarios;
    }
    private List<UsuarioDTO> listEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDTO(entity));
        }
        return list;
    }
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO getUsuario(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = ulogic.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDTO detailDTO = new UsuarioDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO updateAuthor(@PathParam("usuariosId") Long usuariosId, UsuarioDTO usuario) {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: usuariosId: {0} , usuario: {1}", new Object[]{usuariosId, usuario});
        usuario.setId(usuariosId);
        if (ulogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /authors/" + usuariosId + " no existe.", 404);
        }
        UsuarioDTO detailDTO = new UsuarioDTO(ulogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO);
        return detailDTO;
    }
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (ulogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        ulogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }

}
