/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * Pruebas de lógica de evento
 *
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {

    /**
     * Pdam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Lógica del evento
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Contexto de persistencias
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Transacción
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista con datos del evento
     */
    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    /**
     * Lista de memorias
     */
    private List<MemoriaEntity> memoriaData = new ArrayList();

    /**
     * Lista de usuarios
     */
    private List<UsuarioEntity> usuarioData = new ArrayList();

    /**
     * Lista de actividad
     */
    private List<ActividadEventoEntity> actividadesData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from MemoriaEntity").executeUpdate();
        em.createQuery("delete from ActividadEventoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MemoriaEntity memorias = factory.manufacturePojo(MemoriaEntity.class);
            em.persist(memorias);
            memoriaData.add(memorias);
        }
        for (int i = 0; i < 3; i++) {
            ActividadEventoEntity actividades = factory.manufacturePojo(ActividadEventoEntity.class);
            em.persist(actividades);
            actividadesData.add(actividades);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                memoriaData.get(i).setEvento(entity);
            }
            if (i == 1) {
                actividadesData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba para crear un evento
     *
     * @throws BusinessLogicException en caso de fallar alguna regla de negocio
     */
    @Test
    public void createEventoTest() throws BusinessLogicException {

        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);

        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getCategoria(), result.getCategoria());
        Assert.assertEquals(entity.getEntradasRestantes(), result.getEntradasRestantes());
        Assert.assertEquals(entity.getDetallesAdicionales(), result.getDetallesAdicionales());
        Assert.assertEquals(entity.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), result.getFechaFin());
        Assert.assertEquals(entity.getValor(), result.getValor());
    }

    /**
     * Prueba para crear un evento con nombre nulo
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoNombreNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setNombre(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con categoría con nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoCategoriaNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setCategoria(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con descripción nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoDescripcionNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setDescripcion(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con fecha de inicio nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaInicioNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setFechaInicio(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con fecha de fin nula
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaFinNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setFechaFin(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con entradas restantes nulas
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoEntradasRestantesNullTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setEntradasRestantes(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con entradas restantes negativas
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoEntradasRestantesNegativeTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setEntradasRestantes(-2);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear evento con fecha de inicio sin una semana de
     * anterioridad
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaInicioSinUnaSemanaTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        Calendar c = Calendar.getInstance();
        newEntity.setFechaInicio(c.getTime());
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con fecha inicial luego de la fecha final
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaInicioLuegoFechaFinTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        Calendar c = Calendar.getInstance();
        newEntity.setFechaFin(c.getTime());
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para crear un evento con valor negativo
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoValorNegativoTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setValor(new Long(-1));
        eventoLogic.createEvento(newEntity);
    }
    
    /**
     * Prueba para crear un evento sin imagen
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoSinImagenTest() throws BusinessLogicException {
        EventoEntity newEntity = em.find(EventoEntity.class, data.get(2).getId());
        newEntity.setImagen(null);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * Prueba para consultar la lista de eventos
     */
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

    /**
     * Prueba para consultar un evento
     */
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
        Assert.assertEquals(entity.getDetallesAdicionales(), resultEntity.getDetallesAdicionales());
        Assert.assertEquals(entity.getEntradasRestantes(), resultEntity.getEntradasRestantes());
        Assert.assertEquals(entity.getValor(), resultEntity.getValor());
        Assert.assertEquals(entity.getImagen(), resultEntity.getImagen());
    }
    
    /**
     * Prueba para obtener un evento inexistente
     */
    @Test
    public void getEventoInexistenteTest() {
        EventoEntity resultEntity = eventoLogic.getEvento(0L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un evento
     */
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
        Assert.assertEquals(pojoEntity.getDetallesAdicionales(), resp.getDetallesAdicionales());
        Assert.assertEquals(pojoEntity.getEntradasRestantes(), resp.getEntradasRestantes());
        Assert.assertEquals(pojoEntity.getValor(), resp.getValor());
        Assert.assertEquals(pojoEntity.getImagen(), resp.getImagen());
    }

    /**
     * Prueba para eliminar un evento
     *
     * @throws BusinessLogicException incumple la regla de negocio
     */
    @Test
    public void deleteEventoTest() throws BusinessLogicException {
        EventoEntity entity = data.get(2);
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un eventos con memorias asociadas
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteEventoConMemoriasAsociadasTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        eventoLogic.deleteEvento(entity.getId());
    }
    
    /**
     * Prueba para eliminar un eventos con actividades asociadas
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteEventoConActividadesAsociadasTest() throws BusinessLogicException {
        EventoEntity entity = data.get(1);
        eventoLogic.deleteEvento(entity.getId());
    }
}
