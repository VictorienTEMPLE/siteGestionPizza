package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.accenture.service.dto.ClientRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
@Tag(name="Clients", description = "Gestion des clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouveau client à travers une requête HTTP POST.</p>
     *
     * @param clientRequestDto Les informations du client à ajouter, envoyées dans le corps de la requête.
     * @return Un objet <code>ResponseEntity</code> avec un statut HTTP 201 (Created) et l'URL de la ressource créée.
     */
    @PostMapping
    @Operation(summary = "Ajouter un nouveau client", description ="Ajoute un nouveau client à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto){
        log.info("L'ajoute du client commence avec son nom : {}", clientRequestDto.nom());
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    /**
     * <p>La méthode <code>trouverTous</code> permet de récupérer la liste de tous les clients via une requête HTTP GET.</p>
     *
     * @return Une liste d'objets <code>ClientResponseDto</code> représentant tous les clients.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les clients", description ="Récupération de tous les clients depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<ClientResponseDto> trouverTous(){
        log.info("La récupération de tous les clients commence");
        return clientService.trouverTous();
    }


    /**
     * <p>La méthode <code>filterParId</code> permet de récupérer un client spécifique par son identifiant via une requête HTTP GET.</p>
     *
     * @param id L'id du client à rechercher, passé en paramètre de l'URL.
     * @return Un objet <code>ClientResponseDto</code> représentant le client trouvé.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer les informations d'un client", description ="Récupération des informations d'un client depuis la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ClientResponseDto filterParId(@PathVariable int id){
        log.info("La récupération des informations du client commence avec son id : {}", id);
        return clientService.filtrerParId(id);
    }
}
