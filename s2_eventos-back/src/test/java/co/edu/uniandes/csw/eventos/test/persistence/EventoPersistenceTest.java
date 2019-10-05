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
 *
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest 
{
@Inject
    UserTransaction utx;

    @Inject
    private EventoPersistence ep;
    @Inject
    private UsuarioPersistence up;

    @PersistenceContext
    private EntityManager em;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    
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

    private void clearData() 
    {
     em.createQuery("delete from EventoEntity").executeUpdate();
     em.createQuery("delete from UsuarioEntity").executeUpdate();

    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            entity.setResponsable(usuario);
            usuario.setEvento(entity);
            em.persist(usuario);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createEventoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = ep.create(evento);
        Assert.assertNotNull(result);

        EventoEntity entity = em.find(EventoEntity.class, result.getId());

        Assert.assertEquals(evento.getId(), entity.getId());
        Assert.assertEquals(evento.getNombre(), entity.getNombre());
        Assert.assertEquals(evento.getCategoria(), entity.getCategoria());
        Assert.assertEquals(evento.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(evento.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(evento.getFechaFin(), entity.getFechaFin());
        Assert.assertEquals(evento.getDetallesAdicionales(), entity.getDetallesAdicionales());
        Assert.assertEquals(evento.getEntradasRestantes(), entity.getEntradasRestantes());
        Assert.assertEquals(evento.getEsPago(), entity.getEsPago());
        Assert.assertEquals(evento.getValor(), entity.getValor());
    }

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
        Assert.assertEquals(entity.getEsPago(), newEntity.getEsPago());
        Assert.assertEquals(entity.getValor(), newEntity.getValor());
    }

    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(0);
        ep.delete(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

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
        Assert.assertEquals(newEntity.getEsPago(), resp.getEsPago());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
    }

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
