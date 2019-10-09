/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
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
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class PatrocinioEventosLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PatrocinioEventosLogic patrocinioEventosLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private PatrocinioEntity patrocinio = new PatrocinioEntity();

    private List<EventoEntity> data = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(PatrocinioEventosLogic.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
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
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
        patrocinio.setId(1L);
        patrocinio.setEventos(new ArrayList<>());
        em.persist(patrocinio);

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setPatrocinios(new ArrayList<>());
            entity.getPatrocinios().add(patrocinio);
            em.persist(entity);
            data.add(entity);
            patrocinio.getEventos().add(entity);
        }
    }
@Test
    public void addEventoTest() throws BusinessLogicException {
        EventoEntity newEvento = factory.manufacturePojo(EventoEntity.class);
        eventoLogic.createEvento(newEvento);
        EventoEntity eventoEntity = patrocinioEventosLogic.addEvento(patrocinio.getId(), newEvento.getId());
        Assert.assertNotNull(eventoEntity);

        Assert.assertEquals(eventoEntity.getId(), newEvento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), newEvento.getNombre());
        Assert.assertEquals(eventoEntity.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(eventoEntity.getValor(), newEvento.getValor());

        EventoEntity lastEvento = patrocinioEventosLogic.getEvento(patrocinio.getId(), newEvento.getId());

        Assert.assertEquals(lastEvento.getId(), newEvento.getId());
        Assert.assertEquals(lastEvento.getNombre(), newEvento.getNombre());
        Assert.assertEquals(lastEvento.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(lastEvento.getValor(), newEvento.getValor());
    }
@Test
    public void getEventosTest() {

        List<EventoEntity> eventoEntities = patrocinioEventosLogic.getEventos(patrocinio.getId());

        Assert.assertEquals(data.size(), eventoEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(eventoEntities.contains(data.get(0)));
        }
    }
    @Test
    public void getEventoTest() throws BusinessLogicException {
        EventoEntity eventoEntity = data.get(0);
        EventoEntity evento = patrocinioEventosLogic.getEvento(patrocinio.getId(), eventoEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(eventoEntity.getId(), evento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), evento.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), evento.getDescripcion());
        Assert.assertEquals(eventoEntity.getCategoria(), evento.getCategoria());
        Assert.assertEquals(eventoEntity.getValor(), evento.getValor());
    }
    @Test
    public void replaceEventosTest() throws BusinessLogicException {
        List<EventoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setLugares(new ArrayList<>());
            entity.getPatrocinios().add(patrocinio);
            eventoLogic.createEvento(entity);
            nuevaLista.add(entity);
        }
        patrocinioEventosLogic.replaceEventos(patrocinio.getId(), nuevaLista);
        List<EventoEntity> eventoEntities = patrocinioEventosLogic.getEventos(patrocinio.getId());
        for (EventoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(eventoEntities.contains(aNuevaLista));
        }
    }

    @Test
    public void removeEventoTest() {
        for (EventoEntity evento : data) {
            patrocinioEventosLogic.removeEvento(patrocinio.getId(), evento.getId());
        }
        Assert.assertTrue(patrocinioEventosLogic.getEventos(patrocinio.getId()).isEmpty());
    }
}
