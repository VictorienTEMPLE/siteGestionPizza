package com.accenture.service;

import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import com.accenture.exception.ClientException;

import com.accenture.service.dto.ClientRequestDto;

@ExtendWith(MockitoExtension.class)

@ExtendWith(MockitoExtension.class)
class ClientTest {
    @Mock
    ClientDAO daoMock = Mockito.mock(ClientDAO.class);

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;

    @Test
    void testTrouverTousAvecBaseVide(){
        Mockito.when(daoMock.findAll()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), service.trouverTous());
    }

    @Test
    void testTrouverTousOk(){
        Client client1 = creerClient();
        Client client2 = creerClient2();
        List<Client> listeClient = List.of(client1,client2);
        ClientResponseDto clientResponseDto1 = creerClientResponseDto();
        ClientResponseDto clientResponseDto2 = creerClientResponseDto2();
        List<ClientResponseDto> listeClientresponseDto = List.of(clientResponseDto1,clientResponseDto2);
        Mockito.when(daoMock.findAll()).thenReturn(listeClient);
        Mockito.when(mapperMock.toClientResponseDto(listeClient.getFirst())).thenReturn(listeClientresponseDto.getFirst());
        Mockito.when(mapperMock.toClientResponseDto(listeClient.get(1))).thenReturn(listeClientresponseDto.get(1));
        Assertions.assertEquals(listeClientresponseDto, service.trouverTous());
    }

    @Test
    void testFiltrerParIdAvecIdExistePas(){
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.empty());
        EntityNotFoundException pe = Assertions.assertThrows(EntityNotFoundException.class,()->service.filtrerParId(1));
        Assertions.assertEquals("L'id n'existe pas", pe.getMessage());
    }

    @Test
    void testFilterParIdOk(){
        Client client = creerClient();
        ClientResponseDto clientResponseDto =creerClientResponseDto();
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(client));
        Mockito.when(mapperMock.toClientResponseDto(client)).thenReturn(clientResponseDto);
        Assertions.assertEquals(clientResponseDto, service.filtrerParId(1));
    }



    @Test
    void testAjouterClientAvecDtoNull(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(null));
        Assertions.assertEquals("Le client ne peut pas être nul", ce.getMessage());
    }

    @Test
    void testAjouterClientAvecNomNull(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto(null,"emmanuel@gmail.com", true)));
        Assertions.assertEquals("Le nom du client ne peut pas être nul ou vide", ce.getMessage());
    }
    @Test
    void testAjouterClientAvecNomBlank(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto("  ","emmanuel@gmail.com", true)));
        Assertions.assertEquals("Le nom du client ne peut pas être nul ou vide", ce.getMessage());
    }


    @Test
    void testAjouterClientAvecMailNull(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto("Emmanuel",null, true)));
        Assertions.assertEquals("Le mail du client ne peut pas être nul ou vide", ce.getMessage());
    }

    @Test
    void testAjouterClientAvecMailBlank(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto("Emmanuel","    ", true)));
        Assertions.assertEquals("Le mail du client ne peut pas être nul ou vide", ce.getMessage());
    }


    @Test
    void testAjouterClientAvecMailMauvaiseFormat(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto("Emmanuel","emmanuelgmail.com", true)));
        Assertions.assertEquals("Le format de mail du client est invalide", ce.getMessage());
    }

    @Test
    void testAjouterClientAvecVipNull(){
        ClientException ce = Assertions.assertThrows(ClientException.class, () -> service.ajouter(new ClientRequestDto("Emmanuel","emmanuel@gmail.com", null)));
        Assertions.assertEquals("Le statut vip du client ne peut pas être nul", ce.getMessage());
    }

    @Test
    void testAjouterClientOK(){
        ClientRequestDto clientRequestDto = creerClientRequestDto();
        Client client = creerClient();
        Client clientEnreg = creerClient();
        clientEnreg.setId(1);
        ClientResponseDto clientResponseDto = creerClientResponseDto();
        Mockito.when(mapperMock.toClient(clientRequestDto)).thenReturn(client);
        Mockito.when(daoMock.save(client)).thenReturn(clientEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientEnreg)).thenReturn(clientResponseDto);
        Assertions.assertEquals(clientResponseDto,service.ajouter(clientRequestDto));
        Mockito.verify(daoMock).save(client);

    }



    /* *********************************************** *
     *                                                 *
     *                 methodes privées                *
     *                                                 *
     * *********************************************** *
     */

    private static Client creerClient(){
        return new Client("Legrand","joelegrand@email.com",false);
    }
    private static Client creerClient2(){
        return new Client("Solaire","flowrahsolaire@email.com",true);
    }
    private static ClientResponseDto creerClientResponseDto(){
        return new ClientResponseDto(1,"Legrand","joelegrand@email.com",false);
    }
    private static ClientResponseDto creerClientResponseDto2(){
        return new ClientResponseDto(1,"Solaire","flowrahsolaire@email.com",true);
    }


    private ClientRequestDto creerClientRequestDto(){
        return new ClientRequestDto("Emmanuel","emmanuel@gmail.com", true);
   }

}