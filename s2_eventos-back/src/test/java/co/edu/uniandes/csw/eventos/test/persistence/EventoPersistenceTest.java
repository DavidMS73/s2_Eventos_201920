/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
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
 * Pruebas de persistencia de Eventos
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {

    /**
     * Transacción
     */
    @Inject
    UserTransaction utx;

    /**
     * Persistencia del evento
     */
    @Inject
    private EventoPersistence ep;

    /**
     * Persistencia del usuario
     */
    @Inject
    private UsuarioPersistence up;

    /**
     * Contexto de persistencia
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Lista de eventos
     */
    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Pruebas para crear un evento
     */
    @Test
    public void createEventoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = ep.create(evento);

        Assert.assertNotNull(result);

        EventoEntity entity = em.find(EventoEntity.class, result.getId());

        Assert.assertFalse(evento.equals(null));
        Assert.assertEquals(evento.hashCode(), entity.hashCode());
        
        Assert.assertEquals(evento.getId(), entity.getId());
        Assert.assertEquals(evento.getNombre(), entity.getNombre());
        Assert.assertEquals(evento.getCategoria(), entity.getCategoria());
        Assert.assertEquals(evento.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(evento.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(evento.getFechaFin(), entity.getFechaFin());
        Assert.assertEquals(evento.getDetallesAdicionales(), entity.getDetallesAdicionales());
        Assert.assertEquals(evento.getEntradasRestantes(), entity.getEntradasRestantes());
        Assert.assertEquals(evento.getValor(), entity.getValor());
        
        evento.setId(null);
        Assert.assertNotNull(evento.hashCode());
        Assert.assertFalse(evento.equals(entity));
    }

    /**
     * Prueba para consultar la lista de eventos
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> list = ep.findAll();
        
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity ent : list) {
            boolean found = false;
            for (EventoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un evento
     */
    @Test
    public void getEventoTest() {
        EventoEntity entity = data.get(0);

        EventoEntity newEntity = ep.find(entity.getId());

        Assert.assertNotNull(newEntity);

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getCategoria(), newEntity.getCategoria());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getFechaInicio(), newEntity.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), newEntity.getFechaFin());
        Assert.assertEquals(entity.getDetallesAdicionales(), newEntity.getDetallesAdicionales());
        Assert.assertEquals(entity.getEntradasRestantes(), newEntity.getEntradasRestantes());
        Assert.assertEquals(entity.getValor(), newEntity.getValor());
    }

    /**
     * Prueba para eliminar un evento
     */
    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(0);
        ep.delete(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un evento
     */
    @Test
    public void updateEventoTest() {
        EventoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        EventoEntity resp = em.find(EventoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getCategoria(), resp.getCategoria());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), resp.getFechaFin());
        Assert.assertEquals(newEntity.getDetallesAdicionales(), resp.getDetallesAdicionales());
        Assert.assertEquals(newEntity.getEntradasRestantes(), resp.getEntradasRestantes());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
    }

    /**
     * Prueba para encontrar un evento por nombre
     */
    @Test
    public void findEventoByNameTest() {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = ep.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = ep.findByName(null);
        Assert.assertNull(newEntity);
    }
}
