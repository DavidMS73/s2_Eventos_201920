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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
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

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ActividadEventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ActividadEventoEntity> data = new ArrayList<ActividadEventoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ActividadEventoEntity.class.getPackage())
                .addPackage(ActividadEventoLogic.class.getPackage())
                .addPackage(ActividadEventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ActividadEventoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ActividadEventoEntity entity = factory.manufacturePojo(ActividadEventoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createActividadEvento() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        ActividadEventoEntity result = eventoLogic.createActividadEvento(newEntity);
        Assert.assertNotNull(result);

        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
        Assert.assertEquals(entity.getHoraInicio(), result.getHoraInicio());
        Assert.assertEquals(entity.getHoraFin(), result.getHoraFin());
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoNombreNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setNombre(null);
        eventoLogic.createActividadEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoDescripcionNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setDescripcion(null);
        eventoLogic.createActividadEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoFechaNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setFecha(null);
        eventoLogic.createActividadEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoHoraInicioNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setHoraInicio(null);
        eventoLogic.createActividadEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoHoraFinNull() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setHoraFin(null);
        eventoLogic.createActividadEvento(newEntity);
    }

    @Test
    public void getEventosTest() {
        List<ActividadEventoEntity> list = eventoLogic.getEventos();
        Assert.assertEquals(data.size(), list.size());
        for (ActividadEventoEntity entity : list) {
            boolean found = false;
            for (ActividadEventoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity resultEntity = eventoLogic.getEvento(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
        Assert.assertEquals(entity.getHoraInicio(), resultEntity.getHoraInicio());
        Assert.assertEquals(entity.getHoraFin(), resultEntity.getHoraFin());
    }

    @Test
    public void updateEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity pojoEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        pojoEntity.setId(entity.getId());
        eventoLogic.updateEvento(pojoEntity.getId(), pojoEntity);
        ActividadEventoEntity resp = em.find(ActividadEventoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getHoraInicio(), resp.getHoraInicio());
        Assert.assertEquals(pojoEntity.getHoraFin(), resp.getHoraFin());
    }

    @Test
    public void deleteEventoTest() throws BusinessLogicException {
        ActividadEventoEntity entity = data.get(1);
        eventoLogic.deleteEvento(entity.getId());
        ActividadEventoEntity deleted = em.find(ActividadEventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
