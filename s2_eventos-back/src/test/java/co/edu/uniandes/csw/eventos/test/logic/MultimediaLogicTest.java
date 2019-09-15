/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
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
 * @author Gabriel Jose Gonzalez Pereira
 */
@RunWith(Arquillian.class)
public class MultimediaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MultimediaLogic multimediaLogic;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createMultimedia() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
        Assert.assertNotNull(result);

        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getUrl(), entity.getUrl());

    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaNombreNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaTipoNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setTipo(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaUrlNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setTipo(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }
}
