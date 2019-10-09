/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoMemoriasLogic;
import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class EventoMemoriasLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLogic eventoLogic;
    @Inject
    private EventoMemoriasLogic eventoMemoriasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    private List<MemoriaEntity> memoriasData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            MemoriaEntity memoria = factory.manufacturePojo(MemoriaEntity.class);
            memoria.setEvento(data.get(0));
            em.persist(memoria);
            memoriasData.add(memoria);

        }

    }

    @Test
    public void addMemoriasTest() {
        EventoEntity entity = data.get(0);

        MemoriaEntity bookEntity = memoriasData.get(1);

        MemoriaEntity response = eventoMemoriasLogic.addMemoria(bookEntity.getId(), entity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(bookEntity.getId(), response.getId());
    }

    @Test
    public void getMemoriasTest() {
        List<MemoriaEntity> list = eventoMemoriasLogic.getMemorias(data.get(0).getId());
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void getMemoriaTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        MemoriaEntity bookEntity = memoriasData.get(0);
        MemoriaEntity response = eventoMemoriasLogic.getMemoria(entity.getId(), bookEntity.getId());

        Assert.assertEquals(bookEntity.getId(), response.getId());
        Assert.assertEquals(bookEntity.getEvento(), response.getEvento());
        Assert.assertEquals(bookEntity.getFecha(), response.getFecha());
        Assert.assertEquals(bookEntity.getLugar(), response.getLugar());
    }

    @Test
    public void replaceMemoriasTest() {
        EventoEntity entity = data.get(0);
        List<MemoriaEntity> lista = eventoMemoriasLogic.getMemorias(data.get(0).getId());
        List<MemoriaEntity> list = new ArrayList<>();
        MemoriaEntity r = memoriasData.get(1);
        MemoriaEntity r1 = memoriasData.get(2);
        list.add(r);
        list.add(r1);

        List<MemoriaEntity> list1 = eventoMemoriasLogic.replaceMemorias(entity.getId(), list);
        entity = eventoLogic.getEvento(entity.getId());

        Assert.assertFalse(entity.getMemorias().contains(memoriasData.get(0)));
        Assert.assertTrue(entity.getMemorias().contains(memoriasData.get(1)));
        Assert.assertTrue(entity.getMemorias().contains(memoriasData.get(2)));
    }

    @Test(expected = BusinessLogicException.class)
    public void getMemoriaNoAsociadaTest() throws BusinessLogicException {
        EventoEntity entity = data.get(1);
        MemoriaEntity bookEntity = memoriasData.get(1);
        eventoMemoriasLogic.getMemoria(entity.getId(), bookEntity.getId());
    }
}
