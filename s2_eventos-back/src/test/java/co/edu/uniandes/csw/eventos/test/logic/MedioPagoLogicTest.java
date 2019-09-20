/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MedioPagoLogic;
import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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

public class MedioPagoLogicTest {

    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioPagoEntity.class.getPackage())
                .addPackage(MedioPagoLogic.class.getPackage())
                .addPackage(MedioPagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml" , "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml" , "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedioPagoLogic medioPagoLogic;
    
    @Inject
    private UserTransaction utx;
    
    private ArrayList<MedioPagoEntity> data = new ArrayList<>();
    
     /**
     * Configuración inicial de la prueba.
     */
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
        em.createQuery("delete from MedioPagoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         for (int i = 0; i < 3; i++) {
            MedioPagoEntity tarjeta = factory.manufacturePojo(MedioPagoEntity.class);
            em.persist(tarjeta);
            data.add(tarjeta);
        }       
    }

    @Test
    public void createMedioPago() throws BusinessLogicException{
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        MedioPagoEntity result = medioPagoLogic.createMedioPago(newEntity);
        Assert.assertNotNull(result);

        MedioPagoEntity entity = em.find(MedioPagoEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroRecibo(), result.getNumeroRecibo());
    }

    @Test (expected = BusinessLogicException.class)
    public void createMedioPagoReciboNullTest() throws BusinessLogicException{
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        newEntity.setNumeroRecibo(null);
        MedioPagoEntity result = medioPagoLogic.createMedioPagoReciboNull(newEntity);
    }
    
    @Test
    public void updateMedioPagoTest() throws BusinessLogicException{
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity pojoEntity = factory.manufacturePojo(MedioPagoEntity.class);
        pojoEntity.setId(entity.getId());
        medioPagoLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
        MedioPagoEntity result = em.find(MedioPagoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), result.getId());
        Assert.assertEquals(pojoEntity.getNumeroRecibo(), result.getNumeroRecibo());
    }
    
    @Test
    public void deleteMedioPagoTest(){
        MedioPagoEntity result = data.get(0);
        medioPagoLogic.deleteMedioPago(result.getId());
        MedioPagoEntity deleted = em.find(MedioPagoEntity.class, result.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void getMedioPagoTest() throws BusinessLogicException{
        MedioPagoEntity entity = data.get(0);
        MedioPagoEntity result = medioPagoLogic.getMedioPago(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getNumeroRecibo(), result.getNumeroRecibo());
    }
    
    @Test
    public void getMediosTest(){
        List<MedioPagoEntity> list = medioPagoLogic.getMedios();
        Assert.assertEquals(data.size(), list.size());
        for (MedioPagoEntity entity : list) {
            boolean found = false;
            for (MedioPagoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}