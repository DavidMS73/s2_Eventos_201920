/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.TarjetaDTO;
import co.edu.uniandes.csw.eventos.ejb.TarjetaLogic;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Samuelillo el pillo.
 */
@Path("tarjetas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TarjetaResource {
    private static final Logger LOGGER = Logger.getLogger(TarjetaResource.class.getName());
    
    @Inject
    private TarjetaLogic tp;
    
    @POST
    public TarjetaDTO createTarjeta(TarjetaDTO tarjeta) throws BusinessLogicException{
        TarjetaEntity newEntity = tarjeta.toEntity();
        newEntity = tp.createTarjeta(newEntity);
        
        return new TarjetaDTO(newEntity);
    } 
    
    @GET
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO getTarjeta(@PathParam("tarjetasId") Long tarjetasId)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource getTarjeta: input: {0}", tarjetasId);
        TarjetaEntity tarjetaEntity = tp.getTarjeta(tarjetasId);
        if (tarjetaEntity == null) {
            throw new WebApplicationException("El recurso /books/" + tarjetasId + " no existe.", 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tarjetaEntity);
        LOGGER.log(Level.INFO, "BookResource getBook: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }
    
    @PUT
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO updateTarjeta(@PathParam("tarjetasId") Long tarjetasId, TarjetaDTO tarjeta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: input: id: {0} , tarjeta: {1}", new Object[]{tarjetasId, tarjeta});
        tarjeta.setId(tarjetasId);
        if (tp.getTarjeta(tarjetasId) == null) {
            throw new WebApplicationException("El recurso /tarjetas/" + tarjetasId + " no existe.", 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tp.updateTarjeta(tarjetasId, tarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }
    
    @DELETE
    @Path("{tarjetasId: \\d+}")
    public void deleteTarjeta(@PathParam("tarjetasId") Long tarjetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource deleteTarjeta: input: {0}", tarjetasId);
        TarjetaEntity entity = tp.getTarjeta(tarjetasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tarjetas/" + tarjetasId + " no existe.", 404);
        }
        tp.deleteTarjeta(tarjetasId);
        LOGGER.info("TarjetaResource deleteTarjeta: output: void");
    }
}
