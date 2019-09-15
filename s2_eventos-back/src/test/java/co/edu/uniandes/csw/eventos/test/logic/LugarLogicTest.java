/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.LugarLogic;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
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
 * @author Gabriel Jose Gonzalez Pereira
 */
@RunWith(Arquillian.class)
public class LugarLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private LugarLogic lugarLogic;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarLogic.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createLugar() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        LugarEntity result = lugarLogic.createLugar(newEntity);
        Assert.assertNotNull(result);

        LugarEntity entity = em.find(LugarEntity.class, result.getId());
        Assert.assertEquals(newEntity.getSalon(), entity.getSalon());
        Assert.assertEquals(newEntity.getBloque(), entity.getBloque());
        Assert.assertEquals(newEntity.getPiso(), entity.getPiso());
        Assert.assertEquals(newEntity.getUbicacionGeografica(), entity.getUbicacionGeografica());
        Assert.assertEquals(newEntity.getCapacidadAsistentes(), entity.getCapacidadAsistentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createLugarSalonNull() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setSalon(null);
        LugarEntity result = lugarLogic.createLugar(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createLugarBloqueNull() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setBloque(null);
        LugarEntity result = lugarLogic.createLugar(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createLugarPisoNull() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setPiso(null);
        LugarEntity result = lugarLogic.createLugar(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createLugarUbicacionGeograficaNull() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setUbicacionGeografica(null);
        LugarEntity result = lugarLogic.createLugar(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createLugarCapacidad() throws BusinessLogicException {
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setCapacidadAsistentes(0);
        LugarEntity result = lugarLogic.createLugar(newEntity);
    }
}
