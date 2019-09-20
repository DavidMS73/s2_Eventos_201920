/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.TarjetaLogic;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.TarjetaPersistence;
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
 * @author Samuelillo el pillo
 */
@RunWith(Arquillian.class)
public class TarjetaLogicTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaEntity.class.getPackage())
                .addPackage(TarjetaLogic.class.getPackage())
                .addPackage(TarjetaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TarjetaLogic tarjetaLogic;
    
    @Inject
    private UserTransaction utx;
    
    private ArrayList<TarjetaEntity> data = new ArrayList<>();
    
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
        em.createQuery("delete from TarjetaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
         for (int i = 0; i < 3; i++) {
            TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
            em.persist(tarjeta);
            data.add(tarjeta);
        }       
    }


    @Test
    public void createTarjetaTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        TarjetaEntity result = tarjetaLogic.createTarjeta(newEntity);
        Assert.assertNotNull(result);

        TarjetaEntity entity = em.find(TarjetaEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(entity.getCw(), result.getCw());
        Assert.assertEquals(entity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(entity.getTipoTarjeta(), result.getTipoTarjeta());
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumNullTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta(null);
        TarjetaEntity result = tarjetaLogic.createTarjeta(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumeroInvalidoTest1() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta("1000");
        TarjetaEntity result = tarjetaLogic.createTarjetaNumeroInvalido(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumeroInvalidoTest2() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta("11111111111111111");
        TarjetaEntity result = tarjetaLogic.createTarjetaNumeroInvalido(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTipoNullTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setTipoTarjeta(null);
        TarjetaEntity result = tarjetaLogic.createTarjetaTipoNull(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaCWInvalidoTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setCw(null);
        TarjetaEntity result = tarjetaLogic.createTarjetaCWInvalido(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaExpiracionInvalidaTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setExpiracion(null);
        TarjetaEntity result = tarjetaLogic.createTarjetaExpiracionInvalida(newEntity);
    }
    
    @Test
    public void updateTarjetaTest() throws BusinessLogicException{
        TarjetaEntity entity = data.get(0);
        TarjetaEntity pojoEntity = factory.manufacturePojo(TarjetaEntity.class);
        pojoEntity.setId(entity.getId());
        tarjetaLogic.updateTarjeta(pojoEntity.getId(), pojoEntity);
        TarjetaEntity result = em.find(TarjetaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), result.getId());
        Assert.assertEquals(pojoEntity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(pojoEntity.getCw(), result.getCw());
        Assert.assertEquals(pojoEntity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(pojoEntity.getTipoTarjeta(), result.getTipoTarjeta());
    }
    
    @Test
    public void deleteTarjetaTest(){
        TarjetaEntity result = data.get(0);
        tarjetaLogic.deleteTarjeta(result.getId());
        TarjetaEntity deleted = em.find(TarjetaEntity.class, result.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void getTarjetaTest() throws BusinessLogicException{
        TarjetaEntity entity = data.get(0);
        TarjetaEntity result = tarjetaLogic.getTarjeta(entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(entity.getCw(), result.getCw());
        Assert.assertEquals(entity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(entity.getTipoTarjeta(), result.getTipoTarjeta());
    }
    
    @Test
    public void getTarjetasTest(){
        List<TarjetaEntity> list = tarjetaLogic.getTarjetas();
        Assert.assertEquals(data.size(), list.size());
        for (TarjetaEntity entity : list) {
            boolean found = false;
            for (TarjetaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
