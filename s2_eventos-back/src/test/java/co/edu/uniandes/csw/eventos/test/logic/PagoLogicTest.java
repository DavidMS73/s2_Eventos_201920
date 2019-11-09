/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.PagoLogic;
import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PagoPersistence;
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
 * @author Daniel Santiago Tenjo
 */
@RunWith(Arquillian.class)
public class PagoLogicTest {

    @Deployment
    public static JavaArchive CreateDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoLogic.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<PagoEntity> data = new ArrayList<>();

    @Inject
    private PagoLogic pagoLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData() {
        em.createQuery("delete from PagoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }
    
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createPagoTest() throws BusinessLogicException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        PagoEntity result = pagoLogic.createPago(newEntity);
        Assert.assertNotNull(result);

        PagoEntity entity = em.find(PagoEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
    }

    @Test(expected = BusinessLogicException.class)
    public void createPagoFechaNullTest() throws BusinessLogicException {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        newEntity.setFecha(null);
        PagoEntity result = pagoLogic.createPago(newEntity);
    }
    
    
    

}
