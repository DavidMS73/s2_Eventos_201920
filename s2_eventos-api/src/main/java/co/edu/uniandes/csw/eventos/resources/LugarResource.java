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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Alberic Despres
 */
@Path("lugares")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class LugarResource {

    @Inject
    private LugarLogic logica;

    private static final Logger LOGGER = Logger.getLogger(LugarResource.class.getName());
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos LugarEntity a una lista de
     * objetos LugarDTO (json)
     *
     * @param entityList corresponde a la lista de lugares de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de lugares en forma DTO (json)
     */
    private List<LugarDTO> listEntity2DetailDTO(List<LugarEntity> entityList) {
        List<LugarDTO> list = new ArrayList<>();
        for (LugarEntity entity : entityList) {
            list.add(new LugarDTO(entity));
        }
        return list;
    }

    @POST
    public LugarDTO createLugar(LugarDTO lugar) throws BusinessLogicException {

        LugarEntity lugarEntity = lugar.toEntity();
        lugarEntity = logica.createLugar(lugarEntity);
        return new LugarDTO(lugarEntity);
    }
    
    /**
     * Busca y devuelve todos los lugares que existen en la aplicacion.
     *
     * @return JSONArray {@link LugarDTO} - Los lugares encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<LugarDTO> getLugares() {
        LOGGER.info("LugarResource getLugares: input: void");
        List<LugarDTO> listLugares = listEntity2DetailDTO(logica.getLugares());
        LOGGER.log(Level.INFO, "LugarResource getBooks: output: {0}", listLugares);
        return listLugares;
    }
}
