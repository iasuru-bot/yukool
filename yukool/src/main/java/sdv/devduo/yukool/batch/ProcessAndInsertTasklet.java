package sdv.devduo.yukool.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.model.*;
import sdv.devduo.yukool.repository.*;
import sdv.devduo.yukool.shared.SharedProductRawHolder;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProcessAndInsertTasklet implements Tasklet {
    private final SharedProductRawHolder sharedProductRawHolder;
    private final MarqueRepository marqueRepository;
    private final CategorieRepository categorieRepository;
    private final IngredientRepository ingredientRepository;
    private final AdditifRepository additifRepository;
    private final AllergeneRepository allergeneRepository;
    private final ProduitRepository produitRepository;


    @Autowired
    public ProcessAndInsertTasklet(SharedProductRawHolder sharedProductRawHolder, MarqueRepository marqueRepository, CategorieRepository categorieRepository, IngredientRepository ingredientRepository, AdditifRepository additifRepository, AllergeneRepository allergeneRepository, ProduitRepository produitRepository) {
        this.sharedProductRawHolder = sharedProductRawHolder;
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
        this.ingredientRepository = ingredientRepository;
        this.additifRepository = additifRepository;
        this.allergeneRepository = allergeneRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<ProduitRaw> raws = sharedProductRawHolder.getProduitRawList();
        if (raws == null || raws.isEmpty()) return RepeatStatus.FINISHED;

        // 1. Extraire les référentiels uniques

        // 2. Insérer les référentiels si non existants

        // 3. Transformer et insérer les produits
        
        return RepeatStatus.FINISHED;
    }


} 