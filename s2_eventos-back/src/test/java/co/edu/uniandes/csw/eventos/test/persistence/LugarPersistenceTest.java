/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
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
 * @author Gabriel Jose Gonzalez Pereira
 */
@RunWith(Arquillian.class)
public class LugarPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    UserTransaction userT;

    @PersistenceContext
    EntityManager em;

    @Inject
    LugarPersistence lp;

    private List<LugarEntity> data = new ArrayList<LugarEntity>();

    @Before
    public void setUp() {
        try {
            userT.begin();
            em.joinTransaction();
            clearData();
            insertData();
            userT.commit();

        } catch (Exception e) {
            e.printStackTrace();

            try {
                userT.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from LugarEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createLugarTest() {
        PodamFactory podam = new PodamFactoryImpl();
        LugarEntity lugar = podam.manufacturePojo(LugarEntity.class);
        LugarEntity result = lp.create(lugar);

        Assert.assertNotNull(result);

        LugarEntity entity = em.find(LugarEntity.class, result.getId());
        
        Assert.assertFalse(lugar.equals(null));
        Assert.assertEquals(lugar.hashCode(), entity.hashCode());

        Assert.assertEquals(lugar.getId(), entity.getId());
        Assert.assertEquals(lugar.getSalon(), entity.getSalon());
        Assert.assertEquals(lugar.getPiso(), entity.getPiso());
        Assert.assertEquals(lugar.getBloque(), entity.getBloque());
        Assert.assertEquals(lugar.getCapacidadAsistentes(), entity.getCapacidadAsistentes());
        Assert.assertEquals(lugar.getUbicacionGeografica(), entity.getUbicacionGeografica());
    }

    @Test
    public void getLugaresTest() {
        List<LugarEntity> list = lp.findAll();
        Assert.assertEquals(data.size(), list.size());

        for (LugarEntity ent : list) {
            boolean found = false;

            for (LugarEntity enti : data) {
                if (ent.getId().equals(enti.getId())) {
                    found = true;
                }
            }

            Assert.assertTrue(found);

        }
    }

    @Test
    public void getLugarTest() {
        LugarEntity entity = data.get(0);
        LugarEntity lugEntity = lp.find(entity.getId());

        Assert.assertNotNull(lugEntity);

        Assert.assertEquals(entity.getId(), lugEntity.getId());
        Assert.assertEquals(entity.getSalon(), lugEntity.getSalon());
        Assert.assertEquals(entity.getPiso(), lugEntity.getPiso());
        Assert.assertEquals(entity.getBloque(), lugEntity.getBloque());
        Assert.assertEquals(entity.getCapacidadAsistentes(), lugEntity.getCapacidadAsistentes());
        Assert.assertEquals(entity.getUbicacionGeografica(), lugEntity.getUbicacionGeografica());
    }

    @Test
    public void deleteLugarTest() {
        LugarEntity entity = data.get(0);
        lp.delete(entity.getId());
        LugarEntity deleted = em.find(LugarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateLugarTest() {
        LugarEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity lugEntity = factory.manufacturePojo(LugarEntity.class);

        lugEntity.setId(entity.getId());

        lp.update(lugEntity);

        LugarEntity resp = em.find(LugarEntity.class, entity.getId());
        Assert.assertEquals(lugEntity.getSalon(), resp.getSalon());
        Assert.assertEquals(lugEntity.getPiso(), resp.getPiso());
        Assert.assertEquals(lugEntity.getBloque(), resp.getBloque());
        Assert.assertEquals(lugEntity.getCapacidadAsistentes(), resp.getCapacidadAsistentes());
        Assert.assertEquals(lugEntity.getUbicacionGeografica(), resp.getUbicacionGeografica());
    }

    @Test
    public void findLugarByNameTest() {
        LugarEntity entity = data.get(0);
        LugarEntity newEntity = lp.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = lp.findByName(null);
        Assert.assertNull(newEntity);
    }

}
