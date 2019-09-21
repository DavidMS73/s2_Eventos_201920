/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Stateless
public class MultimediaLogic 
{

    private static final Logger LOGGER = Logger.getLogger(MultimediaLogic.class.getName());
    
    @Inject
    private MultimediaPersistence persistence;

    public MultimediaEntity createMultimedia(MultimediaEntity multimedia) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Se comienza la creacion de multimedias");
        if (multimedia.getNombre() == null) {
            throw new BusinessLogicException("El nombre de la multimedia esta vacio");
        }

        if (multimedia.getTipo() == null) {
            throw new BusinessLogicException("La multimedia debe tener un tipo");
        }

        if (multimedia.getUrl() == null) {
            throw new BusinessLogicException("Debe añadir un URL para la multimedia");
        }

        multimedia = persistence.create(multimedia);
        LOGGER.log(Level.INFO,"Se termina la creacion de multimedias");
        return multimedia;
    }
    
    public List<MultimediaEntity> getMultimedias()
    {
        LOGGER.log(Level.INFO, "Se empieza el proceso de buscar multimedias");
        List<MultimediaEntity> multimedias = persistence.findAll();
        LOGGER.log(Level.INFO, "Se termina el proceso de buscar multimedias");
        return multimedias;
        
    }

    public MultimediaEntity getMultimedia(Long id)
    {
        LOGGER.log(Level.INFO, "Se empieza la busqueda de la multimedia con id = (0)", id);
        MultimediaEntity en = persistence.find(id);
        
        if(id == null)
        {
            LOGGER.log(Level.INFO, "No existe multimedia con id = (0)");
        }
        
        LOGGER.log(Level.INFO, "Termina el proceso de consultar la multimedia con id = (0)");
        return en;
    }
    
    public MultimediaEntity updateMultimedia(Long id, MultimediaEntity entity)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de actualizar multimedia con id = (0)", id);
        MultimediaEntity en = persistence.update(entity);
        LOGGER.log(Level.INFO, "SE termina la actualizacion de la multimedia con id = (0)", entity.getId());
        return en;
    }
    
    public void deleteMultimedia(Long id)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de borrar la mutlimedia con id = (0)", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina el proceso de eliminar la multimedia con id = (0)", id);
    }
}
