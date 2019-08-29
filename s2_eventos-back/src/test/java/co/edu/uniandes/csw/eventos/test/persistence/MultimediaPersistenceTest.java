/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
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
public class MultimediaPersistenceTest 
{
    @Deployment
     public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MultimediaEntity.class)
                .addClass(MultimediaPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
     
    @Inject
    UserTransaction userT;
     
    @PersistenceContext
    EntityManager em;

    @Inject
    MultimediaPersistence mp;
    
    private List<MultimediaEntity> data = new ArrayList<MultimediaEntity>();
    
    @Before
    public void setUp()
    {
        try 
        {
            userT.begin();
            em.joinTransaction();
            clearData();
            insertData();
            userT.commit();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            
            try 
            {
                userT.rollback();
            } 
            catch (Exception e1) 
            {
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
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 3; i++)
        {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createMultimediaTest() 
    {
        PodamFactory podam = new PodamFactoryImpl();
        MultimediaEntity multimedia = podam.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity result = mp.create(multimedia);

        Assert.assertNotNull(result);

        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());

        Assert.assertEquals(multimedia.getNombre(), entity.getNombre());
    }
    
    @Test
    public void getMultimediasTest()
    {
        List<MultimediaEntity> list = mp.findAll();
        Assert.assertEquals(data.size(), list.size());
        
        for(MultimediaEntity ent : list)
        {
            boolean found = false;
            
            for(MultimediaEntity enti : data)
            {
                if(ent.getId().equals(enti.getId()))
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
        MultimediaEntity mulEntity = mp.find(entity.getId());
        
        Assert.assertNotNull(mulEntity);
        Assert.assertEquals(entity.getNombre(), mulEntity.getNombre());
    }
    
    
    @Test
    public void updateMultimediaTest()
    {
        MultimediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity mulEntity = factory.manufacturePojo(MultimediaEntity.class);
        
        mulEntity.setId(entity.getId());
        
        mp.update(mulEntity);
        
        MultimediaEntity resp = em.find(MultimediaEntity.class, entity.getId());
        
        Assert.assertEquals(mulEntity.getNombre(), resp.getNombre());
    }
    
    
}
