/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();
    
    private List<MemoriaEntity> memoriaData = new ArrayList();


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from MemoriaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            MemoriaEntity memorias = factory.manufacturePojo(MemoriaEntity.class);
            em.persist(memorias);
            memoriaData.add(memorias);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                memoriaData.get(i).setEvento(entity);
            }
            
        }
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(usuario);
        usuario.setEventoResponsable(data.get(2));
        data.get(2).setResponsable(usuario);
    }

    @Test
    public void createEventoTest() throws BusinessLogicException {

        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);

        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getCategoria(), result.getCategoria());
        Assert.assertEquals(entity.getEntradasRestantes(), result.getEntradasRestantes());
        Assert.assertEquals(entity.getEsPago(), result.getEsPago());
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoNombreNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setNombre(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoCategoriaNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setCategoria(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoDescripcionNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setDescripcion(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaInicioNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFechaInicio(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaFinNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFechaFin(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoEntradasRestantesNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setEntradasRestantes(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoEntradasRestantesNegativeTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setEntradasRestantes(-2);
        eventoLogic.createEvento(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEventoEsPagoNullTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setEsPago(null);
        eventoLogic.createEvento(newEntity);
    }

    @Test
    public void getEventosTest() {
        List<EventoEntity> list = eventoLogic.getEventos();
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity entity : list) {
            boolean found = false;
            for (EventoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getEventoTest() {
        EventoEntity entity = data.get(0);
        EventoEntity resultEntity = eventoLogic.getEvento(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getCategoria(), resultEntity.getCategoria());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getFechaInicio(), resultEntity.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), resultEntity.getFechaFin());
        Assert.assertEquals(entity.getEsPago(), resultEntity.getEsPago());
        Assert.assertEquals(entity.getDetallesAdicionales(), resultEntity.getDetallesAdicionales());
        Assert.assertEquals(entity.getEntradasRestantes(), resultEntity.getEntradasRestantes());
        Assert.assertEquals(entity.getValor(), resultEntity.getValor());
    }

    @Test
    public void updateEventoTest() {
        EventoEntity entity = data.get(0);
        EventoEntity pojoEntity = factory.manufacturePojo(EventoEntity.class);
        pojoEntity.setId(entity.getId());
        eventoLogic.updateEvento(pojoEntity.getId(), pojoEntity);
        EventoEntity resp = em.find(EventoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getCategoria(), resp.getCategoria());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(pojoEntity.getFechaFin(), resp.getFechaFin());
        Assert.assertEquals(pojoEntity.getEsPago(), resp.getEsPago());
        Assert.assertEquals(pojoEntity.getDetallesAdicionales(), resp.getDetallesAdicionales());
        Assert.assertEquals(pojoEntity.getEntradasRestantes(), resp.getEntradasRestantes());
        Assert.assertEquals(pojoEntity.getValor(), resp.getValor());
    }

    @Test
    public void deleteEventoTest() throws BusinessLogicException {
        EventoEntity entity = data.get(1);
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
