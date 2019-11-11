/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PatrocinioDTO;
import co.edu.uniandes.csw.eventos.dtos.PatrocinioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
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
@Path("patrocinios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PatrocinioResource {

    private static final Logger LOGGER = Logger.getLogger(PatrocinioResource.class.getName());
    @Inject
    private PatrocinioLogic logic;

    @POST
    public PatrocinioDTO crearPatrocinio(PatrocinioDTO patrocinio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PatrocinioResource createPatrocinio: input: {0}", patrocinio);
        PatrocinioEntity entity = patrocinio.toEntity();
        entity = logic.createPatrocinio(entity);
        PatrocinioDTO nuevoPatrocinioDTO = new PatrocinioDTO(entity);
        LOGGER.log(Level.INFO, "PatrocinioResource createPatrocinio: output: {0}", nuevoPatrocinioDTO);
        return nuevoPatrocinioDTO;
    }

    @GET
    public List<PatrocinioDetailDTO> getPatrocinios() {
        LOGGER.info("PatrocinioResource getPatrocinios: input: void");
        List<PatrocinioDetailDTO> listaPatrocinios = listEntity2DTO(logic.getPatrocinios());
        LOGGER.log(Level.INFO, "UsuarioResource getPatrocinios: output: {0}", listaPatrocinios);
        return listaPatrocinios;
    }

    private List<PatrocinioDetailDTO> listEntity2DTO(List<PatrocinioEntity> entityList) {
        List<PatrocinioDetailDTO> list = new ArrayList<>();
        for (PatrocinioEntity entity : entityList) {
            list.add(new PatrocinioDetailDTO(entity));
        }
        return list;
    }

    @GET
    @Path("{patrociniosId: \\d+}")
    public PatrocinioDetailDTO getPatrocinio(@PathParam("patrociniosId") Long patrociniosId) {
        LOGGER.log(Level.INFO, "PatrocinioResource getPatrocinio: input: {0}", patrociniosId);
        PatrocinioEntity entity = logic.getPatrocinio(patrociniosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        PatrocinioDetailDTO detailDTO = new PatrocinioDetailDTO(entity);
        LOGGER.log(Level.INFO, "PatrocinioResource getPatrocinio: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{patrociniosId: \\d+}")
    public PatrocinioDTO updatePatrocinio(@PathParam("patrociniosId") Long patrociniosId, PatrocinioDTO patrocinio) {
        LOGGER.log(Level.INFO, "PatrocinioResource updatePatrocinio: input: patrociniosId: {0} , patrocinio: {1}", new Object[]{patrociniosId, patrocinio});
        patrocinio.setId(patrociniosId);
        if (logic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        PatrocinioDTO detailDTO = new PatrocinioDTO(logic.updatePatrocinio(patrociniosId, patrocinio.toEntity()));
        LOGGER.log(Level.INFO, "PatrocinioResource updatePatrocinio: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{patrociniosId: \\d+}")
    public void deletePatrocinio(@PathParam("patrociniosId") Long patrociniosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PatrocinioResource deletePatrocinio: input: {0}", patrociniosId);
        if (logic.getPatrocinio(patrociniosId) == null) {
            throw new WebApplicationException("El recurso /patrocinios/" + patrociniosId + " no existe.", 404);
        }
        logic.deletePatrocinio(patrociniosId);
        LOGGER.info("PatrocinioResource deletePatrocinio: output: void");
    }
}
