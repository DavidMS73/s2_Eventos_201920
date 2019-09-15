/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.ActividadEventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
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
 * @author Germán David Martínez
 */
@RunWith(Arquillian.class)
public class ActividadEventoLogicTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ActividadEventoEntity.class.getPackage())
                .addPackage(ActividadEventoLogic.class.getPackage())
                .addPackage(ActividadEventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ActividadEventoLogic eventoLogic;

    @Test
    public void createActividadEvento() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        ActividadEventoEntity result = eventoLogic.createActividadEvento(newEntity);
        Assert.assertNotNull(result);

        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoNombreNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setNombre(null);
        ActividadEventoEntity result = eventoLogic.createActividadEvento(newEntity);
    }
}
