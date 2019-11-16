/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PagoLogic {

    private static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());

    @Inject
    private PagoPersistence persistence;

    public PagoEntity createPago(PagoEntity pago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del pago");
        if (pago.getFecha() == null) {
            throw new BusinessLogicException("La fecha del pago está vacio");
        }
        pago = persistence.create(pago);
        LOGGER.log(Level.INFO, "Termina proceso de creación del evento");
        return pago;
    }

    public List<PagoEntity> getPagos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los pagos");
        List<PagoEntity> pagos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los pagos");
        return pagos;
    }

    public PagoEntity getPago(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el pago con id = {0}", id);
        PagoEntity en = persistence.find(id);
        if (en == null) {
            LOGGER.log(Level.SEVERE, "El pago con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el pago con id = {0}", id);

        return en;
    }

    public PagoEntity updatePago(Long id, PagoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el pago con id = {0}", id);
        PagoEntity en = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el pago con id = {0}", entity.getId());
        return en;
    }

    public void deletePago(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el pago con id = {0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el pago con id = {0}", id);
    }

}
