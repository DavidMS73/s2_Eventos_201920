/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Betancurth
 */
public class PatrocinioDetailDTO extends PatrocinioDTO implements Serializable {

    private List<EventoDTO> eventos;

    public PatrocinioDetailDTO() {
        super();
    }

    public PatrocinioDetailDTO(PatrocinioEntity patrocinio) {
        super(patrocinio);
        if (patrocinio.getEventos()!= null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEvento : patrocinio.getEventos()) {
                eventos.add(new EventoDTO(entityEvento));
            }
        }
    }
    
     @Override
    public PatrocinioEntity toEntity() 
    {
        PatrocinioEntity patrocinioEntity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : getEventos()) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            patrocinioEntity.setEventos(eventosEntity);
        }
        return patrocinioEntity;
    }

    /**
     * @return the eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
}
