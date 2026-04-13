package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class VikingService {
    // каждый раз при изменении создаётся новая копия списка 
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    @Autowired
    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }
    
    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        vikings.add(viking);
        return viking;
    }


    public Viking addViking(Viking viking) {
        if(viking.name() == null || viking.name().trim().isEmpty()){
            throw new IllegalArgumentException("Name required");
        }
        vikings.add(viking);
        return viking;
    }
    
    public Viking replaceViking(String name, Viking newViking) {
    for (int i = 0; i < vikings.size(); i++) {
        if (vikings.get(i).name().equals(name)) {
            vikings.set(i, newViking);
            return newViking;
        }
    }
    throw new RuntimeException("Viking named '" + name + "' not found");
}

    public void deleteViking(String name) {
        boolean removed = vikings.removeIf(v -> v.name().equals(name));
        if (!removed) {
            throw new RuntimeException("Viking named '" + name + "' not found");
        }
    }
}

    