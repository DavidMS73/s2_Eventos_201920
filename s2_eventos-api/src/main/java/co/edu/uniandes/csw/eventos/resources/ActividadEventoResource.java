/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.ActividadEventoDTO;
import co.edu.uniandes.csw.eventos.ejb.ActividadEventoLogic;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * Clase que implementa el recurso "actividadesEvento".
 *
 * @author Alberic Despres
 *
 */
@Path("actividadesEvento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ActividadEventoResource {

    private static final Logger LOGGER = Logger.getLogger(ActividadEventoResource.class.getName());

    @Inject
    private ActividadEventoLogic actividadEventoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva actividad con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param actividad {@link ActividadEventoDTO} - La actividad que se desea guardar.
     * @return JSON {@link ActividadEventoDTO} - La actividad guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la actividad o el isbn es
     * inválido o si el evento ingresado es invalido.
     */
    @POST
    public ActividadEventoDTO createActividadEvento(ActividadEventoDTO actividad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividadEvento: input: {0}", actividad);
        ActividadEventoDTO nuevaActividadDTO = new ActividadEventoDTO(actividadEventoLogic.createActividadEvento(actividad.toEntity()));
        LOGGER.log(Level.INFO, "ActividadEventoResource createActividadEvento: output: {0}", nuevaActividadDTO);
        return nuevaActividadDTO;
    }
}