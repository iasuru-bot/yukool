package sdv.devduo.yukool.service;

import org.springframework.stereotype.Service;
import sdv.devduo.yukool.model.Allergene;

import java.util.List;

@Service
public interface AllergeneService {
    List<Allergene> findTopAllergene(int limit);
}
