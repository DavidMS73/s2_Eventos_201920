/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class PatrocinioLogic {
    @Inject    
private PatrocinioPersistence persistence;

public PatrocinioEntity createUsuario(PatrocinioEntity patrocinio) throws BusinessLogicException
{
    if(patrocinio.getEmpresa()== null)
    {
        throw new BusinessLogicException("El patrocinio no tiene empresa");
    }
    
    
    patrocinio = persistence.create(patrocinio);
    return patrocinio;
}
}
