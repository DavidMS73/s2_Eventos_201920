/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.TarjetaLogic;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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

    private ArrayList<UsuarioEntity> dataUsuario = new ArrayList<>();

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
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            dataUsuario.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            TarjetaEntity entity = factory.manufacturePojo(TarjetaEntity.class);
            entity.setUsuario(dataUsuario.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createTarjetaTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setUsuario(dataUsuario.get(1));
        TarjetaEntity result = tarjetaLogic.createTarjeta(dataUsuario.get(1).getId(), newEntity);
        Assert.assertNotNull(result);

        TarjetaEntity entity = em.find(TarjetaEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(entity.getCvv(), result.getCvv());
        Assert.assertEquals(entity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(entity.getTipoTarjeta(), result.getTipoTarjeta());
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumNullTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta(null);
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumeroInvalidoTest1() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta("1000");
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumeroInvalidoTest2() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumeroTarjeta("11111111111111111");
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaTipoNullTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setTipoTarjeta(null);
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaCWInvalidoTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setCvv(null);
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaExpiracionInvalidaTest() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setExpiracion(null);
        tarjetaLogic.createTarjeta(dataUsuario.get(0).getId(), newEntity);
    }

    @Test
    public void updateTarjetaTest() throws BusinessLogicException {
        TarjetaEntity entity = data.get(0);
        TarjetaEntity pojoEntity = factory.manufacturePojo(TarjetaEntity.class);
        pojoEntity.setId(entity.getId());
        tarjetaLogic.updateTarjeta(dataUsuario.get(1).getId(), pojoEntity);
        TarjetaEntity result = em.find(TarjetaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), result.getId());
        Assert.assertEquals(pojoEntity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(pojoEntity.getCvv(), result.getCvv());
        Assert.assertEquals(pojoEntity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(pojoEntity.getTipoTarjeta(), result.getTipoTarjeta());
    }

    @Test
    public void deleteTarjetaTest() throws BusinessLogicException {
        TarjetaEntity result = data.get(0);
        tarjetaLogic.deleteTarjeta(dataUsuario.get(1).getId(), result.getId());
        TarjetaEntity deleted = em.find(TarjetaEntity.class, result.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getTarjetaTest() throws BusinessLogicException {
        TarjetaEntity entity = data.get(0);
        TarjetaEntity result = tarjetaLogic.getTarjeta(dataUsuario.get(1).getId(), entity.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(entity.getNumeroTarjeta(), result.getNumeroTarjeta());
        Assert.assertEquals(entity.getCvv(), result.getCvv());
        Assert.assertEquals(entity.getExpiracion(), result.getExpiracion());
        Assert.assertEquals(entity.getTipoTarjeta(), result.getTipoTarjeta());
    }

    @Test
    public void getTarjetasTest() {
        List<TarjetaEntity> list = tarjetaLogic.getTarjetas(dataUsuario.get(1).getId());
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
