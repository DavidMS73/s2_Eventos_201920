/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PseDTO;
import co.edu.uniandes.csw.eventos.ejb.PseLogic;
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

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PseResource {

    @Inject
    private PseLogic pseLogic;

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /usuarios/";

    /**
     * Parte del mensaje
     */
    private String msg2 = "/pse/";

    /**
     * Parte del mensaje
     */
    private String msg3 = " no existe.";

    private static final Logger LOGGER = Logger.getLogger(PseResource.class.getName());

    private List<PseDTO> listEntity2DTO(List<PseEntity> entityList) {
        List<PseDTO> list = new ArrayList<>();
        for (PseEntity entity : entityList) {
            list.add(new PseDTO(entity));
        }
        return list;
    }

    @POST
    public PseDTO createPse(@PathParam("usuariosId") Long usuariosId, PseDTO pse) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PseResource createPse: input: {0}", pse);
        PseDTO nuevoPse = new PseDTO(pseLogic.createPse(usuariosId, pse.toEntity()));
        LOGGER.log(Level.INFO, "PseResource createPse: output: {0}", nuevoPse);
        return nuevoPse;
    }

    @GET
    public List<PseDTO> getPses(@PathParam("usuariosId") Long usuariosId) {
        return listEntity2DTO(pseLogic.getPses(usuariosId));
    }

    @GET
    @Path("{pseId: \\d+}")
    public PseDTO getPse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId) {
        LOGGER.log(Level.INFO, "PseResource getPse: input: {0}", pseId);
        PseEntity tarjetaEntity = pseLogic.getPse(usuariosId, pseId);
        if (tarjetaEntity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + pseId + msg3, 404);
        }
        PseDTO pseDTO = new PseDTO(tarjetaEntity);
        LOGGER.log(Level.INFO, "PseResource getPse: output: {0}", pseDTO);
        return pseDTO;
    }

    @PUT
    @Path("{pseId: \\d+}")
    public PseDTO updatePse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId, PseDTO pse) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PseResource updatePse: input: usuariosId: {0} , pseId: {1} , pse:{2}", new Object[]{usuariosId, pseId, pse});
        if (pseId.equals(pse.getId())) {
            throw new BusinessLogicException("Los ids del pse no coinciden.");
        }
        PseEntity entity = pseLogic.getPse(usuariosId, pseId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + pseId + msg3, 404);
        }
        PseDTO pseDTO = new PseDTO(pseLogic.updatePse(pseId, pse.toEntity()));
        LOGGER.log(Level.INFO, "PseResource updatePse: output: {0}", pseDTO);
        return pseDTO;
    }

    @DELETE
    @Path("{pseId: \\d+}")
    public void deletePse(@PathParam("usuariosId") Long usuariosId, @PathParam("pseId") Long pseId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PseResource deletePse: input: usuariosId: {0} , pseId: {1}", new Object[]{usuariosId, pseId});
        PseEntity entity = pseLogic.getPse(usuariosId, pseId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + usuariosId + msg2 + pseId + msg3, 404);
        }
        pseLogic.deletePse(usuariosId, pseId);
        LOGGER.info("PseResource deletePse: output: void");
    }
}
