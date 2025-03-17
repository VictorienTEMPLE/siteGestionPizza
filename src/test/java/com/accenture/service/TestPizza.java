package com.accenture.service;

import org.mockito.InjectMocks;


public class TestPizza {
//    @Mock //on crée le mockito.mock ici
//    PizzaDAO daoMock = Mockito.mock(PizzaDAO.class);
//    @Mock
//    PizzaMapper mapperMock;
    @InjectMocks //on créer le new tachServiceImpl ici
    PizzaServiceImpl service;
//    @Test
//    void testAjouterNull(){
//        Assertions.assertThrows(PizzaException.class, ()->service.ajouter(null));
//    }
}
