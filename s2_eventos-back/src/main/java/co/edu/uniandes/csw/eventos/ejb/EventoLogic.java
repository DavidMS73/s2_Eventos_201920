/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
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

    @Inject
    private UsuarioPersistence up;

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
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0}", eventosId);
        return eventoEntity;
    }

    public EventoEntity updateEvento(Long eventosId, EventoEntity eventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el evento con id = {0}", eventosId);

        EventoEntity newEntity = persistence.update(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el evento con id = {0}", eventoEntity.getId());
        return newEntity;
    }

    public void deleteEvento(Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el evento con id = {0}", eventosId);
        List<LugarEntity> lugares = getEvento(eventosId).getLugares();
        if (lugares != null && !lugares.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene lugares asociados");
        }
        List<MemoriaEntity> memorias = getEvento(eventosId).getMemorias();
        if (memorias != null && !memorias.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene memorias asociadas");
        }
        List<PatrocinioEntity> patrocinios = getEvento(eventosId).getPatrocinios();
        if (patrocinios != null && !patrocinios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene patrocinios asociadas");
        }
        List<UsuarioEntity> inscritos = getEvento(eventosId).getInscritos();
        if (inscritos != null && !inscritos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene inscritos asociados");
        }
        List<UsuarioEntity> invitadosEspeciales = getEvento(eventosId).getInvitadosEspeciales();
        if (invitadosEspeciales != null && !invitadosEspeciales.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene invitados especiales asociados");
        }
        List<ActividadEventoEntity> actividadesEvento = getEvento(eventosId).getActividadesEvento();
        if (actividadesEvento != null && !actividadesEvento.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el evento con id = " + eventosId + " porque tiene actividades evento asociadas");
        }
        persistence.delete(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el evento con id = {0}", eventosId);
    }
}
