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
public class LugarLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private LugarLogic lugarLogic;
    
    @Inject
    private UserTransaction utx;
    
    private List<LugarEntity> data = new ArrayList<LugarEntity>();
    
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
    
    
    @Before
    public void configTest()
    {
        try 
        {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData()
    {
        em.createQuery("delete from LugarEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i = 0; i < 3; i++)
        {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

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
    
    @Test
    public void getLugaresTest()
    {
        List<LugarEntity> list = lugarLogic.getLugares();
        Assert.assertEquals(data.size(), list.size());
        for(LugarEntity entity : list)
        {
            boolean found = false;
            for(LugarEntity stored : data)
            {
                if(entity.getId().equals(stored.getId()))
                {
                    found = true;
                }
            }
            
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getLugarTest()
    {
        LugarEntity entity = data.get(0);
        LugarEntity resultEntity = lugarLogic.getLugar(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getSalon(), resultEntity.getSalon());
        Assert.assertEquals(entity.getPiso(), resultEntity.getPiso());
        Assert.assertEquals(entity.getBloque(), resultEntity.getBloque());
        Assert.assertEquals(entity.getCapacidadAsistentes(), resultEntity.getCapacidadAsistentes());
        Assert.assertEquals(entity.getUbicacionGeografica(), resultEntity.getUbicacionGeografica());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    
    @Test
    public void updateLugarTest()
    {
        LugarEntity entity = data.get(0);
        LugarEntity pojoEntity = factory.manufacturePojo(LugarEntity.class);
        pojoEntity.setId(entity.getId());
        lugarLogic.updateLugar(pojoEntity.getId(), pojoEntity);
        LugarEntity resp = em.find(LugarEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    @Test
    public void deleteLugarTest()
    {
        LugarEntity entity = data.get(1);
        lugarLogic.deleteLugar(entity.getId());
        LugarEntity deleted = em.find(LugarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
