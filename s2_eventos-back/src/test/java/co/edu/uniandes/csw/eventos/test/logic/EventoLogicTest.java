/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class EventoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EventoLogic eventoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createEventoTest() throws BusinessLogicException{
        
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);
        
        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getCategoria(), result.getCategoria());
        Assert.assertEquals(entity.getEntradasRestantes(), result.getEntradasRestantes());
        Assert.assertEquals(entity.getTipo(), result.getTipo());
        Assert.assertEquals(entity.getEsPago(), result.getEsPago());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoNombreNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setNombre(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoCategoriaNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setCategoria(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoDescripcionNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setDescripcion(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoFechaInicioNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFechaInicio(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoFechaFinNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFechaFin(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoEntradasRestantesNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setEntradasRestantes(null);
        eventoLogic.createEvento(newEntity);
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoEsPagoNullTest() throws BusinessLogicException{
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setEsPago(null);
        eventoLogic.createEvento(newEntity);
    }
}
