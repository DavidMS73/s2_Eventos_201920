/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Santiago Leal
 */
@Stateless
public class UsuarioPsesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioTarjetasLogic.class.getName());

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    @Inject
    private PsePersistence psePersistence;
    
    
    public PseEntity addPse(Long usuariosId, Long pseId){
        LOGGER.log(Level.INFO, "Inicia el proceso de agregar un pse al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        PseEntity pseEntity = psePersistence.find(usuariosId, pseId);
        pseEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de agregar un pse al usuario con id = {0}", usuariosId);
        return pseEntity;
    }
    
    public void removePse(Long usuariosId, Long pseId){
        LOGGER.log(Level.INFO, "Inicia el proceso de eliminar un pse al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        PseEntity pseEntity = psePersistence.find(usuariosId, pseId);
        usuarioEntity.getPse().remove(pseEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de eliminar un pse al usuario con id = {0}", usuariosId);        
    }
    
    
}

