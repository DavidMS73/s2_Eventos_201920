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
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de {@link EventoDTO} para manejar las relaciones entre los
 * Eventos JSON y otros DTOs.
 *
 * @author Germán David Martínez Solano
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    /**
     * Lista de tipo ActividadEventoDTO que contiene las actividades que están
     * asociadas a un evento
     */
    private List<ActividadEventoDTO> actividades;

    private List<LugarDTO> lugares;

    private List<MemoriaDTO> memorias;

    private List<PatrocinioDTO> patrocinios;

    private List<UsuarioDTO> inscritos;

    private List<UsuarioDTO> invitadosEspeciales;

    /**
     * Constructor por defecto
     */
    public EventoDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param eventoEntity entidad del evento para tranformar a DTO
     */
    public EventoDetailDTO(EventoEntity eventoEntity) {
        super(eventoEntity);
        if (eventoEntity != null) {
            if (eventoEntity.getActividadesEvento() != null) {
                actividades = new ArrayList<>();
                for (ActividadEventoEntity entityActividad : eventoEntity.getActividadesEvento()) {
                    actividades.add(new ActividadEventoDTO(entityActividad));
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
            if (eventoEntity.getPatrocinios() != null) {
                patrocinios = new ArrayList<>();
                for (PatrocinioEntity patrocinio : eventoEntity.getPatrocinios()) {
                    patrocinios.add(new PatrocinioDTO(patrocinio));
                }
            }
            if (eventoEntity.getInscritos() != null) {
                inscritos = new ArrayList<>();
                for (UsuarioEntity inscrito : eventoEntity.getInscritos()) {
                    inscritos.add(new UsuarioDTO(inscrito));
                }
            }
            if (eventoEntity.getInvitadosEspeciales() != null) {
                invitadosEspeciales = new ArrayList<>();
                for (UsuarioEntity invitadoE : eventoEntity.getInscritos()) {
                    invitadosEspeciales.add(new UsuarioDTO(invitadoE));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return DTO del evento para tranformar a Entity
     */
    @Override
    public EventoEntity toEntity() {
        EventoEntity entidad = super.toEntity();
        if (actividades != null) {
            List<ActividadEventoEntity> actividadesEntity = new ArrayList<>();
            for (ActividadEventoDTO dtoActividad : actividades) {
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
            for (MemoriaDTO mem : getMemorias()) {
                memoriasEntity.add(mem.toEntity());
            }
            entidad.setMemorias(memoriasEntity);

        }
        if (getPatrocinios() != null) {
            List<PatrocinioEntity> patrociniosEntity = new ArrayList<>();
            for (PatrocinioDTO pat : getPatrocinios()) {
                patrociniosEntity.add(pat.toEntity());
            }
            entidad.setPatrocinios(patrociniosEntity);
        }
        if (getInscritos() != null) {
            List<UsuarioEntity> ins = new ArrayList<>();
            for (UsuarioDTO pat : getInscritos()) {
                ins.add(pat.toEntity());
            }
            entidad.setInscritos(ins);
        }
        if (getInvitadosEspeciales() != null) {
            List<UsuarioEntity> invs = new ArrayList<>();
            for (UsuarioDTO pat : getInvitadosEspeciales()) {
                invs.add(pat.toEntity());
            }
            entidad.setInvitadosEspeciales(invs);
        }
        return entidad;
    }

    /**
     * @return the actividadesEvento
     */
    public List<ActividadEventoDTO> getActividadesEvento() {
        return actividades;
    }

    /**
     * @param actividadesEvento the actividadesEvento to set
     */
    public void setActividadesEvento(List<ActividadEventoDTO> actividadesEvento) {
        this.actividades = actividadesEvento;
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

    /**
     * @return the inscritos
     */
    public List<UsuarioDTO> getInscritos() {
        return inscritos;
    }

    /**
     * @param inscritos the inscritos to set
     */
    public void setInscritos(List<UsuarioDTO> inscritos) {
        this.inscritos = inscritos;
    }

    /**
     * @return the invitadosEspeciales
     */
    public List<UsuarioDTO> getInvitadosEspeciales() {
        return invitadosEspeciales;
    }

    /**
     * @param invitadosEspeciales the invitadosEspeciales to set
     */
    public void setInvitadosEspeciales(List<UsuarioDTO> invitadosEspeciales) {
        this.invitadosEspeciales = invitadosEspeciales;
    }
}
