/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
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
 * Pruebas de persistencia de Actividades
 *
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class ActividadEventoPersistenceTest {

    /**
     * Transacción
     */
    @Inject
    UserTransaction utx;

    /**
     * Persistencia de la actividad
     */
    @Inject
    private ActividadEventoPersistence ep;

    /**
     * Contexto de persistencia
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Lista de actividades
     */
    private List<ActividadEventoEntity> data = new ArrayList<ActividadEventoEntity>();

    /**
     * Lista de eventos
     */
    private List<EventoEntity> dataEvento = new ArrayList<EventoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ActividadEventoEntity.class.getPackage())
                .addPackage(ActividadEventoPersistence.class.getPackage())
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
        em.createQuery("delete from ActividadEventoEntity").executeUpdate();
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
            dataEvento.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ActividadEventoEntity entity = factory.manufacturePojo(ActividadEventoEntity.class);
            if (i == 0) {
                entity.setEvento(dataEvento.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una actividad.
     */
    @Test
    public void createActividadEventoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ActividadEventoEntity actividadEvento = factory.manufacturePojo(ActividadEventoEntity.class);
        ActividadEventoEntity result = ep.create(actividadEvento);

        Assert.assertNotNull(result);

        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, result.getId());
        
        Assert.assertEquals(actividadEvento.hashCode(), entity.hashCode());

        Assert.assertEquals(actividadEvento.getId(), entity.getId());
        Assert.assertEquals(actividadEvento.getNombre(), entity.getNombre());
        Assert.assertEquals(actividadEvento.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(actividadEvento.getFecha(), entity.getFecha());
        Assert.assertEquals(actividadEvento.getHoraInicio(), entity.getHoraInicio());
        Assert.assertEquals(actividadEvento.getHoraFin(), entity.getHoraFin());
    }

    /**
     * Prueba para consultar una actividad.
     */
    @Test
    public void getActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity newEntity = ep.find(dataEvento.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
        Assert.assertEquals(entity.getHoraInicio(), newEntity.getHoraInicio());
        Assert.assertEquals(entity.getHoraFin(), newEntity.getHoraFin());
    }

    /**
     * Prueba para eliminar una actividad.
     */
    @Test
    public void deleteActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ep.delete(entity.getId());
        ActividadEventoEntity deleted = em.find(ActividadEventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una actividad.
     */
    @Test
    public void updateActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        ActividadEventoEntity resp = em.find(ActividadEventoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getHoraInicio(), resp.getHoraInicio());
        Assert.assertEquals(newEntity.getHoraFin(), resp.getHoraFin());
    }
}