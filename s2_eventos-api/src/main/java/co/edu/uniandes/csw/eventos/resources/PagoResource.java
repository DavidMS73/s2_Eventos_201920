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
 * @author Daniel
 */
@Path("pagos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {

    private static final Logger LOGGER = Logger.getLogger(PagoResource.class.getName());

    @Inject
    private PagoLogic logica;

    /**
     * Parte del mensaje
     */
    private String msg1 = "El recurso /pagos/";

    /**
     * Parte del mensaje
     */
    private String msg2 = " no existe.";

    private List<PagoDTO> listEntity2DTO(List<PagoEntity> entityList) {
        List<PagoDTO> list = new ArrayList<>();
        for (PagoEntity entity : entityList) {
            list.add(new PagoDTO(entity));
        }
        return list;
    }

    @POST
    public PagoDTO crearPago(PagoDTO pago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagoResource crearPago: input: {0}", pago);
        PagoEntity entity = pago.toEntity();
        entity = logica.createPago(entity);
        PagoDTO nuevoPagoDTO = new PagoDTO(entity);
        LOGGER.log(Level.INFO, "PagoResource crearPago: output: {0}", nuevoPagoDTO);
        return nuevoPagoDTO;
    }

    @GET
    public List<PagoDTO> getPagos() {
        LOGGER.info("PagoResource getPagos: input: void");
        List<PagoDTO> listaPagos = listEntity2DTO(logica.getPagos());
        LOGGER.log(Level.INFO, "PagoResource getPagos: output: {0}", listaPagos);
        return listaPagos;
    }

    @GET
    @Path("{pagosId: \\d+}")
    public PagoDTO getPago(@PathParam("pagosId") Long pagosId) {
        LOGGER.log(Level.INFO, "PagoResource getPago: input: {0}", pagosId);
        PagoEntity entity = logica.getPago(pagosId);
        if (entity == null) {
            throw new WebApplicationException(msg1 + pagosId + msg2, 404);
        }
        PagoDTO detailDTO = new PagoDTO(entity);
        LOGGER.log(Level.INFO, "PagoResource getPago: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{pagosId: \\d+}")
    public PagoDTO updatePago(@PathParam("pagosId") Long pagosId, PagoDTO pago) {
        LOGGER.log(Level.INFO, "PagoResource updatePago: input: pagosId: {0} , pago: {1}", new Object[]{pagosId, pago});
        pago.setId(pagosId);
        if (logica.getPago(pagosId) == null) {
            throw new WebApplicationException(msg1 + pagosId + msg2, 404);
        }
        PagoDTO detailDTO = new PagoDTO(logica.updatePago(pagosId, pago.toEntity()));
        LOGGER.log(Level.INFO, "PagoResource updatePago: output: {0}", detailDTO);
        return detailDTO;
    }

    @DELETE
    @Path("{pagosId: \\d+}")
    public void deletePago(@PathParam("pagosId") Long pagosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagoResource deletePago: input: {0}", pagosId);
        if (logica.getPago(pagosId) == null) {
            throw new WebApplicationException(msg1 + pagosId + msg2, 404);
        }
        logica.deletePago(pagosId);
        LOGGER.info("PagoResource deletePago: output: void");
    }

}
