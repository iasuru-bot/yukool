package sdv.devduo.yukool.service;

import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitRaw;

import java.util.List;

@Service
public interface ProduitService {

    void saveAllProduit(List<ProduitRaw> produitRaws);
}
