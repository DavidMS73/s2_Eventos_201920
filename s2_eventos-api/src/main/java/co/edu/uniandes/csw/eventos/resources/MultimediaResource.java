/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;


import co.edu.uniandes.csw.eventos.dtos.MultimediaDTO;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
 * @author Gabriel Jose Gonzalez Pereira
 */

@Path("multimedias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MultimediaResource
{
    @Inject
    private MultimediaLogic logic;
   
    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());
   
    @POST
    public MultimediaDTO createMultimedia(MultimediaDTO multimedia) throws BusinessLogicException
    {
        MultimediaEntity multimediaEntity = multimedia.toEntity();
        multimediaEntity = logic.createMultimedia(multimediaEntity);
        return new MultimediaDTO(multimediaEntity);
    }
   
    @GET
    @Path("{multimediasId: \\d+")
    public MultimediaDTO getMultimedia(@PathParam("mutlimediasId") Long multimediasId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "MultimediaResource getMultimedia: input: {0}", multimediasId);
        MultimediaEntity multimediaEntity = logic.getMultimedia(multimediasId);
        if(multimediaEntity == null)
        {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + "no existe", 404);
        }
       
        MultimediaDTO multimediaDTO = new MultimediaDTO(multimediaEntity);
        LOGGER.log(Level.INFO, "MultimediaResource getMultimedia: output: {0}", multimediaDTO);
        return multimediaDTO;
    }
   
    @PUT
    @Path("{multimediasId: \\d+")
    public MultimediaDTO updateMultimedia(@PathParam("multimediasId") Long multimediasId, MultimediaDTO multimedia) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "MultimediaResource updateMultimedia: input: id: {0}, multimedia: {1}", new Object[]{multimediasId, multimedia});
        multimedia.setId(multimediasId);
        if(logic.getMultimedia(multimediasId) == null)
        {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + "no existe.", 404);
        }
       
        MultimediaDTO multimediaDTO = new MultimediaDTO(logic.updateMultimedia(multimediasId, multimedia.toEntity()));
        LOGGER.log(Level.INFO, "MultimediaResource updateMultimedia: output: {0}", multimediaDTO);
        return multimediaDTO;
    }
   
    @DELETE
    @Path("{multimediasId: \\d+")
    public void deleteMultimedia(@PathParam("multimediasId") Long multimediasId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "MultimediaResource deleteMultimedia: input: {0}", multimediasId);
        MultimediaEntity entity = logic.getMultimedia(multimediasId);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + "no existe.", 404);
        }
       
        logic.deleteMultimedia(multimediasId);
        LOGGER.info("MultimediaResource deleteMultimedia: output: void");
    }
}