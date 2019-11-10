/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.ActividadEventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Pruebas de la lógica de la actividad de un evento
 *
 * @author Germán David Martínez
 */
@RunWith(Arquillian.class)
public class ActividadEventoLogicTest {

    /**
     * Podam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Lógica de la actividad
     */
    @Inject
    private ActividadEventoLogic actividadLogic;

    /**
     * Contexto de persistencia
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Transacción
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista de actividades del evento
     */
    private List<ActividadEventoEntity> data = new ArrayList<>();

    /**
     * Lista de eventos
     */
    private List<EventoEntity> dataEvento = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ActividadEventoEntity.class.getPackage())
                .addPackage(ActividadEventoLogic.class.getPackage())
                .addPackage(ActividadEventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
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
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            dataEvento.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ActividadEventoEntity entity = factory.manufacturePojo(ActividadEventoEntity.class);
            entity.setEvento(dataEvento.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una actividad
     *
     * @throws BusinessLogicException en caso de fallar en las reglas de negocio
     */
    @Test
    public void createActividadEventoTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        ActividadEventoEntity result = actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
        Assert.assertNotNull(result);

        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, result.getId());

        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getFecha(), result.getFecha());
        Assert.assertEquals(entity.getHoraInicio(), result.getHoraInicio());
        Assert.assertEquals(entity.getHoraFin(), result.getHoraFin());
    }

    /**
     * Prueba para crear una actividad con nombre nulo
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoNombreNullTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setNombre(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con descripción nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoDescripcionNullTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setDescripcion(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con fecha nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoFechaNullTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setFecha(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con hora de inicio nula
     *
     * @throws BusinessLogicException incumple la regla de negocio0
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoHoraInicioNullTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setHoraInicio(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con hora de fin nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoHoraFinNullTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        newEntity.setHoraFin(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad antes de la fecha del evento que la crea
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoFechaAntesEventoTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        Calendar c = Calendar.getInstance();
        newEntity.setFecha(c.getTime());
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad después de la fecha del evento que la
     * crea
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadEventoFechaDespuesEventoTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(dataEvento.get(1));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 20);
        newEntity.setFecha(c.getTime());
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con un evento que no existe.
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadTestConEventoInexistenteTest() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId(Long.MIN_VALUE);
        newEntity.setEvento(eventoEntity);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para crear una actividad con evento en null.
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadTestConNullEvento() throws BusinessLogicException {
        ActividadEventoEntity newEntity = factory.manufacturePojo(ActividadEventoEntity.class);
        newEntity.setEvento(null);
        actividadLogic.createActividadEvento(dataEvento.get(1).getId(), newEntity);
    }

    /**
     * Prueba para consultar la lista de libros
     *
     * @throws BusinessLogicException En caso de fallar
     */
    @Test
    public void getActividadesEventoTest() throws BusinessLogicException {
        List<ActividadEventoEntity> list = actividadLogic.getActividadesEvento(dataEvento.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (ActividadEventoEntity entity : list) {
            boolean found = false;
            for (ActividadEventoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una actividad
     */
    @Test
    public void getActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity resultEntity = actividadLogic.getActividadEvento(dataEvento.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getFecha(), resultEntity.getFecha());
        Assert.assertEquals(entity.getHoraInicio(), resultEntity.getHoraInicio());
        Assert.assertEquals(entity.getHoraFin(), resultEntity.getHoraFin());
    }

    /**
     * Prueba para actualizar una actividad
     */
    @Test
    public void updateActividadEventoTest() {
        ActividadEventoEntity entity = data.get(0);
        ActividadEventoEntity pojoEntity = factory.manufacturePojo(ActividadEventoEntity.class);

        pojoEntity.setId(entity.getId());

        actividadLogic.updateActividadEvento(dataEvento.get(1).getId(), pojoEntity);

        ActividadEventoEntity resp = em.find(ActividadEventoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getHoraInicio(), resp.getHoraInicio());
        Assert.assertEquals(pojoEntity.getHoraFin(), resp.getHoraFin());
    }

    /**
     * Prueba para borrar una actividad
     *
     * @throws BusinessLogicException en caso de que falle alguna regla de
     * negocio
     */
    @Test
    public void deleteActividadEventoTest() throws BusinessLogicException {
        ActividadEventoEntity entity = data.get(0);
        actividadLogic.deleteActividadEvento(dataEvento.get(1).getId(), entity.getId());
        ActividadEventoEntity deleted = em.find(ActividadEventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminarle una actividad a un evento del cual no pertenece.
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteActividadConEventoNoAsociadoTest() throws BusinessLogicException {
        ActividadEventoEntity entity = data.get(0);
        actividadLogic.deleteActividadEvento(dataEvento.get(0).getId(), entity.getId());
    }
}
