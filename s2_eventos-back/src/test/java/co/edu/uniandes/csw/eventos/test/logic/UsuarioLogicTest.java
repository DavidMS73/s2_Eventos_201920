/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);

        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getIdentificador(), result.getIdentificador());
        Assert.assertEquals(entity.getEmpresa(), result.getEmpresa());
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
        Assert.assertEquals(entity.getContrasena(), result.getContrasena());
        Assert.assertEquals(entity.getCodigoQR(), result.getCodigoQR());
        Assert.assertEquals(entity.getAsiste(), result.getAsiste());
        
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioNombreNullTest() throws BusinessLogicException {
        UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
        entity.setNombre(null);
        usuarioLogic.createUsuario(entity);
    }
    @Test (expected = BusinessLogicException.class)
    public void createUsuarioIdentificadorNull() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setIdentificador(null);
        usuarioLogic.createUsuario(newEntity);
    }
    @Test (expected = BusinessLogicException.class)
    public void createUsuarioIdentificadorNegativo() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setIdentificador(-200L);
        usuarioLogic.createUsuario(newEntity);
    }
     @Test (expected = BusinessLogicException.class)
    public void createUsuarioCorreoNull() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreo(null);
        usuarioLogic.createUsuario(newEntity);
    }
    @Test (expected = BusinessLogicException.class)
    public void createUsuarioCorreoNoUniandes() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreo("juan@hotmail.com");
        usuarioLogic.createUsuario(newEntity);
    }
    public void createUsuarioContrasena() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setContrasena(null);
        usuarioLogic.createUsuario(newEntity);
    }
    public void createUsuarioAsisteNull() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setAsiste(null);
        usuarioLogic.createUsuario(newEntity);
    }
    public void createUsuarioCodigoQRNull() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCodigoQR(null);
        usuarioLogic.createUsuario(newEntity);
    }
    public void createUsuarioEmpresaNull() throws BusinessLogicException{
        
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setEmpresa(null);
        usuarioLogic.createUsuario(newEntity);
    }
}
