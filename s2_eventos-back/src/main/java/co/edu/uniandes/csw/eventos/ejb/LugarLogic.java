/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@Stateless
public class LugarLogic {

    @Inject
    private LugarPersistence persistence;

    public LugarEntity createLugar(LugarEntity lugar) throws BusinessLogicException {
        if (lugar.getSalon() == null) {
            throw new BusinessLogicException("El nombre del salon esta vacio");
        }

        if (lugar.getBloque() == null) {
            throw new BusinessLogicException("Debe añadir un bloque");
        }

        if (lugar.getPiso() == null) {
            throw new BusinessLogicException("Debe añadir un piso");
        }

        if (lugar.getUbicacionGeografica() == null) {
            throw new BusinessLogicException("Debe añadir una ubicacion geografica");
        }

        if (lugar.getCapacidadAsistentes() == 0) {
            throw new BusinessLogicException("Debe añadir una capacidad mayor a cero");
        }

        lugar = persistence.create(lugar);
        return lugar;
    }

}
