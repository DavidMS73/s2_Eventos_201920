/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.LugarDTO;
import co.edu.uniandes.csw.eventos.dtos.LugarDetailDTO;
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
    private List<LugarDetailDTO> listEntity2DetailDTO(List<LugarEntity> entityList) {
        List<LugarDetailDTO> list = new ArrayList<>();
        for (LugarEntity entity : entityList) {
            list.add(new LugarDetailDTO(entity));
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
    public List<LugarDetailDTO> getLugares() {
        LOGGER.info("LugarResource getLugares: input: void");
        List<LugarDetailDTO> listLugares = listEntity2DetailDTO(logica.getLugares());
        LOGGER.log(Level.INFO, "LugarResource getBooks: output: {0}", listLugares);
        return listLugares;
    }

    /**
     * Busca el lugar con el id asociado recibido en la URL y lo devuelve.
     *
     * @param lugaresId Identificador del lugar que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link LugarDTO} - El lugar buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el lugar.
     */
    @GET
    @Path("{lugaresId: \\d+}")
    public LugarDetailDTO getLugar(@PathParam("lugaresId") Long lugaresId) {
        LOGGER.log(Level.INFO, "LugarResource getLugar: input: {0}", lugaresId);
        LugarEntity lugarEntity = logica.getLugar(lugaresId);
        if (lugarEntity == null) {
            throw new WebApplicationException("El recurso /lugares/" + lugaresId + " no existe.", 404);
        }
        LugarDetailDTO lugarDTO = new LugarDetailDTO(lugarEntity);
        LOGGER.log(Level.INFO, "LugarResource getLugar: output: {0}", lugarDTO);
        return lugarDTO;
    }

    /**
     * Actualiza el lugar con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param lugaresId Identificador del lugar que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param lugar {@link LugarDTO} El lugar que se desea guardar.
     * @return JSON {@link BookDetailDTO} - El lugar guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el lugar a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el lugar.
     */
    @PUT
    @Path("{lugaresId: \\d+}")
    public LugarDTO updateLugar(@PathParam("lugaresId") Long lugaresId, LugarDTO lugar) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "LugarResource updateLugar: input: id: {0} , book: {1}", new Object[]{lugaresId, lugar});
        lugar.setId(lugaresId);
        if (logica.getLugar(lugaresId) == null) {
            throw new WebApplicationException("El recurso /lugares/" + lugaresId + " no existe.", 404);
        }
        LugarDTO lugarDTO = new LugarDTO(logica.updateLugar(lugaresId, lugar.toEntity()));
        LOGGER.log(Level.INFO, "LugarResource updateLugar: output: {0}", lugarDTO);
        return lugarDTO;
    }

    /**
     * Borra el lugar con el id asociado recibido en la URL.
     *
     * @param lugaresId Identificador del lugar que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * cuando el libro tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el lugar.
     */
    @DELETE
    @Path("{lugaresId: \\d+}")
    public void deleteLugar(@PathParam("lugaresId") Long lugaresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "LugarResource deleteLugar: input: {0}", lugaresId);
        LugarEntity entity = logica.getLugar(lugaresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /lugares/" + lugaresId + " no existe.", 404);
        }
        logica.deleteLugar(lugaresId);
        LOGGER.info("LugarResource deleteLugar: output: void");
    }
}
