/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioTarjetasLogic;
import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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
 * @author Estudiante
 */
@RunWith(Arquillian.class)
public class UsuarioTarjetasLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    @Inject
    private UsuarioTarjetasLogic usuarioTarjetasLogic;
    
    private List<TarjetaEntity> tarjetasData = new ArrayList<>();
    
    private List<UsuarioEntity> data = new ArrayList<>(); 
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e1){
            e1.printStackTrace();
            try{
                utx.rollback();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }
    
    private void clearData(){
        em.createQuery("delete from TarjetaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    private void insertData(){
        for(int i = 0; i < 3; i++){
            TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
            em.persist(tarjeta);
            tarjetasData.add(tarjeta);
        }
        for(int i = 0; i < 3; i++){
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
            if(i == 0)
                tarjetasData.get(i).setUsuario(entity);
        }
    }
    
    //-------------------------------------------------------------------------------
    // Pruebas.
    //-------------------------------------------------------------------------------
    
    @Test
    public void removeTarjetaTest(){
        UsuarioEntity entity = data.get(0);
        List<TarjetaEntity> list = tarjetasData.subList(1, 3);
        for(int i = 0; i<2; i++){
            usuarioTarjetasLogic.removeTarjeta(entity.getId(), list.get(i).getId());
        }
        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertTrue(entity.getTarjetas().contains(tarjetasData.get(0)));
        Assert.assertFalse(entity.getTarjetas().contains(tarjetasData.get(1)));
        Assert.assertFalse(entity.getTarjetas().contains(tarjetasData.get(2)));
    }
    
    @Test
    public void addTarjetaTest(){
        UsuarioEntity entity = data.get(0);
        TarjetaEntity tarjetaEntity = tarjetasData.get(0);
        TarjetaEntity response = usuarioTarjetasLogic.addTarjeta(entity.getId(), tarjetaEntity.getId());
        
        Assert.assertNotNull(response);
        Assert.assertEquals(tarjetaEntity.getId(), response.getId());
    }
}
