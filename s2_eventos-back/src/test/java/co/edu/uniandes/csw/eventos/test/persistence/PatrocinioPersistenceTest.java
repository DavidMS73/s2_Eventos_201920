/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
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
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class PatrocinioPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    PatrocinioPersistence pp;

    @PersistenceContext
    EntityManager em;

    @Test
    public void createPatrocinioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity usuario = factory.manufacturePojo(PatrocinioEntity.class);
        PatrocinioEntity result = pp.create(usuario);

        Assert.assertNotNull(result);

        PatrocinioEntity entity = em.find(PatrocinioEntity.class, result.getId());

        Assert.assertEquals(usuario.getEmpresa(), entity.getEmpresa());
    }

    @Inject
    UserTransaction utx;
    private List<PatrocinioEntity> data = new ArrayList<PatrocinioEntity>();

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
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void getPatrociniosTest() {
        List<PatrocinioEntity> list = pp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PatrocinioEntity ent : list) {
            boolean found = false;
            for (PatrocinioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getPatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity newEntity = pp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getEmpresa(), newEntity.getEmpresa());
    }

    @Test
    public void deletePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        pp.delete(entity.getId());
        PatrocinioEntity deleted = em.find(PatrocinioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updatePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);

        newEntity.setId(entity.getId());

        pp.update(newEntity);

        PatrocinioEntity resp = em.find(PatrocinioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getEmpresa(), resp.getEmpresa());
    }

    @Test
    public void findPatrocinioByNameTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity newEntity = pp.findByName(entity.getEmpresa());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getEmpresa(), newEntity.getEmpresa());

        newEntity = pp.findByName(null);
        Assert.assertNull(newEntity);
    }
}
