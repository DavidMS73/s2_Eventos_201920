/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoUsuariosLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@RunWith(Arquillian.class)
public class EventoUsuariosLogicTest {
    private static final Logger LOGGER = Logger.getLogger(EventoUsuariosLogic.class.getName());

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoUsuariosLogic eventoUsuariosLogic;

    @Inject
    private UsuarioLogic usuarioLogic;

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();

    private List<UsuarioEntity> usuariosData = new ArrayList();
    
}
