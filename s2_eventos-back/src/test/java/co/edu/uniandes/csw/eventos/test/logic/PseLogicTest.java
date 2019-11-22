/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.PseLogic;
import co.edu.uniandes.csw.eventos.entities.PseEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
 * @author Daniel Santiago Leal
 */
@RunWith(Arquillian.class)
public class PseLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();
    
    
    private List<PseEntity> data = new ArrayList<>();
    
    private ArrayList<UsuarioEntity> dataUsuario = new ArrayList<>();

    @Inject
    private PseLogic pseLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PseEntity.class.getPackage())
                .addPackage(PseLogic.class.getPackage())
                .addPackage(PsePersistence.class.getPackage())
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
        em.createQuery("delete from PseEntity").executeUpdate();
        em.createQuery("delete from MedioPagoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }
    
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            dataUsuario.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            PseEntity entity = factory.manufacturePojo(PseEntity.class);
            entity.setUsuario(dataUsuario.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createPseTest() throws BusinessLogicException {

        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setUsuario(dataUsuario.get(1));
        newEntity.setCorreo("germanElNegritoDeOjosClaros@gmail.com");
        PseEntity result = pseLogic.createPse(dataUsuario.get(1).getId(),newEntity);
        Assert.assertNotNull(result);

        PseEntity entity = em.find(PseEntity.class, result.getId());
        entity.setCorreo("germanElNegritoDeOjosClaros@gmail.com");
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
    }

    @Test(expected = BusinessLogicException.class)
    public void createPseCorreoNullTest() throws BusinessLogicException {
        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setCorreo(null);
        pseLogic.createPse(dataUsuario.get(0).getId(),newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createPseCorreoSinArrobaTest() throws BusinessLogicException {
        PseEntity newEntity = factory.manufacturePojo(PseEntity.class);
        newEntity.setCorreo("GermancitoElsexi");
        pseLogic.createPse(dataUsuario.get(0).getId(),newEntity);
    }
    
    
    @Test
    public void getPsesTest() {
        List<PseEntity> list = pseLogic.getPses(dataUsuario.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (PseEntity entity : list) {
            boolean found = false;
            for (PseEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    @Test
    public void getPseTest() {
        PseEntity entity = data.get(0);
        PseEntity resultEntity = pseLogic.getPse(dataUsuario.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCorreo(), resultEntity.getCorreo());
        
    }
    
    @Test
    public void updatePseTest() {
        PseEntity entity = data.get(0);
        PseEntity pojoEntity = factory.manufacturePojo(PseEntity.class);
        pojoEntity.setId(entity.getId());
        pseLogic.updatePse(dataUsuario.get(1).getId(), pojoEntity);
        PseEntity resp = em.find(PseEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
    }
    
    @Test
    public void deletePseTest() throws BusinessLogicException {
        PseEntity entity = data.get(1);
        pseLogic.deletePse(dataUsuario.get(1).getId(),entity.getId());
        PseEntity deleted = em.find(PseEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
