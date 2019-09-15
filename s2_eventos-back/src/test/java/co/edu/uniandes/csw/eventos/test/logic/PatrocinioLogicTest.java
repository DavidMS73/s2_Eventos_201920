/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
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
public class PatrocinioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PatrocinioLogic usuarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(PatrocinioLogic.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createUsuario() throws BusinessLogicException {
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        PatrocinioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);

        PatrocinioEntity entity = em.find(PatrocinioEntity.class, result.getId());
        Assert.assertEquals(entity.getEmpresa(), result.getEmpresa());
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioNombreNull() throws BusinessLogicException {
        PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
        entity.setEmpresa(null);
        PatrocinioEntity result = usuarioLogic.createUsuario(entity);
    }

}
