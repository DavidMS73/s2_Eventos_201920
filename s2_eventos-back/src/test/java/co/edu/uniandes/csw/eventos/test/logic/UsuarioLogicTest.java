/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
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
 *
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;

    @Inject
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            entity.setEventos(new ArrayList<>());
            data.add(entity);
        }

        UsuarioEntity usuario = data.get(2);
        EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
        entity.getUsuarios().add(usuario);
        em.persist(entity);
        usuario.getEventos().add(entity);
    }

    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
        Assert.assertEquals(entity.getContrasena(), result.getContrasena());
        Assert.assertEquals(entity.getCodigoQR(), result.getCodigoQR());
        Assert.assertEquals(entity.getTipo(), result.getTipo());
    }
    
    /**
     * Prueba para crear un Usuario con un correo que ya existe
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioConMismoCorreoTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        usuarioLogic.createUsuario(newEntity);
    }
    
    @Test
    public void getUsuarioUsernameTest(){
        UsuarioEntity entity = data.get(0);
        UsuarioEntity result = usuarioLogic.getUsuarioUsername(entity.getUsername());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getCorreo(), result.getCorreo());
        Assert.assertEquals(entity.getContrasena(), result.getContrasena());
        Assert.assertEquals(entity.getCodigoQR(), result.getCodigoQR());
        Assert.assertEquals(entity.getTipo(), result.getTipo());
        Assert.assertEquals(entity.getUsername(), result.getUsername());
    }

    /**
     * Prueba para consultar la lista de Authors.
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = usuarioLogic.getUsuarios();
        Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity entity : list) {
            boolean found = false;
            for (UsuarioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioNombreNullTest() throws BusinessLogicException {
        UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
        entity.setNombre(null);
        usuarioLogic.createUsuario(entity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioCorreoNull() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreo(null);
        usuarioLogic.createUsuario(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioCorreoNoUniandes() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreo("juan@hotmail.com");
        usuarioLogic.createUsuario(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioContrasenaNull() throws BusinessLogicException {

        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setContrasena(null);
        usuarioLogic.createUsuario(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioCodigoQRNull() throws BusinessLogicException {

        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCodigoQR(null);
        usuarioLogic.createUsuario(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTipoNull() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setTipo(null);
        usuarioLogic.createUsuario(newEntity);
    }

    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);

        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(pojoEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(pojoEntity.getCodigoQR(), resp.getCodigoQR());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
    }

    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        usuarioLogic.deleteUsuario(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para eliminar un Usuario asociado a un evento
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteUsuarioConEventoTest() throws BusinessLogicException {
        usuarioLogic.deleteUsuario(data.get(2).getId());
    }
}
