/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Germán David Martínez
 */
@Stateless
public class ActividadEventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private ActividadEventoPersistence persistence;

    public ActividadEventoEntity createActividadEvento(ActividadEventoEntity actividad) throws BusinessLogicException {
        if (actividad.getNombre() == null) {
            throw new BusinessLogicException("El nombre de la actividad está vacío");
        }
        if (actividad.getDescripcion() == null) {
            throw new BusinessLogicException("Debe escribir una descripción");
        }

        actividad = persistence.create(actividad);
        return actividad;
    }
}
