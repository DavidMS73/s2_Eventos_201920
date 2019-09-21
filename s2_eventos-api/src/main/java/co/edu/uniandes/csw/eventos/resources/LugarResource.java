/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.LugarDTO;
import co.edu.uniandes.csw.eventos.ejb.LugarLogic;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */

@Path("lugar")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class LugarResource 
{
    @Inject
    private LugarLogic logic;
    
    private static final Logger LOGGER = Logger.getLogger(LugarResource.class.getName());
    
    
    
}