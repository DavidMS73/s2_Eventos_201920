/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MemoriaDTO;
import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
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
 * @author Gabriel Jose Gonzalez Pereira
 */
@Path("memorias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MemoriaResource {

    @Inject
    private MemoriaLogic logic;

    private static final Logger LOGGER = Logger.getLogger(MemoriaResource.class.getName());

    @POST
    public MemoriaDTO createMemoria(MemoriaDTO memoria) throws BusinessLogicException {
        MemoriaEntity memoriaEntity = memoria.toEntity();
        memoriaEntity = logic.createMemoria(memoriaEntity);
        return new MemoriaDTO(memoriaEntity);
    }

    private List<MemoriaDTO> listEntity2DTO(List<MemoriaEntity> entityList) {
        List<MemoriaDTO> list = new ArrayList<>();
        for (MemoriaEntity entity : entityList) {
            list.add(new MemoriaDTO(entity));
        }

        return list;
    }

    @GET
    public List<MemoriaDTO> getMemorias() {
        LOGGER.info("MultimediaResource getMemorias: input: void");
        List<MemoriaDTO> listaMemorias = listEntity2DTO(logic.getMemorias());
        LOGGER.log(Level.INFO, "MemoriaResource getMemorias: output: {0}");
        return listaMemorias;
    }

    @GET
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO getMemoria(@PathParam("memoriasId") Long memoriasId) {
        LOGGER.log(Level.INFO, "MemoriaResource getMemoria: input: {0}", memoriasId);
        MemoriaEntity entity = logic.getMemoria(memoriasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /memorias/" + memoriasId + "no existe.", 404);
        }

        MemoriaDTO memoriaDTO = new MemoriaDTO(entity);
        LOGGER.log(Level.INFO, "MemoriaResource getMemoria: output: {0}", memoriaDTO);
        return memoriaDTO;
    }

    @PUT
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO updateMultimedia(@PathParam("memoriasId") Long memoriasId, MemoriaDTO memoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MemoriaResource updateMemoria: input: id: {0}, memoria: {1}", new Object[]{memoriasId, memoria});
        memoria.setId(memoriasId);
        if (logic.getMemoria(memoriasId) == null) {
            throw new WebApplicationException("El recurso /memorias/" + memoriasId + "no existe.", 404);
        }

        MemoriaDTO memoriaDTO = new MemoriaDTO(logic.updateMemoria(memoriasId, memoria.toEntity()));
        LOGGER.log(Level.INFO, "MemoriaResource updateMemoria: output: {0}", memoriaDTO);
        return memoriaDTO;
    }

    @DELETE
    @Path("{memoriasId: \\d+}")
    public void deleteMemoria(@PathParam("memoriasId") Long memoriasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MemoriaResource deleteMemoria: input: {0}", memoriasId);
        MemoriaEntity entity = logic.getMemoria(memoriasId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /memorias/" + memoriasId + "no existe.", 404);
        }

        logic.deleteMemoria(memoriasId);
        LOGGER.info("MemoriaResource deleteMemoria: output: void");
    }
}
