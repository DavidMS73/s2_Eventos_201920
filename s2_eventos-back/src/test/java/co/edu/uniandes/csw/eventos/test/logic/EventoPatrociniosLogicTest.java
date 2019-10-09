/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLugaresLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoPatrociniosLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinioLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class EventoPatrociniosLogicTest {

    private static final Logger LOGGER = Logger.getLogger(EventoLugaresLogic.class.getName());

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoPatrociniosLogic eventoPatrociniosLogic;

    @Inject
    private PatrocinioLogic patrocinioLogic;

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private PatrocinioPersistence patrocinioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();

    private List<PatrocinioEntity> patrociniosData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(EventoPatrociniosLogic.class.getPackage())
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
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setPatrocinios(new ArrayList<>());
        em.persist(evento);
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
            em.persist(patrocinio);
            patrociniosData.add(patrocinio);
            evento.getPatrocinios().add(patrocinio);
        }
        em.persist(evento);
    }

    @Test
    public void addPatrocinioTest() throws BusinessLogicException {
        PatrocinioEntity newPatrocinio = factory.manufacturePojo(PatrocinioEntity.class);
        patrocinioLogic.createPatrocinio(newPatrocinio);

        Assert.assertNotNull(newPatrocinio);
        PatrocinioEntity patrocinioEntity = eventoPatrociniosLogic.addPatrocinio(evento.getId(), newPatrocinio.getId());
        Assert.assertNotNull(patrocinioEntity);

        Assert.assertEquals(patrocinioEntity.getId(), newPatrocinio.getId());
        Assert.assertEquals(patrocinioEntity.getEmpresa(), newPatrocinio.getEmpresa());
        Assert.assertEquals(patrocinioEntity.getTipo(), newPatrocinio.getTipo());

        PatrocinioEntity lastPatrocinio = eventoPatrociniosLogic.getPatrocinio(evento.getId(), newPatrocinio.getId());

        Assert.assertEquals(lastPatrocinio.getId(), newPatrocinio.getId());
        Assert.assertEquals(lastPatrocinio.getEmpresa(), newPatrocinio.getEmpresa());
        Assert.assertEquals(lastPatrocinio.getTipo(), newPatrocinio.getTipo());
    }

    @Test
    public void getPatrociniosTest() {

        List<PatrocinioEntity> patrocinioEntities = eventoPatrociniosLogic.getPatrocinios(evento.getId());

        Assert.assertEquals(patrociniosData.size(), patrocinioEntities.size());

        for (int i = 0; i < patrociniosData.size(); i++) {
            Assert.assertTrue(patrocinioEntities.contains(patrociniosData.get(i)));
        }
    }

    @Test
    public void getPatrocinioTest() throws BusinessLogicException {
        PatrocinioEntity patrocinioEntity = patrociniosData.get(0);
        PatrocinioEntity patrocinio = eventoPatrociniosLogic.getPatrocinio(evento.getId(), patrocinioEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(patrocinioEntity.getId(), patrocinio.getId());
        Assert.assertEquals(patrocinioEntity.getEmpresa(), patrocinio.getEmpresa());
        Assert.assertEquals(patrocinioEntity.getTipo(), patrocinio.getTipo());

    }

    @Test
    public void replacePatrociniosTest() throws BusinessLogicException {
        List<PatrocinioEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            patrocinioLogic.createPatrocinio(entity);
            nuevaLista.add(entity);
        }
        eventoPatrociniosLogic.replacePatrocinios(evento.getId(), nuevaLista);
        List<PatrocinioEntity> bookEntities = eventoPatrociniosLogic.getPatrocinios(evento.getId());
        for (PatrocinioEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(bookEntities.contains(aNuevaLista));
        }
    }

    @Test
    public void removePatrocinioTest() {
        for (PatrocinioEntity patrocinio : patrociniosData) {
            eventoPatrociniosLogic.removePatrocinio(evento.getId(), patrocinio.getId());
        }
        Assert.assertTrue(eventoPatrociniosLogic.getPatrocinios(evento.getId()).isEmpty());
    }
}
