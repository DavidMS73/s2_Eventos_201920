/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
@RunWith(Arquillian.class)
public class LugarPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(LugarEntity.class)
                .addClass(LugarPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    LugarPersistence lp;

    @Test
    public void createLugarTest() 
    {
        PodamFactory podam = new PodamFactoryImpl();
        LugarEntity lugar = podam.manufacturePojo(LugarEntity.class);
        LugarEntity result = lp.create(lugar);

        Assert.assertNotNull(result);

        LugarEntity entity = em.find(LugarEntity.class, result.getId());

        Assert.assertEquals(lugar.getCapacidadAsistentes(), entity.getCapacidadAsistentes());
    }

}
