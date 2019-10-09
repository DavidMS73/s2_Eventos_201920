/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MemoriaEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
public class MemoriaEventoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MemoriaLogic memoriaLogic;
    @Inject
    private MemoriaEventoLogic memoriaEventoLogic;

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
                .addPackage(MemoriaLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
            MemoriaEntity books = factory.manufacturePojo(MemoriaEntity.class);
            em.persist(books);
            memoriasData.add(books);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                memoriasData.get(i).setEvento(entity);
            }
        }
    }
 @Test
    public void replaceEventoTest()
    {
        MemoriaEntity entity = memoriasData.get(0);
        memoriaEventoLogic.replaceEvento(entity.getId(), data.get(1).getId());
        entity = memoriaLogic.getMemoria(entity.getId());
        Assert.assertEquals(entity.getEvento(), data.get(1));
    }
     @Test
    public void removeEventoTest() throws BusinessLogicException {
        memoriaEventoLogic.removeEvento(memoriasData.get(0).getId());
        MemoriaEntity response= memoriaLogic.getMemoria(memoriasData.get(0).getId());
        Assert.assertNull(response.getEvento());
    }
}
