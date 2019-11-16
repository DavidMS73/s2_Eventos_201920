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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexión con la persistencia para la entidad de
 * Evento.
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class EventoLogic {

    /**
     * Logger del evento
     */
    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    /**
     * Persistencia del evento
     */
    @Inject
    private EventoPersistence persistence;

    /**
     * Fecha 7 días adelante para comparar con la del evento
     *
     * @return Fecha 7 días adelante
     */
    public Date fecha7Adelante() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }

    /**
     * Crea un evento en la persistencia
     *
     * @param evento La entidad que representa el evento a persistir
     * @return Entidad del evento luego de persistirla
     * @throws BusinessLogicException En caso de fallar una de las reglas de
     * negocio
     */
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
        if (evento.getImagen() == null) {
            throw new BusinessLogicException("Debe definir una imagen");
        }

        if (evento.getFechaInicio().before(fecha7Adelante())) {
            throw new BusinessLogicException("No se puede crear un evento si no tiene una "
                    + "semana de anterioridad");
        }

        if (evento.getFechaFin() == null) {
            throw new BusinessLogicException("Debe definir una fecha de fin");
        }

        java.util.Date fecha = new Date();
        if (evento.getFechaInicio().before(fecha)) {
            throw new BusinessLogicException("La fecha de inicio no puede ser anterior a la actual");
        }
        if (evento.getFechaInicio().after(evento.getFechaFin())) {
            throw new BusinessLogicException("La fecha de inicio no puede ser después de la de fin");
        }
        if (evento.getEntradasRestantes() == null) {
            throw new BusinessLogicException("Debe definir un número de entradas iniciales");
        }
        if (evento.getEntradasRestantes() < 0) {
            throw new BusinessLogicException("El número de entradas restantes debe ser positivo");
        }
        if (evento.getValor() < 0) {
            throw new BusinessLogicException("El valor del evento debe ser positivo");
        }
        evento = persistence.create(evento);
        LOGGER.log(Level.INFO, "Termina proceso de creación del evento");
        return evento;
    }

    /**
     * Obtener todos los eventos existentes en la base de datos
     *
     * @return lsita de eventos
     */
    public List<EventoEntity> getEventos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos");
        List<EventoEntity> eventos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los eventos");
        return eventos;
    }

    /**
     * Obtener un evento por medio de su Id
     *
     * @param eventosId: id del evento para ser buscado
     * @return el evento solicitado por medio de su id
     */
    public EventoEntity getEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0}", eventosId);
        EventoEntity eventoEntity = persistence.find(eventosId);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el id = {0} no existe", eventosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0}", eventosId);
        return eventoEntity;
    }

    /**
     * Actualiza un evento
     *
     * @param eventosId: id del evento para buscarlo en la base de datos
     * @param eventoEntity: eventos con los cambios para ser actualizado
     * @return el evento con los cambios actualizados en la base de datos
     */
    public EventoEntity updateEvento(Long eventosId, EventoEntity eventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el evento con id = {0}", eventosId);
        EventoEntity newEntity = persistence.update(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el evento con id = {0}", eventoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un evento
     *
     * @param eventosId: id del evento a borrar
     * @throws BusinessLogicException si el evento a borrar tiene actividades,
     * memorias, patrocinios, usuarios y lugares asociadoss
     */
    public void deleteEvento(Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el evento con id = {0}", eventosId);
        String errorMeg = "No se puede borrar el evento con id = ";
        List<LugarEntity> lugares = getEvento(eventosId).getLugares();
        if (lugares != null && !lugares.isEmpty()) {
            throw new BusinessLogicException(errorMeg + eventosId + " porque tiene lugares asociados");
        }
        List<MemoriaEntity> memorias = getEvento(eventosId).getMemorias();
        if (memorias != null && !memorias.isEmpty()) {
            throw new BusinessLogicException(errorMeg + eventosId + " porque tiene memorias asociadas");
        }
        List<PatrocinioEntity> patrocinios = getEvento(eventosId).getPatrocinios();
        if (patrocinios != null && !patrocinios.isEmpty()) {
            throw new BusinessLogicException(errorMeg + eventosId + " porque tiene patrocinios asociadas");
        }
        List<UsuarioEntity> usuarios = getEvento(eventosId).getUsuarios();
        if (usuarios != null && !usuarios.isEmpty()) {
            throw new BusinessLogicException(errorMeg + eventosId + " porque tiene usuarios asociados");
        }
        List<ActividadEventoEntity> actividadesEvento = getEvento(eventosId).getActividadesEvento();
        if (actividadesEvento != null && !actividadesEvento.isEmpty()) {
            throw new BusinessLogicException(errorMeg + eventosId + " porque tiene actividades asociadas");
        }
        persistence.delete(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el evento con id = {0}", eventosId);
    }
}
