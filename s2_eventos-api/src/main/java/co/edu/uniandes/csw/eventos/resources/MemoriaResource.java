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
    private MemoriaLogic memoriaLogic;

    private static final Logger LOGGER = Logger.getLogger(MemoriaResource.class.getName());

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /eventos/";

    /**
     * Parte del mensaje
     */
    private String msg2 = "/memorias/";

    /**
     * Parte del mensaje
     */
    private String msg3 = " no existe.";

    @POST
    public MemoriaDTO createMemoria(@PathParam("eventosId") Long eventosId, MemoriaDTO memoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MemoriaResource createMemoria: input: {0}", memoria);
        MemoriaDTO nuevaMemoriaDTO = new MemoriaDTO(memoriaLogic.createMemoria(eventosId, memoria.toEntity()));
        LOGGER.log(Level.INFO, "MemoriaResource createMemoria: output: {0}", nuevaMemoriaDTO);
        return nuevaMemoriaDTO;
    }

    @GET
    public List<MemoriaDTO> getMemorias(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "MemoriaResource getMemorias: input: {0}", eventosId);
        List<MemoriaDTO> listaDTOs = listEntity2DTO(memoriaLogic.getMemorias(eventosId));
        LOGGER.log(Level.INFO, "MemoriaResource getMemorias: output: {0}", listaDTOs);
        return listaDTOs;
    }

    @GET
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO getMemoria(@PathParam("eventosId") Long eventosId, @PathParam("memoriasId") Long memoriasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MemoriaResource getMemoria: input: {0}", memoriasId);
        MemoriaEntity entity = memoriaLogic.getMemoria(eventosId, memoriasId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + eventosId + msg2 + memoriasId + msg3, 404);
        }
        MemoriaDTO memoriaDTO = new MemoriaDTO(entity);
        LOGGER.log(Level.INFO, "MemoriaResource getMemoria: output: {0}", memoriaDTO);
        return memoriaDTO;
    }

    @PUT
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO updateMemoria(@PathParam("eventosId") Long eventosId, @PathParam("memoriasId") Long memoriasId, MemoriaDTO memoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MemoriaResource updateMemoria: input: eventosId: {0} , memoriasId: {1} , memoria:{2}", new Object[]{eventosId, memoriasId, memoria});
        if (memoriasId.equals(memoria.getId())) {
            throw new BusinessLogicException("Los ids de la memoria no coinciden.");
        }
        MemoriaEntity entity = memoriaLogic.getMemoria(eventosId, memoriasId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + eventosId + msg2 + memoriasId + msg3, 404);

        }
        MemoriaDTO actividadDTO = new MemoriaDTO(memoriaLogic.updateMemoria(eventosId, memoria.toEntity()));
        LOGGER.log(Level.INFO, "MemoriaResource updateMemoria: output:{0}", actividadDTO);
        return actividadDTO;
    }

    @DELETE
    @Path("{memoriasId: \\d+}")
    public void deleteReview(@PathParam("eventosId") Long eventosId, @PathParam("memoriasId") Long memoriasId) throws BusinessLogicException {
        MemoriaEntity entity = memoriaLogic.getMemoria(eventosId, memoriasId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + eventosId + msg2 + memoriasId + msg3, 404);
        }
        memoriaLogic.deleteMemoria(eventosId, memoriasId);
    }

    private List<MemoriaDTO> listEntity2DTO(List<MemoriaEntity> entityList) {
        List<MemoriaDTO> list = new ArrayList<>();
        for (MemoriaEntity entity : entityList) {
            list.add(new MemoriaDTO(entity));
        }
        return list;
    }
}
