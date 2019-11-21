/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PseDTO;
import co.edu.uniandes.csw.eventos.ejb.PseLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioTarjetasLogic;
import co.edu.uniandes.csw.eventos.entities.PseEntity;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Danisanti Tenjo
 */

@Path("usuarios/{usuariosId: \\d+}/pses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PseResource {

    @Inject
    private PseLogic logica;
    
    @Inject
    private UsuarioTarjetasLogic utp;
    
    
    private static final String  MSG="El recurso /pse/";
    
    private static final String  MSG1=" no existe";
    
    
    private static final Logger LOGGER= Logger.getLogger(PseResource.class.getName());
    
    
    private List<PseDTO> listEntity2DTO(List<PseEntity> entityList) {
        List<PseDTO> list = new ArrayList<>();
        for (PseEntity entity : entityList) {
            list.add(new PseDTO(entity));
        }
        return list;
    }

    @POST
    public PseDTO createPse(@PathParam("usuariosId") Long usuariosId,PseDTO pse) throws BusinessLogicException {

        PseEntity pseEntity = pse.toEntity();
        pseEntity = logica.createPse(usuariosId, pseEntity);
        PseDTO nuevoPse = new PseDTO(pseEntity);
        LOGGER.log(Level.INFO, "PseResource createPse: input: {0}", pse);
        return nuevoPse;
    }

    @GET
    public List<PseDTO> getPses(@PathParam("usuariosId") Long usuariosId) {
        return listEntity2DTO(logica.getPses(usuariosId));
    }    
    
    @GET
    @Path("{pseId: \\d+}")
    public PseDTO getPse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId)
    {
        PseEntity pseEntity= logica.getPse(usuariosId, pseId);
        if( pseEntity == null)
        {
            throw new WebApplicationException(MSG+ pseId + MSG1, 404 );
        }
        PseDTO pseDTO = new PseDTO(pseEntity);
        LOGGER.log(Level.INFO, "PseResource getPse: output: {0}", pseDTO);
        return pseDTO;
    }

    @PUT
    @Path("{pseId: \\d+}")
    public PseDTO updatePse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId, PseDTO pse) {
        
        pse.setId(pseId);
        if (logica.getPse(usuariosId, pseId) == null) {
            throw new WebApplicationException(MSG + pseId + MSG1, 404);
        }
         
        
        return new PseDTO(logica.updatePse(usuariosId, pseId, pse.toEntity()));
    }

    @DELETE
    @Path("{pseId: \\d+}")
    public void deletePse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId) throws BusinessLogicException {
        
        if (logica.getPse(usuariosId ,pseId) == null) {
            throw new WebApplicationException(MSG + pseId + MSG1, 404);
        }
        logica.deletePse(usuariosId, pseId);
        
    }
}
