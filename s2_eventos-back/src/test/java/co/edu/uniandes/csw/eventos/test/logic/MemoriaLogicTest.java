/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MemoriaLogic memoriaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MemoriaEntity> data = new ArrayList<>();

    private List<EventoEntity> dataEvento = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MemoriaEntity.class.getPackage())
                .addPackage(MemoriaLogic.class.getPackage())
                .addPackage(MemoriaPersistence.class.getPackage())
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
        em.createQuery("delete from MemoriaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();

    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            dataEvento.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            MemoriaEntity entity = factory.manufacturePojo(MemoriaEntity.class);
            entity.setEvento(dataEvento.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createMemoria() throws BusinessLogicException {
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        MemoriaEntity result = memoriaLogic.createMemoria(dataEvento.get(1).getId(), newEntity);
        Assert.assertNotNull(result);

        MemoriaEntity entity = em.find(MemoriaEntity.class, result.getId());

        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
        Assert.assertEquals(entity.getLugar(), result.getLugar());
        Assert.assertEquals(entity.getImagen(), result.getImagen());
    }

    @Test(expected = BusinessLogicException.class)
    public void createMemoriaLugarNull() throws BusinessLogicException {
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setLugar(null);
        memoriaLogic.createMemoria(dataEvento.get(1).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMemoriaFechaNull() throws BusinessLogicException {
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setFecha(null);
        memoriaLogic.createMemoria(dataEvento.get(1).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMemoriaImagenNull() throws BusinessLogicException {
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setImagen(null);
        memoriaLogic.createMemoria(dataEvento.get(1).getId(), newEntity);
    }

    @Test
    public void getMemoriasTest() {
        List<MemoriaEntity> list = memoriaLogic.getMemorias(dataEvento.get(1).getId());
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
        MemoriaEntity resultEntity = memoriaLogic.getMemoria(dataEvento.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLugar(), resultEntity.getLugar());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
        Assert.assertEquals(entity.getImagen(), resultEntity.getImagen());
    }

    @Test
    public void updateMemoriaTest() {
        MemoriaEntity entity = data.get(0);
        MemoriaEntity pojoEntity = factory.manufacturePojo(MemoriaEntity.class);
        pojoEntity.setId(entity.getId());

        memoriaLogic.updateMemoria(dataEvento.get(1).getId(), pojoEntity);

        MemoriaEntity resp = em.find(MemoriaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLugar(), resp.getLugar());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getImagen(), resp.getImagen());
    }

    @Test
    public void deleteMemoriaTest() throws BusinessLogicException {
        MemoriaEntity entity = data.get(0);
        memoriaLogic.deleteMemoria(dataEvento.get(1).getId(), entity.getId());
        MemoriaEntity deleted = em.find(MemoriaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test(expected = BusinessLogicException.class)
    public void deleteMemoriaConEventoNoAsociadoTest() throws BusinessLogicException {
        MemoriaEntity entity = data.get(0);
        memoriaLogic.deleteMemoria(dataEvento.get(0).getId(), entity.getId());
    }
}
