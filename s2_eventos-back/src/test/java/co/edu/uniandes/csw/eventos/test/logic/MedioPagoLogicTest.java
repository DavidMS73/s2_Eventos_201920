/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MedioPagoLogic;
import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MedioPagoPersistence;
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
 * @author Samuelillo.
 */
@RunWith(Arquillian.class)

public class MedioPagoLogicTest {
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioPagoEntity.class.getPackage())
                .addPackage(MedioPagoLogic.class.getPackage())
                .addPackage(MedioPagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml" , "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml" , "beans.xml");
    }
    
    @PersistenceContext
    private EntityManager em;
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private MedioPagoLogic medioPagoLogic;
    
    @Test
    public void createMedioPago() throws BusinessLogicException{
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        MedioPagoEntity result = medioPagoLogic.createMedioPago(newEntity);
        Assert.assertNotNull(result);
        
        MedioPagoEntity entity = em.find(MedioPagoEntity.class, result.getId());
        Assert.assertEquals(entity.getNumeroRecibo(), result.getNumeroRecibo());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createMedioPagoReciboNullTest() throws BusinessLogicException{
        MedioPagoEntity newEntity = factory.manufacturePojo(MedioPagoEntity.class);
        newEntity.setNumeroRecibo(null);
        MedioPagoEntity result = medioPagoLogic.createMedioPagoReciboNull(newEntity);
    }
}
