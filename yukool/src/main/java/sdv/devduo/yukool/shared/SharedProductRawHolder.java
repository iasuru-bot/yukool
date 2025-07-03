package sdv.devduo.yukool.shared;

import lombok.Data;
import sdv.devduo.yukool.dto.ProduitRaw;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class SharedProductRawHolder {
    private List<ProduitRaw> produitRawList = new ArrayList<>();
}