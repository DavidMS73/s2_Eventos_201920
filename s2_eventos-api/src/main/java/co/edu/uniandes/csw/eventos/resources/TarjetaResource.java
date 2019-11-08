/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.TarjetaDTO;
import co.edu.uniandes.csw.eventos.ejb.TarjetaLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioTarjetasLogic;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
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
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Samuelillo el pillo.
 */
@Path("usuarios/{usuariosId: \\d+}/tarjetas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TarjetaResource {
    private static final Logger LOGGER = Logger.getLogger(TarjetaResource.class.getName());
    
    @Inject
    private TarjetaLogic tp;
    
    @Inject
    private UsuarioTarjetasLogic utp;
    
    @POST
    public TarjetaDTO createTarjeta(@PathParam("usuariosId") Long usuariosId, TarjetaDTO tarjeta) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "TarjetaResource createTarjeta: input: {0}", tarjeta);
        TarjetaEntity newEntity = tarjeta.toEntity();
        newEntity = tp.createTarjeta(usuariosId, newEntity);
        LOGGER.log(Level.INFO, "TarjetaResource createTarjeta: input: {0}", tarjeta);
        return new TarjetaDTO(newEntity);
    } 
    
    @GET
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO getTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource getTarjeta: input: {0}", tarjetasId);
        TarjetaEntity tarjetaEntity = tp.getTarjeta(usuariosId, tarjetasId);
        if (tarjetaEntity == null) {
            throw new WebApplicationException("El recurso /tarjetas/" + tarjetasId + " no existe.", 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tarjetaEntity);
        LOGGER.log(Level.INFO, "TarjetaResource getTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }
    
    @PUT
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO updateTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId, TarjetaDTO tarjeta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: input: id: {0} , tarjeta: {1}", new Object[]{tarjetasId, tarjeta});
        tarjeta.setId(tarjetasId);
        if (tp.getTarjeta(usuariosId, tarjetasId) == null) {
            throw new WebApplicationException("El recurso /tarjetas/" + tarjetasId + " no existe.", 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tp.updateTarjeta(tarjetasId, tarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }
    
    @DELETE
    @Path("{tarjetasId: \\d+}")
    public void deleteTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: input: id: {0} , tarjeta: {1}", new Object[]{tarjetasId, tarjetasId});
        TarjetaEntity entity = tp.getTarjeta(usuariosId, tarjetasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tarjetas/" + tarjetasId + " no existe.", 404);
        }
        tp.deleteTarjeta(usuariosId, tarjetasId);
        utp.removeTarjeta(usuariosId, tarjetasId);
        LOGGER.info("TarjetaResource deleteTarjeta: output: void");
    }
    
    @GET
    public List<TarjetaDTO> getTarjetas(@PathParam("usuariosId") Long usuariosId){
      LOGGER.log(Level.INFO, "TarjetasResource getTarjetas: input: {0}", usuariosId);
      List<TarjetaDTO> tarjetasDTO = tarjetasEntityToDTO(tp.getTarjetas(usuariosId));
      LOGGER.log(Level.INFO, "EventoResource getLugares: output: {0}", tarjetasDTO);
      return tarjetasDTO;
    }
    
    private List<TarjetaDTO> tarjetasEntityToDTO(List<TarjetaEntity> list){
        List<TarjetaDTO> listDTO = new ArrayList();
        for(TarjetaEntity entity : list){
            listDTO.add(new TarjetaDTO(entity));
        }
        return listDTO;
    }
}
