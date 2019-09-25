package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoActividadesEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * Pruebas de logica de la relacion Evento - ActividadesEvento
 *
 * @author Alberic Despres
 */
@RunWith(Arquillian.class)
public class EventoActividadesEventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLogic eventoLogic;
    @Inject
    private EventoActividadesEventoLogic eventoActividadesEventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

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
        em.createQuery("delete from ActividadEventoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
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
                actividadesData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba para asociar una ActividadEvento existente a un Evento.
     */
    @Test
    public void addActividadTest() {
        EventoEntity entity = data.get(0);
        ActividadEventoEntity actividadEntity = actividadesData.get(1);
        ActividadEventoEntity response = eventoActividadesEventoLogic.addActividad(actividadEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(actividadEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de ActividadesEvento asociadas a una
     * instancia Evento.
     */
    @Test
    public void getActividadesTest() {
        List<ActividadEventoEntity> list = eventoActividadesEventoLogic.getActividades(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de ActividadesEvento asociada a una instancia
     * Evento.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void getActividadTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        ActividadEventoEntity actividadEntity = actividadesData.get(0);
        ActividadEventoEntity response = eventoActividadesEventoLogic.getActividad(entity.getId(), actividadEntity.getId());

        Assert.assertEquals(actividadEntity.getId(), response.getId());
        Assert.assertEquals(actividadEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(actividadEntity.getNombre(), response.getNombre());
        Assert.assertEquals(actividadEntity.getHoraInicio(), response.getHoraInicio());
        Assert.assertEquals(actividadEntity.getHoraFin(), response.getHoraFin());
        Assert.assertEquals(actividadEntity.getFecha(), response.getFecha());
    }

    /**
     * Prueba para desasociar las instancias de Actividades asociadas a una instancia
     * de Evento.
     */
    @Test
    public void removeActividadTest() {
        EventoEntity entity = data.get(0);
        List<ActividadEventoEntity> list = actividadesData.subList(1, 3);
        for(int i=0;i<2;i++){
            eventoActividadesEventoLogic.removeActividad(entity.getId(), list.get(i).getId());
        }
        entity = eventoLogic.getEvento(entity.getId());
        Assert.assertTrue(entity.getActividadesEvento().contains(actividadesData.get(0)));
        Assert.assertFalse(entity.getActividadesEvento().contains(actividadesData.get(1)));
        Assert.assertFalse(entity.getActividadesEvento().contains(actividadesData.get(2)));
    }
}
