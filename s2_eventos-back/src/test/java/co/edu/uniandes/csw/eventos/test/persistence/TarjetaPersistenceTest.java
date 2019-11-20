/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.TarjetaPersistence;
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
 * @author Samuelillo el pillo.
 */
@RunWith(Arquillian.class)
public class TarjetaPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaEntity.class.getPackage())
                .addPackage(TarjetaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    UserTransaction utx;

    @Inject
    private TarjetaPersistence ep;

    @PersistenceContext
    private EntityManager em;

    private List<TarjetaEntity> data = new ArrayList<>();

    private List<UsuarioEntity> dataUsuario = new ArrayList<>();

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

    private void clearData() {
        em.createQuery("delete from TarjetaEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            dataUsuario.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            TarjetaEntity entity = factory.manufacturePojo(TarjetaEntity.class);
            if (i == 0) {
                entity.setUsuario(dataUsuario.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createTarjetaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
        TarjetaEntity result = ep.create(tarjeta);
        Assert.assertNotNull(result);

        TarjetaEntity entity = em.find(TarjetaEntity.class, result.getId());

        Assert.assertFalse(tarjeta.equals(null));
        Assert.assertEquals(tarjeta.hashCode(), entity.hashCode());

        Assert.assertEquals(tarjeta.getNumeroTarjeta(), entity.getNumeroTarjeta());
    }

    @Test
    public void deleteTarjetaTest() {
        TarjetaEntity entity = data.get(0);
        ep.delete(entity.getId());
        TarjetaEntity deleted = em.find(TarjetaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getTarjetasTest() {
        List<TarjetaEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TarjetaEntity ent : list) {
            boolean found = false;
            for (TarjetaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getTarjetaTest() {
        TarjetaEntity entity = data.get(0);
        TarjetaEntity newEntity = ep.find(dataUsuario.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroTarjeta(), newEntity.getNumeroTarjeta());
        Assert.assertEquals(entity.getExpiracion(), newEntity.getExpiracion());
        Assert.assertEquals(entity.getCw(), newEntity.getCw());
    }

    @Test
    public void updateMedioPagoTest() {
        TarjetaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        TarjetaEntity resp = em.find(TarjetaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNumeroTarjeta(), resp.getNumeroTarjeta());
    }

    @Test
    public void findTarjetaByNameNumber() {
        TarjetaEntity entity = data.get(0);
        TarjetaEntity newEntity = ep.findByNumber(entity.getNumeroTarjeta());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumeroTarjeta(), newEntity.getNumeroTarjeta());
        Assert.assertEquals(entity.getExpiracion(), newEntity.getExpiracion());
        Assert.assertEquals(entity.getCw(), newEntity.getCw());

        newEntity = ep.findByNumber(null);
        Assert.assertNull(newEntity);
    }
}
