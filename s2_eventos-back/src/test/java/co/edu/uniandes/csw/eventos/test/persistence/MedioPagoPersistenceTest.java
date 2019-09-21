/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import co.edu.uniandes.csw.eventos.persistence.MedioPagoPersistence;
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
 * @author Samuelillo el pillo.
 */
@RunWith(Arquillian.class)
public class MedioPagoPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioPagoEntity.class.getPackage())
                .addPackage(MedioPagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

     @Inject
    UserTransaction utx;

    @Inject
    private MedioPagoPersistence ep;

    @PersistenceContext
    private EntityManager em;

    private List<MedioPagoEntity> data = new ArrayList<MedioPagoEntity>();

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
        em.createQuery("delete from MedioPagoEntity").executeUpdate();
    }

     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MedioPagoEntity entity = factory.manufacturePojo(MedioPagoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

      @Test
    public void createMedioPagoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MedioPagoEntity medioPago = factory.manufacturePojo(MedioPagoEntity.class);
        MedioPagoEntity result = ep.create(medioPago);
        Assert.assertNotNull(result);

        MedioPagoEntity entity = em.find(MedioPagoEntity.class, result.getId());

        Assert.assertEquals(medioPago.getNumeroRecibo(), entity.getNumeroRecibo());
    }

    @Test
    public void deleteMedioPagoTest() {
        MedioPagoEntity entity = data.get(0);
        ep.delete(entity.getId());
        MedioPagoEntity deleted = em.find(MedioPagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getMedioPagoTest() {
        List<MedioPagoEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(MedioPagoEntity ent : list) {
            boolean found = false;
            for (MedioPagoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getEventoTest(){
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity); 
        Assert.assertEquals(entity.getNumeroRecibo(), newEntity.getNumeroRecibo());
    }

    @Test
    public void updateMedioPagoTest() {
        MedioPagoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        MedioPagoEntity resp = em.find(MedioPagoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumeroRecibo(), resp.getNumeroRecibo());
    }
    
     @Test
    public void findTarjetaByNameNumber() {
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity newEntity = ep.findByNumber(entity.getNumeroRecibo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroRecibo(), newEntity.getNumeroRecibo());

        newEntity = ep.findByNumber(null);
        Assert.assertNull(newEntity);
    }
}