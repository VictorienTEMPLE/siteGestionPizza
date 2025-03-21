package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import com.accenture.service.mapper.PizzaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    public static final String ID_EXISTE_PAS = "L'id n'existe pas";
    private final PizzaDAO pizzaDAO;
    private final PizzaMapper pizzaMapper;

    public PizzaServiceImpl(PizzaDAO pizzaDAO, PizzaMapper pizzaMapper) {
        this.pizzaDAO = pizzaDAO;
        this.pizzaMapper = pizzaMapper;
    }

    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verifierAjout(pizzaRequestDto);
        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        Pizza pizzaEnreg = pizzaDAO.save(pizza);
        System.out.println(pizza);
        return pizzaMapper.toPizzaResponseDto(pizzaEnreg);
    }


    @Override
    public PizzaResponseDto supprimer(int id) throws EntityNotFoundException{
        Pizza pizzaASupprimer = pizzaDAO.findById(id).orElseThrow(()->new EntityNotFoundException(ID_EXISTE_PAS));
        pizzaASupprimer.setId(id);
        pizzaASupprimer.setActif(false);
        pizzaDAO.save(pizzaASupprimer);
        return pizzaMapper.toPizzaResponseDto(pizzaASupprimer);
    }
    /**
     * <p>La méthode List<PizzaResponseDto> trouverTous() permet de récupérer la liste de toutes les pizza.</p> *
     * * @return Une liste d'objets <code>PizzaResponseDto</code> représentant les pizzas. </code>
     **/


    @Override
    public List<PizzaResponseDto> trouverTous(){
        return pizzaDAO.findAll()
                .stream()
                .map(pizzaMapper::toPizzaResponseDto)
                .toList();
    }

    /**
     * <p>La méthode PizzaResponseDto filtrerParId(int id) permet de récupérer une pizza par son ID.</p> *
     * * @return Une liste d'objets <code>PizzaResponseDto</code> représentant la pizza trouvée. </code>
     **/

    @Override
    public PizzaResponseDto filtrerParId(int id) throws EntityNotFoundException{
        Pizza pizzaTrouve = pizzaDAO.findById(id).orElseThrow(()-> new EntityNotFoundException(ID_EXISTE_PAS));
        return pizzaMapper.toPizzaResponseDto(pizzaTrouve);
    }

    /**
     * <p>La méthode PizzaResponseDto filtrerParNom(String nom) permet de récupérer une pizza par son nom.</p> *
     * * @return Une liste d'objets <code>PizzaResponseDto</code> représentant la pizza trouvée. </code>
     **/

    @Override
    public PizzaResponseDto filtrerParNom(String nom) throws EntityNotFoundException{
        Pizza pizzaTrouve = pizzaDAO.findByNomIgnoreCase(nom).orElseThrow(()-> new EntityNotFoundException("Pizza avec ce nom n'existe pas"));
        return pizzaMapper.toPizzaResponseDto(pizzaTrouve);
    }

    /**
     * <p>La méthode List<PizzaResponseDto> filtrerParIngredient(String nom) permet de récupérer une pizza par Ingredient.</p> *
     * * @return Une liste d'objets <code>PizzaResponseDto</code> représentant la pizza trouvée. </code>
     **/
    @Override
    public List<PizzaResponseDto> filtrerParIngredient(String nom){
        return pizzaDAO.findByIngredientNom(nom)
                .stream()
                .map(pizzaMapper::toPizzaResponseDto)
                .toList();
    }

    /**
     * <p>La méthode PizzaResponseDto modifier(int id, PizzaRequestDto pizzaRequestDto) permet de récupérer une pizza par Ingredient.</p> *
     * * @return retourne une PizzaResponseDto avec les données modifiées <code>PizzaRequestDto</code> représente la pizza une fois modifiée. </code>
     * @throws PizzaException lance une exception d'un élément de pizza.
     * @throws EntityNotFoundException lance une exception dans le cas ou l'entité est nul.
     **/

    @Override
    public PizzaResponseDto modifier(int id, PizzaRequestDto pizzaRequestDto) throws  PizzaException,  EntityNotFoundException{
        if (pizzaRequestDto == null)
            throw new PizzaException("La pizza ne peut pas être nul");
        Optional<Pizza> optPizza = pizzaDAO.findById(id);
        if (optPizza.isEmpty())
            throw new EntityNotFoundException("L'id n'existe pas");
        Pizza nouvellePizza = pizzaMapper.toPizza(pizzaRequestDto);
        Pizza pizzaExistante = optPizza.get();
        remplacerPizza(nouvellePizza, pizzaExistante);
        Pizza pizzaEnreg = pizzaDAO.save(pizzaExistante);
        return pizzaMapper.toPizzaResponseDto(pizzaEnreg);
    }

    /**
     * <p>La méthode remplacerPizza(Pizza nouvellePizza, Pizza pizzaExistante) permet de verifier si les éléments de pizzaExistante correspondent ou non à nouvellePizza
     *  et ainsi, dans le cas ou les informations sont différentes, la méthode remplace les information de pizzaExistante avec celle de nouvelle Pizza.</p>
     *  @throws PizzaException lance une exception d'un élément de pizza.
     **/

    private static void remplacerPizza(Pizza nouvellePizza, Pizza pizzaExistante) {
        if (nouvellePizza.getNom()!=null) {
            if (nouvellePizza.getNom().isBlank())
                throw new PizzaException("Le nom ne peut pas être vide");
            pizzaExistante.setNom(nouvellePizza.getNom());
        }
        if (nouvellePizza.getIngredient()!=null)
            pizzaExistante.setIngredient(nouvellePizza.getIngredient());
        if (nouvellePizza.getTarif()!=null)
            pizzaExistante.setTarif(nouvellePizza.getTarif());
        if (nouvellePizza.getActif()!=null)
            pizzaExistante.setActif(nouvellePizza.getActif());
    }
    /**
     * <p>La méthode verifierAjout(PizzaRequestDto pizzaRequestDto) permet de verifier si les éléments de <code>PizzaRequestDto</code> sont null, si oui, il déclenche alors une <code>PizzaException.</code></p>
     *  @throws PizzaException lance une exception d'un élément de pizza.
     **/
    private static void verifierAjout(PizzaRequestDto pizzaRequestDto) {
        if (pizzaRequestDto == null)
            throw new PizzaException("La pizza ne peut pas être nul");
        if (pizzaRequestDto.nom() == null || pizzaRequestDto.nom().isBlank())
            throw new PizzaException("Le nom ne peut pas être nul, ou vide");
        if (pizzaRequestDto.id_ingredient() == null || pizzaRequestDto.id_ingredient().isEmpty())
            throw new PizzaException("La liste des ingrédients ne peut pas être nul ou vide");
        if (pizzaRequestDto.tarif() == null)
            throw new PizzaException("Le tarif ne peut pas être nul");
        if (pizzaRequestDto.actif() == null)
            throw new PizzaException("Le status ne peut pas être nul");
    }



}
