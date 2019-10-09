package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLugaresLogic;
import co.edu.uniandes.csw.eventos.ejb.LugarLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
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
 * Pruebas de logica de la relacion Evento - Lugares
 *
 * @author Alberic Despres
 */
@RunWith(Arquillian.class)
public class EventoLugaresLogicTest {

    private static final Logger LOGGER = Logger.getLogger(EventoLugaresLogic.class.getName());

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLugaresLogic eventoLugaresLogic;

    @Inject
    private LugarLogic lugarLogic;

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private LugarPersistence lugarPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();

    private List<LugarEntity> lugaresData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(EventoLugaresLogic.class.getPackage())
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
        em.createQuery("delete from LugarEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setLugares(new ArrayList<>());
        em.persist(evento);
        for (int i = 0; i < 3; i++) {
            LugarEntity lugares = factory.manufacturePojo(LugarEntity.class);
            em.persist(lugares);
            lugaresData.add(lugares);
            evento.getLugares().add(lugares);
        }
        em.persist(evento);
    }

    /**
     * Prueba para asociar un Lugar existente a un Evento.
     */
    @Test
    public void addLugarTest() throws BusinessLogicException {
        LugarEntity newLugar = factory.manufacturePojo(LugarEntity.class);
        lugarLogic.createLugar(newLugar);

        Assert.assertNotNull(newLugar);
        LugarEntity lugarEntity = eventoLugaresLogic.addLugar(evento.getId(), newLugar.getId());
        Assert.assertNotNull(lugarEntity);

        Assert.assertEquals(lugarEntity.getId(), newLugar.getId());
        Assert.assertEquals(lugarEntity.getNombre(), newLugar.getNombre());
        Assert.assertEquals(lugarEntity.getCapacidadAsistentes(), newLugar.getCapacidadAsistentes());
        Assert.assertEquals(lugarEntity.getUbicacionGeografica(), newLugar.getUbicacionGeografica());
        Assert.assertEquals(lugarEntity.getBloque(), newLugar.getBloque());
        Assert.assertEquals(lugarEntity.getPiso(), newLugar.getPiso());
        Assert.assertEquals(lugarEntity.getSalon(), newLugar.getSalon());

        LugarEntity lastLugar = eventoLugaresLogic.getLugar(evento.getId(), newLugar.getId());

        Assert.assertEquals(lastLugar.getId(), newLugar.getId());
        Assert.assertEquals(lastLugar.getNombre(), newLugar.getNombre());
        Assert.assertEquals(lastLugar.getCapacidadAsistentes(), newLugar.getCapacidadAsistentes());
        Assert.assertEquals(lastLugar.getUbicacionGeografica(), newLugar.getUbicacionGeografica());
        Assert.assertEquals(lastLugar.getBloque(), newLugar.getBloque());
        Assert.assertEquals(lastLugar.getPiso(), newLugar.getPiso());
        Assert.assertEquals(lastLugar.getSalon(), newLugar.getSalon());
    }

    /**
     * Prueba para obtener una colección de instancias de Lugares asociadas a
     * una instancia Evento.
     */
    @Test
    public void getLugaresTest() {

        List<LugarEntity> lugarEntities = eventoLugaresLogic.getLugares(evento.getId());

        Assert.assertEquals(lugaresData.size(), lugarEntities.size());

        for (int i = 0; i < lugaresData.size(); i++) {
            Assert.assertTrue(lugarEntities.contains(lugaresData.get(i)));
        }
    }

    @Test
    public void getLugarTest() throws BusinessLogicException {
        LugarEntity lugarEntity = lugaresData.get(0);
        LugarEntity lugar = eventoLugaresLogic.getLugar(evento.getId(), lugarEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(lugarEntity.getId(), lugar.getId());
        Assert.assertEquals(lugarEntity.getNombre(), lugar.getNombre());
    }
    @Test
    public List<LugarEntity> replaceLugares(Long eventoId, List<LugarEntity> lugares) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los lugares asociados al evento con id = {0}", eventoId);
        EventoEntity eventoEntity = eventoPersistence.find(eventoId);
        List<LugarEntity> lugarList = lugarPersistence.findAll();
        for (LugarEntity lugar : lugarList) {
            if (lugares.contains(evento)) {
                if (!lugar.getEventos().contains(eventoEntity)) {
                    lugar.getEventos().add(eventoEntity);
                }
            } else {
                lugar.getEventos().remove(eventoEntity);
            }
        }
        eventoEntity.setLugares(lugares);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los lugares asociados al evento con id = {0}", eventoId);
        return eventoEntity.getLugares();
    }

    @Test
    public void removeLugarTest() {
        for (LugarEntity lugar : lugaresData) {
            eventoLugaresLogic.removeLugar(evento.getId(), lugar.getId());
        }
        Assert.assertTrue(eventoLugaresLogic.getLugares(evento.getId()).isEmpty());
    }
}
