/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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
        if (usuarioEntity != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEventos : usuarioEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEventos));
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
        if (eventos != null) {
            List<EventoEntity> booksEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventos) {
                booksEntity.add(dtoEvento.toEntity());
            }
            usuarioEntity.setEventos(booksEntity);
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
}
