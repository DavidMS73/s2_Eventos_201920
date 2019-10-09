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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

@POST
public PagoDTO crearPago(PagoDTO pago) throws BusinessLogicException
{
        PagoEntity entity = pago.toEntity();
        entity = logica.createPago(entity);
        PagoDTO nuevoPagoDTO = new PagoDTO(entity);
        return nuevoPagoDTO;
}
    
    
    
}
