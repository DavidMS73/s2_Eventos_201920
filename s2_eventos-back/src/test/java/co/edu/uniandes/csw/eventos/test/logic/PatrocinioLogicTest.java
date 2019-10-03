/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class PatrocinioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PatrocinioLogic logic;

    @Inject
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

         private List<PatrocinioEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(PatrocinioLogic.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
     
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

   
    /**
     * Prueba para consultar la lista de Authors.
     */
    @Test
    public void getPatrociniosTest() {
        List<PatrocinioEntity> list = logic.getPatrocinios();
        Assert.assertEquals(data.size(), list.size());
        for (PatrocinioEntity entity : list) {
            boolean found = false;
            for (PatrocinioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void createPatrocinio() throws BusinessLogicException {
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        PatrocinioEntity result = logic.createPatrocinio(newEntity);
        Assert.assertNotNull(result);

        PatrocinioEntity entity = em.find(PatrocinioEntity.class, result.getId());
        Assert.assertEquals(entity.getEmpresa(), result.getEmpresa());
    }

    @Test(expected = BusinessLogicException.class)
    public void createPatrocinioEmpresaNull() throws BusinessLogicException {
        PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
        entity.setEmpresa(null);
        PatrocinioEntity result = logic.createPatrocinio(entity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPatrocinioTipoNull() throws BusinessLogicException {
        PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
        entity.setTipo(null);
        PatrocinioEntity result = logic.createPatrocinio(entity);
    }
@Test
    public void updatePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity pojoEntity = factory.manufacturePojo(PatrocinioEntity.class);

        pojoEntity.setId(entity.getId());

        logic.updatePatrocinio(pojoEntity.getId(), pojoEntity);

        PatrocinioEntity resp = em.find(PatrocinioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getEmpresa(), resp.getEmpresa());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
    }


    @Test
    public void deletePatrocinioTest() throws BusinessLogicException {
        PatrocinioEntity entity = data.get(0);
        logic.deletePatrocinio(entity.getId());
        PatrocinioEntity deleted = em.find(PatrocinioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
