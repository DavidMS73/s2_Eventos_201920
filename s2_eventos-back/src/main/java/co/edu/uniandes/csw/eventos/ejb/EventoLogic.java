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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Germàn David Martìnez Solano
 */
@Stateless
public class EventoLogic {

    @Inject
    private EventoPersistence persistence;

    public Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public EventoEntity createEvento(EventoEntity evento) throws BusinessLogicException {
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
            throw new BusinessLogicException("El número de entradas iniciales debe ser positivo");
        }
        if (evento.getEntradasRestantes() == 0) {
            throw new BusinessLogicException("El número de entradas iniciales debe ser mayor a cero");
        }
        if (evento.getTipo() == null) {
            throw new BusinessLogicException("Debe definir un tipo de evento");
        }
        if (evento.getEsPago() == null) {
            throw new BusinessLogicException("Debe definir si el evento es pago o no");
        }

        evento = persistence.create(evento);
        return evento;
    }
}
