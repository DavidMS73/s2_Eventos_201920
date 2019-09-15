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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Samuel Osorio
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

    @Test
    public void createTarjeta() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        TarjetaEntity result = tarjetaLogic.createTarjeta(newEntity);
        Assert.assertNotNull(result);

        TarjetaEntity entity = em.find(TarjetaEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroTarjeta(), result.getNumeroTarjeta());
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaNumNull() throws BusinessLogicException {
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
    public void createTarjetaTipoNull() throws BusinessLogicException {
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setTipoTarjeta(null);
        TarjetaEntity result = tarjetaLogic.createTarjetaTipoNull(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaCWInvalido() throws BusinessLogicException {
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
}
