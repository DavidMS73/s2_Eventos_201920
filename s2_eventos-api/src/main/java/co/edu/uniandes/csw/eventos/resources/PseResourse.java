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
public class PseResourse {
    
    @Inject
    private PseLogic logica;
    
    private static final Logger LOGGER= Logger.getLogger(PseResourse.class.getName());
    
    
    private List<PseDTO> listEntity2DTO(List<PseEntity> entityList) {
        List<PseDTO> list = new ArrayList<>();
        for (PseEntity entity : entityList) {
            list.add(new PseDTO(entity));
        }
        return list;
    }
    @POST
    public PseDTO createPse(PseDTO pse) throws BusinessLogicException {

        PseEntity pseEntity = pse.toEntity();
        pseEntity = logica.createPse(pseEntity);
        PseDTO nuevoPse = new PseDTO(pseEntity);
        return nuevoPse;
    }

    @GET
    public List<PseDTO> getPatrocinios() {
        List<PseDTO> listaPatrocinios = listEntity2DTO(logica.getPses());
        return listaPatrocinios;
    }    
    
    @GET
    @Path("{pseId: \\d+}")
    public PseDTO getPse(@PathParam("pseId") Long pseId)
    {
        PseEntity pseEntity= logica.getPse(pseId);
        if( pseEntity == null)
        {
            throw new WebApplicationException("El recurso /books/"+ pseId + " no existe.", 404 );
        }
        PseDTO pseDTO= new PseDTO(pseEntity);
        return pseDTO;
    }
    
    @PUT
    @Path("{pseId: \\d+}")
    public PseDTO updatePse(@PathParam("pseId") Long pseId, PseDTO pse) {
        
        pse.setId(pseId);
        if (logica.getPse(pseId) == null) {
            throw new WebApplicationException("El recurso /pse/" + pseId + " no existe.", 404);
        }
        PseDTO detailDTO = new PseDTO(logica.updatePse(pseId, pse.toEntity()));
        
        return detailDTO;
    }
    
    
    @DELETE
    @Path("{pseId: \\d+}")
    public void deletePse(@PathParam("pseId") Long pseId) throws BusinessLogicException {
        
        if (logica.getPse(pseId) == null) {
            throw new WebApplicationException("El recurso /pse/" + pseId + " no existe.", 404);
        }
        logica.deletePse(pseId);
        
    }
}
