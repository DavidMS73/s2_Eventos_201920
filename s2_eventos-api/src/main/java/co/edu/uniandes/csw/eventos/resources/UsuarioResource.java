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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
}
