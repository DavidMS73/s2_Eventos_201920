/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class UsuarioLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());

    @Inject
    private UsuarioPersistence persistence;

    public UsuarioEntity createUsuario(UsuarioEntity usuario) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de un usuario");
        if (usuario.getNombre() == null) {
            throw new BusinessLogicException("El nombre del usuario esta vacío");
        }
        if (usuario.getCorreo() == null) {
            throw new BusinessLogicException("El correo del usuario esta vacío");
        }
        if (usuario.getContrasena() == null) {
            throw new BusinessLogicException("La contraseña del usuario esta vacía");
        }
        if (usuario.getCodigoQR() == null) {
            throw new BusinessLogicException("El codigo QR del usuario es nulo");
        }
        if (usuario.getTipo() == null) {
            throw new BusinessLogicException("El tipo del usuario es nulo");
        }
        if (!usuario.getCorreo().contains("@uniandes.edu.co")) {
            throw new BusinessLogicException("El correo del usuario no es valido");
        }
        if (persistence.findByEmail(usuario.getCorreo()) != null) {
            throw new BusinessLogicException("El correo del usuario ya existe");
        }
        if(persistence.findByUsername(usuario.getUsername()) != null){
            throw new BusinessLogicException("El nombre de usuario ya existe");
        }
        usuario = persistence.create(usuario);
        LOGGER.log(Level.INFO, "Termina proceso de creación del usuario");
        return usuario;
    }

    public List<UsuarioEntity> getUsuarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los usuarios");
        List<UsuarioEntity> usuarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los usuarios");
        return usuarios;
    }

    public UsuarioEntity getUsuario(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = persistence.find(usuariosId);
        if (usuarioEntity == null) {
            LOGGER.log(Level.INFO, "El usuario con el id = {0} no existe", usuariosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con id = {0}", usuariosId);
        return usuarioEntity;
    }
    
    public UsuarioEntity getUsuarioUsername(String pCorreo){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con correo = {0}", pCorreo);
        UsuarioEntity usuarioEntity = persistence.findByUsername(pCorreo);
        if(usuarioEntity == null){
            LOGGER.log(Level.INFO, "El usuario con el correo = {0} no existe", pCorreo);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con el correo = {0}", pCorreo);
        return usuarioEntity;
    }

    public UsuarioEntity updateUsuario(Long usuariosId, UsuarioEntity usuarioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar usuario con id = {0}", usuariosId);
        UsuarioEntity newEntity = persistence.update(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar usuario con id = {0}", usuarioEntity.getId());
        return newEntity;
    }

    public void deleteUsuario(Long usuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el evento con id = {0}", usuariosId);
        List<EventoEntity> eventos = getUsuario(usuariosId).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el usuario con id = " + usuariosId + " porque tiene eventos asociados");
        }
        List<TarjetaEntity> tarjetas = getUsuario(usuariosId).getTarjetas();
        if (tarjetas != null && !tarjetas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el usuario con id  = " + usuariosId + " porque tiene tarjetas registradas.");
        }
        List<PseEntity> pses = getUsuario(usuariosId).getPse();
        if (pses != null && !pses.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el usuario con id  = " + usuariosId + " porque tiene pses registrados.");
        }
        persistence.delete(usuariosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrer el evento con id = {0}", usuariosId);
    }
}
