/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
 * Pruebas de lógica de la relación Usuario - Eventos
 *
 * @author Germán David Martínez Solano
 */
@RunWith(Arquillian.class)
public class UsuarioEventosLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioEventosLogic usuarioEventoLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private UsuarioEntity usuario = new UsuarioEntity();
    private List<EventoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(UsuarioEventosLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setId(1L);
        usuario.setEventos(new ArrayList<>());
        em.persist(usuario);

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setUsuarios(new ArrayList<>());
            entity.getUsuarios().add(usuario);
            em.persist(entity);
            data.add(entity);
            usuario.getEventos().add(entity);
        }
    }

    /**
     * Prueba para asociar un usuario a un evento.
     *
     *
     * @throws BusinessLogicException
     */
    @Test
    public void addEventoTest() throws BusinessLogicException {
        EventoEntity newEvento = factory.manufacturePojo(EventoEntity.class);
        eventoLogic.createEvento(newEvento);
        EventoEntity eventoEntity = usuarioEventoLogic.addEvento(usuario.getId(), newEvento.getId());
        Assert.assertNotNull(eventoEntity);

        Assert.assertEquals(eventoEntity.getId(), newEvento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), newEvento.getNombre());
        Assert.assertEquals(eventoEntity.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(eventoEntity.getDescripcion(), newEvento.getDescripcion());
        Assert.assertEquals(eventoEntity.getDetallesAdicionales(), newEvento.getDetallesAdicionales());
        Assert.assertEquals(eventoEntity.getEntradasRestantes(), newEvento.getEntradasRestantes());
        Assert.assertEquals(eventoEntity.getFechaInicio(), newEvento.getFechaInicio());
        Assert.assertEquals(eventoEntity.getFechaFin(), newEvento.getFechaFin());
        Assert.assertEquals(eventoEntity.getValor(), newEvento.getValor());

        EventoEntity lastEvento = usuarioEventoLogic.getEvento(usuario.getId(), newEvento.getId());

        Assert.assertEquals(lastEvento.getId(), newEvento.getId());
        Assert.assertEquals(lastEvento.getNombre(), newEvento.getNombre());
        Assert.assertEquals(lastEvento.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(lastEvento.getDescripcion(), newEvento.getDescripcion());
        Assert.assertEquals(lastEvento.getDetallesAdicionales(), newEvento.getDetallesAdicionales());
        Assert.assertEquals(lastEvento.getEntradasRestantes(), newEvento.getEntradasRestantes());
        Assert.assertEquals(lastEvento.getFechaInicio(), newEvento.getFechaInicio());
        Assert.assertEquals(lastEvento.getFechaFin(), newEvento.getFechaFin());
        Assert.assertEquals(lastEvento.getValor(), newEvento.getValor());
    }

    /**
     * Prueba para consultar la lista de Eventos de un usuario.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> eventoEntities = usuarioEventoLogic.getEventos(usuario.getId());

        Assert.assertEquals(data.size(), eventoEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(eventoEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para consultar un evento de un usuario.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getEventoTest() throws BusinessLogicException {
        EventoEntity eventoEntity = data.get(0);
        EventoEntity evento = usuarioEventoLogic.getEvento(usuario.getId(), eventoEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(eventoEntity.getId(), evento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), evento.getNombre());
        Assert.assertEquals(eventoEntity.getCategoria(), evento.getCategoria());
        Assert.assertEquals(eventoEntity.getDescripcion(), evento.getDescripcion());
        Assert.assertEquals(eventoEntity.getDetallesAdicionales(), evento.getDetallesAdicionales());
        Assert.assertEquals(eventoEntity.getEntradasRestantes(), evento.getEntradasRestantes());
        Assert.assertEquals(eventoEntity.getFechaInicio(), evento.getFechaInicio());
        Assert.assertEquals(eventoEntity.getFechaFin(), evento.getFechaFin());
        Assert.assertEquals(eventoEntity.getValor(), evento.getValor());
    }

    /**
     * Prueba para actualizar los eventos de un usuario.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void replaceEventosTest() throws BusinessLogicException {
        List<EventoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setUsuarios(new ArrayList<>());
            entity.getUsuarios().add(usuario);
            eventoLogic.createEvento(entity);
            nuevaLista.add(entity);
        }
        usuarioEventoLogic.replaceEventos(usuario.getId(), nuevaLista);
        List<EventoEntity> bookEntities = usuarioEventoLogic.getEventos(usuario.getId());
        for (EventoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(bookEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un evento con un usuario.
     *
     */
    @Test
    public void removeEventoTest() {
        for (EventoEntity evento : data) {
            usuarioEventoLogic.removeEvento(usuario.getId(), evento.getId());
        }
        Assert.assertTrue(usuarioEventoLogic.getEventos(usuario.getId()).isEmpty());
    }
}
