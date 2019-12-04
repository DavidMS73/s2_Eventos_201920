/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de {@link UsuarioDTO} para manejar las relaciones entre
 * UsuarioDTO y otros DTO
 *
 * @author Germán David Martínez Solano
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable {

    /**
     * Lista de eventos
     */
    private List<EventoDTO> eventos;

    /**
     * Lista de tipo TarjetaDTO que contiene las tarjetas que están asociadas a
     * un usuario
     */
    private List<TarjetaDTO> tarjetas;

    /**
     * Lista de tipo PSEDTO que contiene los correos PSE que están asociados a
     * un usuario
     */
    private List<PseDTO> pse;

    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO() {
        super();
    }

    /**
     * Crea un objeto UsuarioDetailDTO a partir de un objeto UsuarioEntity
     * incluyendo los atributos de UsuarioDTO.
     *
     * @param usuarioEntity Entidad UsuarioEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEventos : usuarioEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEventos));
            }
        }
        if (usuarioEntity.getTarjetas() != null) {
            tarjetas = new ArrayList<>();
            for (TarjetaEntity entityTarjeta : usuarioEntity.getTarjetas()) {
                tarjetas.add(new TarjetaDTO(entityTarjeta));
            }
        }
        if (usuarioEntity.getPse() != null) {
            pse = new ArrayList<>();
            for (PseEntity entityPse : usuarioEntity.getPse()) {
                pse.add(new PseDTO(entityPse));
            }
        }
    }

    /**
     * Convierte un objeto UsuarioDetailDTO a UsuarioEntity incluyendo los
     * atributos de UsuarioDTO.
     *
     * @return Nuevo objeto UsuarioEntity.
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        if (getEventos() != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : getEventos()) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            usuarioEntity.setEventos(eventosEntity);
        }
        if (getTarjetas() != null) {
            List<TarjetaEntity> tarjetasEntity = new ArrayList<>();
            for (TarjetaDTO dtoTarjeta : getTarjetas()) {
                tarjetasEntity.add(dtoTarjeta.toEntity());
            }
            usuarioEntity.setTarjetas(tarjetasEntity);
        }
        if (getPse() != null) {
            List<PseEntity> pseEntity = new ArrayList<>();
            for (PseDTO dtoPse : getPse()) {
                pseEntity.add(dtoPse.toEntity());
            }
            usuarioEntity.setPse(pseEntity);
        }
        return usuarioEntity;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the tarjetas
     */
    public List<TarjetaDTO> getTarjetas() {
        return tarjetas;
    }

    /**
     * @param tarjetas the tarjetas to set
     */
    public void setTarjetas(List<TarjetaDTO> tarjetas) {
        this.tarjetas = tarjetas;
    }

    /**
     * @return the pse
     */
    public List<PseDTO> getPse() {
        return pse;
    }

    /**
     * @param pse the pse to set
     */
    public void setPse(List<PseDTO> pse) {
        this.pse = pse;
    }
}
