/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(EventoEntity.class)
                .addClass(EventoPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml"); 
    }
    
    @Inject
    EventoPersistence ep;
    
    @PersistenceContext
    EntityManager em;
    
    @Test
    public void createTest(){
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = ep.create(evento);
        Assert.assertNotNull(result);
        
        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        
        Assert.assertEquals(evento.getNombre(), entity.getNombre());
        Assert.assertEquals(evento.getCategoria(), entity.getCategoria());
        Assert.assertEquals(evento.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(evento.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(evento.getFechaFin(), entity.getFechaFin());
        Assert.assertEquals(evento.getDetallesAdicionales(), entity.getDetallesAdicionales());
        Assert.assertEquals(evento.getEntradasRestantes(), entity.getEntradasRestantes());
        Assert.assertEquals(evento.getTipo(), entity.getTipo());
    }
}
