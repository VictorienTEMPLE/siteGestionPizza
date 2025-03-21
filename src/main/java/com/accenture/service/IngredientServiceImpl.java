package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;


@Service
public class IngredientServiceImpl implements IngredientService{

    public static final String ID_EXISTE_PAS = "L'id n'existe pas";
    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;


    public IngredientServiceImpl(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouveau ingrédient dans la base de données.</p>
     *
     * @param ingredientRequestDto Les inforamtions de l'ingrédient à ajouter,
     * @return Un objet <code>IngredientResponseDto</code> représentant l'ingrédient ajouté.
     * @throws IngredientException Si une erreur se produit lors de la validation de l'ingrédient.
     */
    @Override
    public IngredientResponseDto ajouter(IngredientRequestDto ingredientRequestDto) throws IngredientException {
        verifierAjout(ingredientRequestDto);
        Ingredient ingredient=ingredientMapper.toIngredient(ingredientRequestDto);
        Ingredient ingredientEnreg = ingredientDAO.save(ingredient);
        return ingredientMapper.toIngredientResponseDto(ingredientEnreg);
    }

    /**
     * <p>La méthode <code>modifier</code> permet de modifier un ingrédient existant dans la base de données.</p>
     *
     * @param id L'id de l'ingrédient à modifier.
     * @param ingredientRequestDto Les informations de l'ingrédient à modifier.
     * @return Un objet <code>IngredientResponseDto</code> représentant l'ingrédient modifié.
     * @throws IngredientException Si une erreur se produit lors de la validation de l'ingrédient.
     * @throws EntityNotFoundException Si l'ingrédient avec l'id donné n'est pas trouvé dans la base de données.
     */
    @Override
    public IngredientResponseDto modifier(int id, IngredientRequestDto ingredientRequestDto) throws IngredientException, EntityNotFoundException{
        Optional<Ingredient> optIngredient = ingredientDAO.findById(id);
        if (optIngredient.isEmpty())
            throw new EntityNotFoundException(ID_EXISTE_PAS);
        Ingredient ingredientExistante = optIngredient.get();
        Ingredient nouvelle = ingredientMapper.toIngredient(ingredientRequestDto);
        verifierEtRemplacer(nouvelle, ingredientExistante);
        Ingredient ingredientEnreg = ingredientDAO.save(ingredientExistante);
        return ingredientMapper.toIngredientResponseDto(ingredientEnreg);
    }

    /**
     * <p>La méthode <code>lister</code> permet de récupérer tous les ingrédients présents dans la base de données.</p>
     *
     * @return Une liste d'objets <code>IngredientResponseDto</code> représentant tous les ingrédients.
     */
    @Override
    public List<IngredientResponseDto> lister() {
        List<Ingredient> listIngredient =ingredientDAO.findAll();
        return listIngredient.stream()
                .map(ingredientMapper::toIngredientResponseDto)
                .toList();
    }





    private static void verifierAjout(IngredientRequestDto ingredientRequestDto) {
        if (ingredientRequestDto == null)
            throw new IngredientException("L'ingrédient ne peut pas être nul");
        if (ingredientRequestDto.nom()==null || ingredientRequestDto.nom().isBlank())
            throw new IngredientException("Le nom ne peut pas être null, ou vide");
        if (ingredientRequestDto.quantiteEnStock() == null)
            throw new IngredientException("La quantité ne peut pas être nul");
        if(ingredientRequestDto.quantiteEnStock() < 0)
            throw new IngredientException("La quantité ne peut pas être négative");
        if (ingredientRequestDto.enStock() == null)
            throw new IngredientException("Le status ne peut pas être nul");
        if(ingredientRequestDto.quantiteEnStock() == 0 && Boolean.TRUE.equals(ingredientRequestDto.enStock()))
            throw new IngredientException("Le enStock doit être false lors que la quantité est 0");
    }
  
      
       private void verifierEtRemplacer(Ingredient nouvelle, Ingredient ingredientExistante) {
        if(nouvelle == null)
            throw new IngredientException("L'ingrédient ne peut pas être nul");
        if(nouvelle.getNom() != null){
            if(nouvelle.getNom().isBlank())
                throw new IngredientException("Le nom ne peut pas être vide");
            else ingredientExistante.setNom(nouvelle.getNom());}
        if(nouvelle.getQuantiteEnStock() != null) {
            if (nouvelle.getQuantiteEnStock() < 0)
                throw new IngredientException("La quantité ne peut pas être négative");
            else ingredientExistante.setQuantiteEnStock(nouvelle.getQuantiteEnStock());
        }
        if(nouvelle.getEnStock() != null)
            ingredientExistante.setEnStock(nouvelle.getEnStock());
        if(nouvelle.getQuantiteEnStock() == 0 && Boolean.TRUE.equals(nouvelle.getEnStock()))
            throw new IngredientException("Le enStock doit être false lors que la quantité est 0");
    }

    
}
