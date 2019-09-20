/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class MemoriaLogicTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MemoriaEntity.class.getPackage())
                .addPackage(MemoriaLogic.class.getPackage())
                .addPackage(MemoriaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MemoriaLogic memoriaLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<MemoriaEntity> data = new ArrayList<MemoriaEntity>();
    
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
        em.createQuery("delete from MemoriaEntity").executeUpdate();
    }
    
     private void insertData() {
        for (int i = 0; i < 3; i++) {
            MemoriaEntity entity = factory.manufacturePojo(MemoriaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createMemoria() throws BusinessLogicException {

        MemoriaEntity newMemoria = factory.manufacturePojo(MemoriaEntity.class);
        MemoriaEntity result = memoriaLogic.createMemoria(newMemoria);
        Assert.assertNotNull(result);

        MemoriaEntity entity = em.find(MemoriaEntity.class, result.getId());
        Assert.assertEquals(entity.getLugar(), result.getLugar());
        Assert.assertEquals(entity.getFecha(), result.getFecha());

    }

    @Test(expected = BusinessLogicException.class)
    public void createMemoriaLugarNull() throws BusinessLogicException {

        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setLugar(null);
        MemoriaEntity result = memoriaLogic.createMemoria(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMemoriaFechaNull() throws BusinessLogicException {

        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setFecha(null);
        MemoriaEntity result = memoriaLogic.createMemoria(newEntity);
    }
    
    @Test
    public void getMemoriasTest() {
        List<MemoriaEntity> list = memoriaLogic.getMemorias();
        Assert.assertEquals(data.size(), list.size());
        for (MemoriaEntity entity : list) {
            boolean found = false;
            for (MemoriaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity resultEntity = memoriaLogic.getMemoria(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLugar(), resultEntity.getLugar());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
    }

    @Test
    public void updateMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity pojoEntity = factory.manufacturePojo(MemoriaEntity.class);
        pojoEntity.setId(entity.getId());
        memoriaLogic.updateMemoria(pojoEntity.getId(), pojoEntity);
        MemoriaEntity resp = em.find(MemoriaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLugar(), resp.getLugar());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
    }

    @Test
    public void deleteMemoriaTest() throws BusinessLogicException {
        MemoriaEntity entity = data.get(1);
        memoriaLogic.deleteMemoria(entity.getId());
        MemoriaEntity deleted = em.find(MemoriaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
