/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Germán David Martínez Solano
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    private List<ActividadEventoDTO> actividadesEvento;

    private List<LugarDTO> lugares;

    public EventoDetailDTO() {
        super();
    }

    public EventoDetailDTO(EventoEntity eventoEntity) {
        super(eventoEntity);
        if (eventoEntity.getActividadesEvento() != null) {
            actividadesEvento = new ArrayList<>();
            for (ActividadEventoEntity entityReview : eventoEntity.getActividadesEvento()) {
                actividadesEvento.add(new ActividadEventoDTO(entityReview));
            }
        }
        if (eventoEntity.getLugares() != null) {
            lugares = new ArrayList<>();
            for (LugarEntity entityLugar : eventoEntity.getLugares()) {
                lugares.add(new LugarDTO(entityLugar));
            }
        }
    }

    @Override
    public EventoEntity toEntity() {
        EventoEntity entidad = super.toEntity();
        if (actividadesEvento != null) {
            List<ActividadEventoEntity> actividadesEntity = new ArrayList<>();
            for (ActividadEventoDTO dtoActividad : getActividadesEvento()) {
                actividadesEntity.add(dtoActividad.toEntity());
            }
            entidad.setActividadesEvento(actividadesEntity);
        }
        if (getLugares() != null) {
            List<LugarEntity> lugaresEntity = new ArrayList<>();
            for (LugarDTO dtoAuthor : getLugares()) {
                lugaresEntity.add(dtoAuthor.toEntity());
            }
            entidad.setLugares(lugaresEntity);
        }
        return entidad;
    }

    /**
     * @return the actividadesEvento
     */
    public List<ActividadEventoDTO> getActividadesEvento() {
        return actividadesEvento;
    }

    /**
     * @param actividadesEvento the actividadesEvento to set
     */
    public void setActividadesEvento(List<ActividadEventoDTO> actividadesEvento) {
        this.actividadesEvento = actividadesEvento;
    }

    /**
     * @return the lugares
     */
    public List<LugarDTO> getLugares() {
        return lugares;
    }

    /**
     * @param lugares the lugares to set
     */
    public void setLugares(List<LugarDTO> lugares) {
        this.lugares = lugares;
    }
}
