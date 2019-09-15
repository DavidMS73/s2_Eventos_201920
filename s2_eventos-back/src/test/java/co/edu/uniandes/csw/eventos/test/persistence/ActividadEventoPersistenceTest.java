/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
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
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class ActividadEventoPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ActividadEventoEntity.class)
                .addClass(ActividadEventoPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    UserTransaction utx;

    @Inject
    private ActividadEventoPersistence ep;

    @PersistenceContext
    private EntityManager em;

    private List<ActividadEventoEntity> data = new ArrayList<ActividadEventoEntity>();

    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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
        ;
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ActividadEventoEntity entity = factory.manufacturePojo(ActividadEventoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createActividadEventoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ActividadEventoEntity evento = factory.manufacturePojo(ActividadEventoEntity.class);
        ActividadEventoEntity result = ep.create(evento);
        Assert.assertNotNull(result);

        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, result.getId());

        Assert.assertEquals(evento.getNombre(), entity.getNombre());
    }

    @Test
    public void getActividadesEventosTest() {
        List<ActividadEventoEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ActividadEventoEntity ent : list) {
            boolean found = false;
            for (ActividadEventoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    @Test
    public void deleteActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ep.delete(entity.getId());
        ActividadEventoEntity deleted = em.find(ActividadEventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        ActividadEventoEntity resp = em.find(ActividadEventoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    @Test
    public void findActividadEventoByNameTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity newEntity = ep.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = ep.findByName(null);
        Assert.assertNull(newEntity);
    }
}
