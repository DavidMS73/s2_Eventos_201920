/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Germàn David Martìnez Solano
 */
@Stateless
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private EventoPersistence persistence;

    public Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public EventoEntity createEvento(EventoEntity evento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del evento");
        if (evento.getNombre() == null) {
            throw new BusinessLogicException("El nombre de evento está vacío");
        }
        if (evento.getCategoria() == null) {
            throw new BusinessLogicException("Debe definir una categoría");
        }
        if (evento.getDescripcion() == null) {
            throw new BusinessLogicException("Debe escribir una descripción");
        }
        if (evento.getFechaInicio() == null) {
            throw new BusinessLogicException("Debe definir una fecha de inicio");
        }
        /**
         * Date fechaComparar = new Date(); Date fechaAdelante =
         * sumarRestarDiasFecha(fechaComparar, 7); if
         * (evento.getFechaInicio().before(fechaAdelante)) { throw new
         * BusinessLogicException("No se puede crear un evento si no tiene una
         * semana de anterioridad"); }
         */
        if (evento.getFechaFin() == null) {
            throw new BusinessLogicException("Debe definir una fecha de fin");
        }
        if (evento.getEntradasRestantes() == null) {
            throw new BusinessLogicException("Debe definir un número de entradas iniciales");
        }
        if (evento.getEntradasRestantes() < 0) {
            throw new BusinessLogicException("El número de entradas restantes debe ser positivo");
        }
        if (evento.getTipo() == null) {
            throw new BusinessLogicException("Debe definir un tipo de evento");
        }
        if (evento.getEsPago() == null) {
            throw new BusinessLogicException("Debe definir si el evento es pago o no");
        }

        evento = persistence.create(evento);
        LOGGER.log(Level.INFO, "Termina proceso de creación del evento");
        return evento;
    }

    public List<EventoEntity> getEventos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos");
        List<EventoEntity> eventos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los eventos");
        return eventos;
    }

    public EventoEntity getEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0}", eventosId);
        EventoEntity eventoEntity = persistence.find(eventosId);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el id = {0} no existe", eventosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la editorial con id = {0}", eventosId);
        return eventoEntity;
    }

    public EventoEntity updateEvento(Long eventosId, EventoEntity eventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", eventosId);
        EventoEntity newEntity = persistence.update(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", eventoEntity.getId());
        return newEntity;
    }

    public void deleteEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", eventosId);
        persistence.delete(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", eventosId);
    }
}
