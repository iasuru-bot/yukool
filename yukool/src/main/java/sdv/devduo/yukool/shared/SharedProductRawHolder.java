package sdv.devduo.yukool.shared;

import lombok.Getter;
import lombok.Setter;
import sdv.devduo.yukool.dto.ProduitRaw;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SharedProductRawHolder {
    private List<ProduitRaw> produitRawList = new ArrayList<>();
} 