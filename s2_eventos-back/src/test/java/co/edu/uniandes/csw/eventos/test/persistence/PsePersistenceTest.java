/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.persistence.PsePersistence;
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
 * @author Daniel Santiago Tenjo
 */
@RunWith(Arquillian.class)
public class PsePersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PseEntity.class)
                .addClass(PsePersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Inject
    UserTransaction utx;

    @Inject
    private PsePersistence ep;

    @PersistenceContext
    private EntityManager em;

    private List<PseEntity> data = new ArrayList<PseEntity>();

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
        em.createQuery("delete from PseEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PseEntity entity = factory.manufacturePojo(PseEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createPseTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PseEntity pse = factory.manufacturePojo(PseEntity.class);
        PseEntity result = ep.create(pse);
        Assert.assertNotNull(result);

        PseEntity entity = em.find(PseEntity.class, result.getId());

        Assert.assertEquals(pse.getCorreo(), entity.getCorreo());
    }

    /**
     * @Test public void getPsesTest() { List<PseEntity> list = ep.findAll();
     * Assert.assertEquals(data.size(), list.size()); for(PseEntity ent : list)
     * { boolean found = false; for (PseEntity entity : data) { if
     * (ent.getId().equals(entity.getId())) { found = true; } }
     * Assert.assertTrue(found); } }
     */
    @Test
    public void getPseTest() {
        PseEntity entity = data.get(0);
        PseEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCorreo(), newEntity.getCorreo());
    }

    @Test
    public void deletePseTest() {
        PseEntity entity = data.get(0);
        ep.delete(entity.getId());
        PseEntity deleted = em.find(PseEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updatePseTest() {
        PseEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        PseEntity resp = em.find(PseEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
    }

    /**
     * @Test public void findPseByNameTest() { PseEntity entity = data.get(0);
     * PseEntity newEntity = ep.findByName(entity.getCorreo());
     * Assert.assertNotNull(newEntity); Assert.assertEquals(entity.getCorreo(),
     * newEntity.getCorreo());
     *
     * newEntity = ep.findByName(null); Assert.assertNull(newEntity); }
     */
}
