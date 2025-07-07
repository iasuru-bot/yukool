package sdv.devduo.yukool.service;

import org.springframework.stereotype.Service;
import sdv.devduo.yukool.model.Allergene;

import java.util.List;

@Service
public class AllergeneServiceImpl implements AllergeneService{
    @Override
    public List<Allergene> findTopAllergene(int limit) {
        return List.of();
    }
}
