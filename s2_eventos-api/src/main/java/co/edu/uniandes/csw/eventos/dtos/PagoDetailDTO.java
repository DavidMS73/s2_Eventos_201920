/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import java.io.Serializable;

/**
 *
 * @author Danisanti Tenjo
 */
public class PagoDetailDTO extends PagoDTO implements Serializable{

    public PagoDetailDTO() {
        super();
    }

    public PagoDetailDTO(PagoEntity pagoEntity) {
        super(pagoEntity);

    }

    public PagoEntity toEntity() {
        PagoEntity entidad = super.toEntity();

        return entidad;
    }
}
