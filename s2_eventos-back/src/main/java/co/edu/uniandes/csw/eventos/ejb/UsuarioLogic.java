/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class UsuarioLogic {
    
@Inject    
private UsuarioPersistence persistence;

public UsuarioEntity createUsuario(UsuarioEntity usuario) throws BusinessLogicException
{
    if(usuario.getNombre()== null)
    {
        throw new BusinessLogicException("El nombre del usuario esta vacio");
    }
    
    usuario = persistence.create(usuario);
    return usuario;
}
}
