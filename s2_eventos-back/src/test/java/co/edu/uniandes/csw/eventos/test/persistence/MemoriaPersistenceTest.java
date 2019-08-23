/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
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
 * @author Alberic Despres
 */
@RunWith(Arquillian.class)
public class MemoriaPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class).addClass(MemoriaEntity.class).addClass(MemoriaPersistence.class).addAsManifestResource("META-INF/persistence.xml","persistence.xml").addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    MemoriaPersistence mp;
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    @Test
    public void testCreate(){
        
        
        PodamFactory factory = new PodamFactoryImpl();
        MemoriaEntity memoria = factory.manufacturePojo(MemoriaEntity.class);
        MemoriaEntity newMem=mp.create(memoria);
        Assert.assertNotNull(newMem);
        //Assert.assertNotNull(newMem.getId());
        //Assert.assertNotNull(MemoriaEntity.class);
        //Assert.assertNotNull(em);

        MemoriaEntity myEntity = em.find(MemoriaEntity.class, newMem.getId());
        Assert.assertEquals(memoria.getLugar(), myEntity.getLugar());
        Assert.assertEquals(memoria.getFecha(), myEntity.getFecha());
    }
    
}
