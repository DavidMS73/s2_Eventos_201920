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
 * @author Danisanti Tenjo
 */
@Path("pse")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PseResource {

    @Inject
    private PseLogic logica;

    private static final Logger LOGGER = Logger.getLogger(PseResource.class.getName());

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /pse/";

    /**
     * Parte del mensaje
     */
    private String msg2 = " no existe.";

    private List<PseDTO> listEntity2DTO(List<PseEntity> entityList) {
        List<PseDTO> list = new ArrayList<>();
        for (PseEntity entity : entityList) {
            list.add(new PseDTO(entity));
        }
        return list;
    }

    @POST
    public PseDTO createPse(PseDTO pse) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PseResource createPse: input: {0}", pse);
        PseEntity pseEntity = pse.toEntity();
        pseEntity = logica.createPse(pseEntity);
        PseDTO nuevoPse = new PseDTO(pseEntity);
        LOGGER.log(Level.INFO, "PseResource createPse: input: {0}", pse);
        return nuevoPse;
    }

    @GET
    public List<PseDTO> getPses() {
        LOGGER.info("PseResource getPses: input: void");
        List<PseDTO> listaPses = listEntity2DTO(logica.getPses());
        LOGGER.log(Level.INFO, "PseResource getPses: output: {0}", listaPses);
        return listaPses;
    }

    @GET
    @Path("{pseId: \\d+}")
    public PseDTO getPse(@PathParam("pseId") Long pseId) {
        LOGGER.log(Level.INFO, "PseResource getPse: input: {0}", pseId);
        PseEntity pseEntity = logica.getPse(pseId);
        if (pseEntity == null) {
            throw new WebApplicationException(msg1 + pseId + msg2, 404);
        }
        PseDTO pseDTO = new PseDTO(pseEntity);
        LOGGER.log(Level.INFO, "PseResource getPse: output: {0}", pseDTO);
        return pseDTO;
    }

    @PUT
    @Path("{pseId: \\d+}")
    public PseDTO updatePse(@PathParam("pseId") Long pseId, PseDTO pse) {
        LOGGER.log(Level.INFO, "PseResource updatePse: input: id: {0} , evento: {1}", new Object[]{pseId, pse});
        pse.setId(pseId);
        if (logica.getPse(pseId) == null) {
            throw new WebApplicationException(msg1 + pseId + msg2, 404);
        }
        PseDTO detailDTO = new PseDTO(logica.updatePse(pseId, pse.toEntity()));
        LOGGER.log(Level.INFO, "PseResource updatePse: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{pseId: \\d+}")
    public void deletePse(@PathParam("pseId") Long pseId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PseResource deletePse: input: {0}", pseId);
        if (logica.getPse(pseId) == null) {
            throw new WebApplicationException(msg1 + pseId + msg2, 404);
        }
        logica.deletePse(pseId);
        LOGGER.info("PseResource deletePse: output: void");
    }
}
