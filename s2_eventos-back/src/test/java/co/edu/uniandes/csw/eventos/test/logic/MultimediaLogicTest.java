/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class MultimediaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MultimediaLogic multimediaLogic;
    
    @Inject
    private UserTransaction utx;
    
    private List<MultimediaEntity> data = new ArrayList<MultimediaEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
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
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData()
    {
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i = 0; i < 3; i++)
        {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createMultimedia() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
        Assert.assertNotNull(result);

        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getUrl(), entity.getUrl());

    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaNombreNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaTipoNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setTipo(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createMultimediaUrlNull() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setTipo(null);
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
    }
    
    @Test
    public void getMultimediasTest()
    {
        List<MultimediaEntity> list = multimediaLogic.getMultimedias();
        Assert.assertEquals(data.size(), list.size());
        for(MultimediaEntity entity : list)
        {
            boolean found = false;
            for(MultimediaEntity stored : data)
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
    public void getMultimediaTest()
    {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity resultEntity = multimediaLogic.getMultimedia(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getTipo(), resultEntity.getTipo());
        Assert.assertEquals(entity.getUrl(), resultEntity.getUrl());
    }
    
    @Test
    public void updateMultimediaTest()
    {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity pojoEntity = factory.manufacturePojo(MultimediaEntity.class);
        pojoEntity.setId(entity.getId());
        multimediaLogic.updateMultimedia(pojoEntity.getId(), pojoEntity);
        MultimediaEntity resp = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    @Test
    public void deleteMultimediaTest()
    {
        MultimediaEntity entity = data.get(1);
        multimediaLogic.deleteMultimedia(entity.getId());
        MultimediaEntity deleted = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
