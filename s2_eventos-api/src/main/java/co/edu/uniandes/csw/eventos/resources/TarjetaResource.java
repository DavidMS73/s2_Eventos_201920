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

/**
 *
 * @author Samuel Osorio
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TarjetaResource {

    private static final Logger LOGGER = Logger.getLogger(TarjetaResource.class.getName());

    @Inject
    private TarjetaLogic tarjetaLogic;

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /usuarios/";

    /**
     * Parte del mensaje
     */
    private String msg2 = "/tarjetas/";

    /**
     * Parte del mensaje
     */
    private String msg3 = " no existe.";

    @POST
    public TarjetaDTO createTarjeta(@PathParam("usuariosId") Long usuariosId, TarjetaDTO tarjeta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource createTarjeta: input: {0}", tarjeta);
        TarjetaDTO nuevaTarjetaDTO = new TarjetaDTO(tarjetaLogic.createTarjeta(usuariosId, tarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaResource createTarjeta: output: {0}", nuevaTarjetaDTO);
        return nuevaTarjetaDTO;
    }

    @GET
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO getTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource getTarjeta: input: {0}", tarjetasId);
        TarjetaEntity tarjetaEntity = tarjetaLogic.getTarjeta(usuariosId, tarjetasId);
        if (tarjetaEntity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + tarjetasId + msg3, 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tarjetaEntity);
        LOGGER.log(Level.INFO, "TarjetaResource getTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }

    @PUT
    @Path("{tarjetasId: \\d+}")
    public TarjetaDTO updateTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId, TarjetaDTO tarjeta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: input: usuariosId: {0} , tarjetasId: {1} , tarjeta:{2}", new Object[]{usuariosId, tarjetasId, tarjeta});
        if (tarjetasId.equals(tarjeta.getId())) {
            throw new BusinessLogicException("Los ids de la tarjeta no coinciden.");
        }
        TarjetaEntity entity = tarjetaLogic.getTarjeta(usuariosId, tarjetasId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + tarjetasId + msg3, 404);
        }
        TarjetaDTO tarjetaDTO = new TarjetaDTO(tarjetaLogic.updateTarjeta(tarjetasId, tarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaResource updateTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }

    @DELETE
    @Path("{tarjetasId: \\d+}")
    public void deleteTarjeta(@PathParam("usuariosId") Long usuariosId, @PathParam("tarjetasId") Long tarjetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetasResource deleteTarjeta: input: usuariosId: {0} , tarjetasId: {1}", new Object[]{usuariosId, tarjetasId});
        TarjetaEntity entity = tarjetaLogic.getTarjeta(usuariosId, tarjetasId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + tarjetasId + msg3, 404);
        }
        tarjetaLogic.deleteTarjeta(usuariosId, tarjetasId);
        LOGGER.info("TarjetaResource deleteTarjeta: output: void");
    }

    @GET
    public List<TarjetaDTO> getTarjetas(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "TarjetasResource getTarjetas: input: {0}", usuariosId);
        List<TarjetaDTO> tarjetasDTO = tarjetasEntityToDTO(tarjetaLogic.getTarjetas(usuariosId));
        LOGGER.log(Level.INFO, "TarjetasResource getTarjetas: output: {0}", tarjetasDTO);
        return tarjetasDTO;
    }

    private List<TarjetaDTO> tarjetasEntityToDTO(List<TarjetaEntity> list) {
        List<TarjetaDTO> listDTO = new ArrayList();
        for (TarjetaEntity entity : list) {
            listDTO.add(new TarjetaDTO(entity));
        }
        return listDTO;
    }
}
