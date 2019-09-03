/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MemoriaLogic;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class MemoriaLogicTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MemoriaEntity.class.getPackage())
                .addPackage(MemoriaLogic.class.getPackage())
                .addPackage(MemoriaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private MemoriaLogic memoriaLogic; 
    
    @PersistenceContext
    private EntityManager em;
    
    @Test
    public void createMemoria() throws BusinessLogicException{
        
        MemoriaEntity newMemoria = factory.manufacturePojo(MemoriaEntity.class);
        MemoriaEntity result = memoriaLogic.createMemoria(newMemoria);
        Assert.assertNotNull(result);
        
        MemoriaEntity entity = em.find(MemoriaEntity.class, result.getId());
        Assert.assertEquals(entity.getLugar(), result.getLugar());
        Assert.assertEquals(entity.getFecha(), result.getFecha());

    }
    
    @Test (expected = BusinessLogicException.class)
    public void createMemoriaLugarNull() throws BusinessLogicException{
        
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setLugar(null);
        MemoriaEntity result = memoriaLogic.createMemoria(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createMemoriaFechaNull() throws BusinessLogicException{
        
        MemoriaEntity newEntity = factory.manufacturePojo(MemoriaEntity.class);
        newEntity.setFecha(null);
        MemoriaEntity result = memoriaLogic.createMemoria(newEntity);
    }
}
