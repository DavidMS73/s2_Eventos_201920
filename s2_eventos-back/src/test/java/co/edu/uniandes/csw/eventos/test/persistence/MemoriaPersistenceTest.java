/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
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
 * @author Alberic Despres
 */
@RunWith(Arquillian.class)
public class MemoriaPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class).addClass(MemoriaEntity.class).addClass(MemoriaPersistence.class).addAsManifestResource("META-INF/persistence.xml", "persistence.xml").addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    UserTransaction utx;

    @Inject
    MemoriaPersistence mp;

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    private List<MemoriaEntity> data = new ArrayList<MemoriaEntity>();

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
        em.createQuery("delete from MemoriaEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MemoriaEntity entity = factory.manufacturePojo(MemoriaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void testCreate() {

        PodamFactory factory = new PodamFactoryImpl();
        MemoriaEntity memoria = factory.manufacturePojo(MemoriaEntity.class);
        MemoriaEntity newMem = mp.create(memoria);
        Assert.assertNotNull(newMem);
        //Assert.assertNotNull(newMem.getId());
        //Assert.assertNotNull(MemoriaEntity.class);
        //Assert.assertNotNull(em);

        MemoriaEntity myEntity = em.find(MemoriaEntity.class, newMem.getId());
        Assert.assertEquals(memoria.getLugar(), myEntity.getLugar());
        Assert.assertEquals(memoria.getFecha(), myEntity.getFecha());

    }

    @Test
    public void getMemoriasTest() {
        List<MemoriaEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MemoriaEntity ent : list) {
            boolean found = false;
            for (MemoriaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity newEntity = mp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLugar(), newEntity.getLugar());
    }

    @Test
    public void deleteMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        mp.delete(entity.getId());
        MemoriaEntity deleted = em.find(MemoriaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);

        newEntity.setId(entity.getId());

        mp.update(newEntity);

        MemoriaEntity resp = em.find(MemoriaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getLugar(), resp.getLugar());
    }

    @Test
    public void findEventoByNameTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity newEntity = mp.findByName(entity.getLugar());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLugar(), newEntity.getLugar());

        newEntity = mp.findByName(null);
        Assert.assertNull(newEntity);
    }
}
