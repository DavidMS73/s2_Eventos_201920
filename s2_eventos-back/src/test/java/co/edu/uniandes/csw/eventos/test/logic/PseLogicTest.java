/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.PseLogic;
import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
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
 * @author Daniel Santiago Leal
 */
@RunWith(Arquillian.class)
public class PseLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PseLogic pseLogic;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PseEntity.class.getPackage())
                .addPackage(PseLogic.class.getPackage())
                .addPackage(PsePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createPseTest() throws BusinessLogicException {

        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setCorreo("germanElNegritoDeOjosClaros@gmail.com");
        PseEntity result = pseLogic.createPse(newEntity);
        Assert.assertNotNull(result);

        PseEntity entity = em.find(PseEntity.class, result.getId());
        entity.setCorreo("germanElNegritoDeOjosClaros@gmail.com");
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
    }

    @Test(expected = BusinessLogicException.class)
    public void createPseCorreoNullTest() throws BusinessLogicException {
        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setCorreo(null);
        pseLogic.createPse(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createPseCorreoSinArrobaTest() throws BusinessLogicException {
        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setCorreo("GermancitoElsexi");
        pseLogic.createPse(newEntity);
    }

}
