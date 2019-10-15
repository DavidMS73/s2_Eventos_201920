/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
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
public class MultimediaPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    UserTransaction userT;

    @PersistenceContext
    EntityManager em;

    @Inject
    MultimediaPersistence mp;
    
    @Inject
    MemoriaPersistence mep;
    
    @Inject
    ActividadEventoPersistence aep;

    private List<MultimediaEntity> data = new ArrayList<MultimediaEntity>();

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
        em.createQuery("delete from MultimediaEntity").executeUpdate();
        em.createQuery("delete from MemoriaEntity").executeUpdate();
        em.createQuery("delete from ActividadEventoEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            MemoriaEntity perteneceAMemoria = factory.manufacturePojo(MemoriaEntity.class);
            ActividadEventoEntity perteneceAActividad = factory.manufacturePojo(ActividadEventoEntity.class);
            em.persist(entity);
            data.add(entity);
            
            entity.setMemoria(perteneceAMemoria);
            entity.setActividadEvento(perteneceAActividad);
        }
    }

    @Test
    public void createMultimediaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity multimedia = factory.manufacturePojo(MultimediaEntity.class);
        MemoriaEntity memoria = factory.manufacturePojo(MemoriaEntity.class);
        ActividadEventoEntity actividad = factory.manufacturePojo(ActividadEventoEntity.class);
        
        memoria = mep.create(memoria);
        memoria.setMultimedia(multimedia);
        
        actividad = aep.create(actividad);
        actividad.setMultimedia(multimedia);
        
        MultimediaEntity result = mp.create(multimedia);

        Assert.assertNotNull(result);

        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());

        Assert.assertEquals(multimedia.getNombre(), entity.getNombre());
        
    }

    @Test
    public void getMultimediasTest() {
        List<MultimediaEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());

        for (MultimediaEntity ent : list) {
            boolean found = false;

            for (MultimediaEntity enti : data) {
                if (ent.getId().equals(enti.getId())) {
                    found = true;
                }
            }

            Assert.assertTrue(found);

        }
    }

    @Test
    public void getMultimediaTest() {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity mulEntity = mp.find(entity.getId());

        Assert.assertNotNull(mulEntity);
        Assert.assertEquals(entity.getNombre(), mulEntity.getNombre());
    }

    @Test
    public void deleteMultimediaTest() {
        MultimediaEntity multimedia = data.get(0);
        mp.delete(multimedia.getId());

        MultimediaEntity deleted = em.find(MultimediaEntity.class, multimedia.getId());
        Assert.assertNull(deleted);

    }

    @Test
    public void updateMultimediaTest() {
        MultimediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity mulEntity = factory.manufacturePojo(MultimediaEntity.class);

        mulEntity.setId(entity.getId());

        mp.update(mulEntity);

        MultimediaEntity resp = em.find(MultimediaEntity.class, entity.getId());

        Assert.assertEquals(mulEntity.getNombre(), resp.getNombre());
    }

    @Test
    public void findMultimediaByNameTest() {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity mulEntity = mp.findByName(entity.getNombre());

        Assert.assertNotNull(mulEntity);
        Assert.assertEquals(entity.getNombre(), mulEntity.getNombre());

        mulEntity = mp.findByName(null);
        Assert.assertNull(mulEntity);
    }

}
