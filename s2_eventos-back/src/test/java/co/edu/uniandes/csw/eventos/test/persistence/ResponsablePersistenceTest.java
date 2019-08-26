/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;


import co.edu.uniandes.csw.eventos.entities.ResponsableEntity;
import co.edu.uniandes.csw.eventos.persistence.ResponsablePersistence;
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
public class ResponsablePersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class).addClass(ResponsableEntity.class).addClass(ResponsablePersistence.class).addAsManifestResource("META-INF/persistence.xml","persistence.xml").addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    ResponsablePersistence rp;
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    @Test
    public void testCreate(){
        
        
        PodamFactory factory = new PodamFactoryImpl();
        ResponsableEntity responsable = factory.manufacturePojo(ResponsableEntity.class);
        ResponsableEntity newResp=rp.create(responsable);
        Assert.assertNotNull(newResp);
        //Assert.assertNotNull(newMem.getId());
        //Assert.assertNotNull(MemoriaEntity.class);
        //Assert.assertNotNull(em);

        ResponsableEntity myEntity = em.find(ResponsableEntity.class, newResp.getId());
        Assert.assertEquals(responsable.getCodigoUniandes(), myEntity.getCodigoUniandes());
    }
    
}