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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Path("usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsuarioResource {
    
private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
private UsuarioLogic ulogic;
@POST
public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws BusinessLogicException
{
    LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", usuario);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UsuarioEntity usuarioEntity = usuario.toEntity();
    // Invoca la lógica para crear la editorial nueva
        UsuarioEntity nuevoEditorialEntity = ulogic.createUsuario(usuarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        UsuarioDTO nuevoEditorialDTO = new UsuarioDTO(nuevoEditorialEntity);
        LOGGER.log(Level.INFO, "EditorialResource createEditorial: output: {0}", nuevoEditorialDTO);
    return usuario;
}
}
