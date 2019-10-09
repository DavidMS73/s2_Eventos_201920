/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PagoDTO;
import co.edu.uniandes.csw.eventos.ejb.PagoLogic;
import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 * @author Daniel el travieso
 */

@Path("pagos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {
    
    
@Inject
private PagoLogic logica;


    private List<PagoDTO> listEntity2DTO(List<PagoEntity> entityList) {
        List<PagoDTO> list = new ArrayList<>();
        for (PagoEntity entity : entityList) {
            list.add(new PagoDTO(entity));
        }
        return list;
    }

    @POST
    public PagoDTO crearPago(PagoDTO pago) throws BusinessLogicException
    {
        PagoEntity entity = pago.toEntity();
        entity = logica.createPago(entity);
        PagoDTO nuevoPagoDTO = new PagoDTO(entity);
        return nuevoPagoDTO;
    }


    @GET
    public List<PagoDTO> getPagos() {
        List<PagoDTO> listaPagos = listEntity2DTO(logica.getPagos());
        return listaPagos;
    }

    @GET
    @Path("{pagosId: \\d+}")
    public PagoDTO getPago(@PathParam("pagosId") Long pagosId) {
        PagoEntity entity = logica.getPago(pagosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /pagos/" + pagosId + " no existe.", 404);
        }
        PagoDTO detailDTO = new PagoDTO(entity);
         return detailDTO;
    }
    
    
    @PUT
    @Path("{pagosId: \\d+}")
    public PagoDTO updatePago(@PathParam("pagosId") Long pagosId, PagoDTO pago) {
        pago.setId(pagosId);
        if (logica.getPago(pagosId) == null) {
            throw new WebApplicationException("El recurso /authors/" + pagosId + " no existe.", 404);
        }
        PagoDTO detailDTO = new PagoDTO(logica.updatePago(pagosId, pago.toEntity()));
        return detailDTO;
    }
    
    
    @DELETE
    @Path("{pagosId: \\d+}")
    public void deletePago(@PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        if (logica.getPago(pagosId) == null) {
            throw new WebApplicationException("El recurso /pagos/" + pagosId + " no existe.", 404);
        }
        logica.deletePago(pagosId);
     }
    
}
