/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class MemoriaLogic {

    @Inject
    private MemoriaPersistence persistence;

    public MemoriaEntity createMemoria(MemoriaEntity memoria) throws BusinessLogicException {

        if (memoria.getLugar() == null || memoria.getFecha() == null) {
            throw new BusinessLogicException("El lugar o la fecha de memoria esta vacio");
        }

        memoria = persistence.create(memoria);
        return memoria;
    }

}
