/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
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

    //memorias
    private List<MemoriaDTO> memorias;
    
    private List<PatrocinioDTO> patrocinios;

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
        if (eventoEntity.getMemorias() != null) {
            memorias = new ArrayList<>();
            for (MemoriaEntity memoria : eventoEntity.getMemorias()) {
                memorias.add(new MemoriaDTO(memoria));
            }
        }
        if (eventoEntity.getPatrocinios()!= null) {
            patrocinios = new ArrayList<>();
            for (PatrocinioEntity patrocinio : eventoEntity.getPatrocinios()) {
                patrocinios.add(new PatrocinioDTO(patrocinio));
            }
        }
        
    }

    @Override
    public EventoEntity toEntity() {
        EventoEntity entidad = super.toEntity();
        if (getActividadesEvento() != null) {
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
        if (getMemorias() != null) {
            List<MemoriaEntity> memoriasEntity = new ArrayList<>();
            for(MemoriaDTO mem: getMemorias())
            {
                memoriasEntity.add(mem.toEntity());
            }
            entidad.setMemorias(memoriasEntity);
            
        }
        if (getPatrocinios()!= null) {
            List<PatrocinioEntity> patrociniosEntity = new ArrayList<>();
            for(PatrocinioDTO pat: getPatrocinios())
            {
                patrociniosEntity.add(pat.toEntity());
            }
            entidad.setPatrocinios(patrociniosEntity);
            
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

    /**
     * @return the memorias
     */
    public List<MemoriaDTO> getMemorias() {
        return memorias;
    }

    /**
     * @param memorias the memorias to set
     */
    public void setMemorias(List<MemoriaDTO> memorias) {
        this.memorias = memorias;
    }

    /**
     * @return the patrocinios
     */
    public List<PatrocinioDTO> getPatrocinios() {
        return patrocinios;
    }

    /**
     * @param patrocinios the patrocinios to set
     */
    public void setPatrocinios(List<PatrocinioDTO> patrocinios) {
        this.patrocinios = patrocinios;
    }
}
