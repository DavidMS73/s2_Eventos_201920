/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Alberic Despres
 */
public class LugarDetailDTO extends LugarDTO implements Serializable {

    private List<EventoDTO> eventos;

    public LugarDetailDTO() {
        super();
    }

    public LugarDetailDTO(LugarEntity lugarEntity) {
        super(lugarEntity);
        if (lugarEntity.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityReview : lugarEntity.getEventos()) {
                eventos.add(new EventoDTO(entityReview));
            }
        }
    }

    @Override
    public LugarEntity toEntity() {
        LugarEntity entidad = super.toEntity();
        if (getEventos() != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : getEventos()) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            entidad.setEventos(eventosEntity);
        }
        return entidad;
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
