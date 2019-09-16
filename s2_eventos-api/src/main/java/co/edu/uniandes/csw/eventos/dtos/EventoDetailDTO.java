/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Germán David Martínez Solano
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    private List<ActividadEventoDTO> actividadesEvento;

    public EventoDetailDTO() {
        super();
    }

    public EventoDetailDTO(EventoEntity eventoEntity) {
        super(eventoEntity);
        /*if(eventoEntity != null){
            if(eventoEntity.get)
        }*/
    }

    public EventoEntity toEntity() {
        EventoEntity entidad = super.toEntity();

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
}
