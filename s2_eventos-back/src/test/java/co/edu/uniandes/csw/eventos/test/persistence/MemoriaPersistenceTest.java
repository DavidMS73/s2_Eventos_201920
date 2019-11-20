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

    @Inject
    UserTransaction utx;

    @Inject
    MemoriaPersistence mp;

    @PersistenceContext
    protected EntityManager em;

    private List<MemoriaEntity> data = new ArrayList<MemoriaEntity>();

    private List<EventoEntity> dataEvento = new ArrayList<EventoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MemoriaEntity.class.getPackage())
                .addPackage(MemoriaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            dataEvento.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            MemoriaEntity entity = factory.manufacturePojo(MemoriaEntity.class);
            if (i == 0) {
                entity.setEvento(dataEvento.get(0));
            }
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

        MemoriaEntity myEntity = em.find(MemoriaEntity.class, newMem.getId());

        Assert.assertFalse(memoria.equals(null));
        Assert.assertEquals(memoria.hashCode(), myEntity.hashCode());

        Assert.assertEquals(memoria.getId(), myEntity.getId());
        Assert.assertEquals(memoria.getLugar(), myEntity.getLugar());
        Assert.assertEquals(memoria.getFecha(), myEntity.getFecha());
        Assert.assertEquals(memoria.getImagen(), myEntity.getImagen());
    }

    @Test
    public void getMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity newEntity = mp.find(dataEvento.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getLugar(), newEntity.getLugar());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
        Assert.assertEquals(entity.getImagen(), newEntity.getImagen());
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

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getLugar(), resp.getLugar());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getImagen(), resp.getImagen());
    }
}
