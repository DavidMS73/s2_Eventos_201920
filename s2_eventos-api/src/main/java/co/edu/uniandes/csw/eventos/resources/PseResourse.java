/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PseDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Danisanti Tenjo
 */

@Path("pse")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PseResourse {
    
    private static final Logger LOGGER= Logger.getLogger(PseResourse.class.getName());
    
    @POST
    public PseDTO createPse(PseDTO pse)
    {
     return pse;   
    }
    
}
