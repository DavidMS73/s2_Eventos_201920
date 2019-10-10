package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.LugarEventosLogic;
import co.edu.uniandes.csw.eventos.ejb.LugarLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
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
 * Pruebas de logica de la relacion Evento - Lugares
 *
 * @author Alberic Despres
 */
@RunWith(Arquillian.class)
public class LugarEventosLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private LugarEventosLogic lugarEventosLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private LugarEntity lugar = new LugarEntity();

    private List<EventoEntity> data = new ArrayList();
    
    private UsuarioEntity responsable = new UsuarioEntity();
    
    private UsuarioEntity organizador = new UsuarioEntity();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(LugarEventosLogic.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
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

    private void clearData() {
        em.createQuery("delete from LugarEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        responsable = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(responsable);
        organizador = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(organizador);
        lugar = factory.manufacturePojo(LugarEntity.class);
        lugar.setId(1L);
        lugar.setEventos(new ArrayList<>());
        em.persist(lugar);

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setLugares(new ArrayList<>());
            entity.getLugares().add(lugar);
            em.persist(entity);
            data.add(entity);
            lugar.getEventos().add(entity);
        }
    }

    @Test
    public void addEventoTest() throws BusinessLogicException {
        EventoEntity newEvento = factory.manufacturePojo(EventoEntity.class);
        responsable.setEvento(newEvento);
        organizador.setEvento(newEvento);
        newEvento.setResponsable(responsable);
        newEvento.setOrganizador(organizador);
        eventoLogic.createEvento(newEvento);
        EventoEntity eventoEntity = lugarEventosLogic.addEvento(lugar.getId(), newEvento.getId());
        Assert.assertNotNull(eventoEntity);
        
        Assert.assertEquals(eventoEntity.getId(), newEvento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), newEvento.getNombre());
        Assert.assertEquals(eventoEntity.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(eventoEntity.getValor(), newEvento.getValor());
        

        EventoEntity lastEvento = lugarEventosLogic.getEvento(lugar.getId(), newEvento.getId());

        Assert.assertEquals(lastEvento.getId(), newEvento.getId());
        Assert.assertEquals(lastEvento.getNombre(), newEvento.getNombre());
        Assert.assertEquals(lastEvento.getCategoria(), newEvento.getCategoria());
        Assert.assertEquals(lastEvento.getValor(), newEvento.getValor());
    }

    @Test
    public void getEventosTest() {

        List<EventoEntity> eventoEntities = lugarEventosLogic.getEventos(lugar.getId());

        Assert.assertEquals(data.size(), eventoEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(eventoEntities.contains(data.get(0)));
        }
    }

    @Test
    public void getEventoTest() throws BusinessLogicException {
        EventoEntity eventoEntity = data.get(0);
        EventoEntity evento = lugarEventosLogic.getEvento(lugar.getId(), eventoEntity.getId());
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
            entity.getLugares().add(lugar);
            responsable.setEvento(entity);
            organizador.setEvento(entity);
            entity.setResponsable(responsable);
            entity.setOrganizador(organizador);
            eventoLogic.createEvento(entity);
            nuevaLista.add(entity);
        }
        lugarEventosLogic.replaceEventos(lugar.getId(), nuevaLista);
        List<EventoEntity> bookEntities = lugarEventosLogic.getEventos(lugar.getId());
        for (EventoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(bookEntities.contains(aNuevaLista));
        }
    }

    @Test
    public void removeEventoTest() {
        for (EventoEntity evento : data) {
            lugarEventosLogic.removeEvento(lugar.getId(), evento.getId());
        }
        Assert.assertTrue(lugarEventosLogic.getEventos(lugar.getId()).isEmpty());
    }
}
