/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MemoriaDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoMemoriasLogic;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "eventos/{id}/memorias".
 * 
 * @author Albéric Despres 
 */
@Path("eventos/{eventosId: \\d+}/memorias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EventoMemoriasResource {
    
    private static final Logger LOGGER = Logger.getLogger(EventoMemoriasResource.class.getName());
   
    @Inject
    private EventoMemoriasLogic eventoMemoriasLogic;
    
    @Inject
    private MemoriaLogic memoriaLogic;
    
    
    /**
     * Convierte una lista de MemoriaEntity a una lista de MemoriaDTO.
     *
     * @param entityList Lista de MemoriaEntity a convertir.
     * @return Lista de MemoriaDTO convertida.
     */
    private List<MemoriaDTO> memoriasEntityToDTO(List<MemoriaEntity> entityList) {
        List<MemoriaDTO> list = new ArrayList();
        for (MemoriaEntity entity : entityList) {
            list.add(new MemoriaDTO(entity));
        }
        return list;
    }
  
    /**
     * Convierte una lista de MemoriaDTO a una lista de MemoriaEntity.
     *
     * @param dtos Lista de MemoriaDTO a convertir.
     * @return Lista de MemoriaEntity convertida.
     */
    private List<MemoriaEntity> memoriasListDTO2Entity(List<MemoriaDTO> dtos) {
        List<MemoriaEntity> list = new ArrayList<>();
        for (MemoriaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
            
    
    
    /**
    * Busca y devuelve todas las memorias que existen en el evento.
    *
    * @param eventosId Identificador del evento que se esta buscando.
    * Este debe ser una cadena de dígitos.
    * @return JSONArray {@link MemoriaDTO} - Las memorias encontrados en el
    * evento. Si no hay ninguno retorna una lista vacía.
    */
    @GET
    public List<MemoriaDTO> getMemorias(@PathParam("eventosId") Long eventosId) {
      LOGGER.log(Level.INFO, "EventoMemoriasResource getMemorias: input: {0}", eventosId);
      List<MemoriaDTO> memoriasDTO = memoriasEntityToDTO(eventoMemoriasLogic.getMemorias(eventosId));
      LOGGER.log(Level.INFO, "EventoMemoriasResource getMemorias: output: {0}", memoriasDTO);
      return memoriasDTO;
    }
    
    /**
    * Busca y devuelve la memoria de un evento.
    *
    * @param eventosId Identificador del evento que se esta buscando.
    * @param memoriasId Identificador de la memoria que se esta buscando.
    * Este deben ser cadenas de dígitos.
    * @return JSON {@link MemoriaDTO} - La memoria encontrada en el
    * evento.
    */
    @GET
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO getMemoria(@PathParam("eventosId") Long eventosId, @PathParam("memoriasId") Long memoriasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoMemoriasResource getMemoria: input: {0}", memoriasId);
        MemoriaEntity memoriaEntity = eventoMemoriasLogic.getMemoria(eventosId, memoriasId);
        if (memoriaEntity == null) {
            throw new WebApplicationException("El recurso /memorias/" + memoriasId + " no existe.", 404);
        }
        MemoriaDTO memoriaDTO = new MemoriaDTO(memoriaEntity);
        LOGGER.log(Level.INFO, "EventoMemoriasResource getMemoria: output: {0}", memoriaDTO);
        return memoriaDTO;
    }
    
    
     /**
     * Aasocia una memoria existante con un evento existente
     *
     * @param eventosId El ID del evento al cual se le va a asociar la memoria
     * @param memoriasId El ID de la memoria a crear y asociar al evento
     * @return JSON {@link MemoriaDTO} - La memoria asociada.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la memoria.
     */
    @POST
    @Path("{memoriasId: \\d+}")
    public MemoriaDTO addMemoria(@PathParam("eventosId") Long eventosId, @PathParam("memoriasId") Long memoriasId) {
        LOGGER.log(Level.INFO, "EventoMemoriasResource addMemoria: input: eventosId {0} , memoriasId {1}", new Object[]{eventosId, memoriasId});
        if (memoriaLogic.getMemoria(memoriasId) == null) {
            throw new WebApplicationException("El recurso /memorias/" + memoriasId + " no existe.", 404);
        }
        MemoriaDTO memoriaDTO = new MemoriaDTO(eventoMemoriasLogic.addMemoria(memoriasId, eventosId));
        LOGGER.log(Level.INFO, "EventoMemoriasResource addMemoria: output: {0}", memoriaDTO);
        return memoriaDTO;
    }
    
     /**
     * Actualiza la lista de memorias de un evento con la lista que se recibe en
     * el cuerpo.
     *
     * @param eventosId El ID del evento al cual se le va a asociar la lista de
     * memorias
     * @param memorias JSONArray {@link MemoriaDTO} - La lista de memorias
     * que se desea guardar.
     * @return JSONArray {@link MemoriaDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra una memoria.
     */
    @PUT
    public List<MemoriaDTO> replaceMemorias(@PathParam("eventosId") Long eventosId, List<MemoriaDTO> memorias) {
        LOGGER.log(Level.INFO, "EventoMemoriasResource replaceMemorias: input: eventosId {0} , memorias {1}", new Object[]{eventosId, memorias});
        for (MemoriaDTO memoria : memorias) {
            if (memoriaLogic.getMemoria(memoria.getId()) == null) {
                throw new WebApplicationException("El recurso /authors/" + memoria.getId() + " no existe.", 404);
            }
        }
        List<MemoriaDTO> lista = memoriasEntityToDTO(eventoMemoriasLogic.replaceMemorias(eventosId, memoriasListDTO2Entity(memorias)));
        LOGGER.log(Level.INFO, "EventoMemoriasResource replaceMemorias: output:{0}", lista);
        return lista;
    }
}
