/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.TarjetaPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implemententa la relación entre Usuario - Tarjetas
 * @author Germán David Martínez Solano
 */
@Stateless
public class UsuarioTarjetasLogic {
    private static final Logger LOGGER = Logger.getLogger(UsuarioTarjetasLogic.class.getName());

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    @Inject
    private TarjetaPersistence tarjetaPersistence;
    
    
    public TarjetaEntity addTarjeta(Long usuariosId, Long tarjetasId){
        LOGGER.log(Level.INFO, "Inicia el proceso de agregar una tarjeta al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        TarjetaEntity tarjetaEntity = tarjetaPersistence.find(usuariosId, tarjetasId);
        tarjetaEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de agregar una tarjeta al usuario con id = {0}", usuariosId);
        return tarjetaEntity;
    }
    
    public void removeTarjeta(Long usuariosId, Long tarjetasId){
        LOGGER.log(Level.INFO, "Inicia el proceso de eliminar una tarjeta al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        TarjetaEntity tarjetaEntity = tarjetaPersistence.find(usuariosId, tarjetasId);
        usuarioEntity.getTarjetas().remove(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de eliminar una tarjeta al usuario con id = {0}", usuariosId);        
    }
}
