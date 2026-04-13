package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mephi.vikingdemo.model.VikingUpdateRequest;


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
    
    public Viking updateViking(String name, VikingUpdateRequest updateData) {
        for (int i = 0; i < vikings.size(); i++){
            Viking existing = vikings.get(i);
            if(existing.name().equals(name)){
                Viking updated = new Viking(
                        existing.name(),
                        updateData.getAge() != null ? updateData.getAge() : existing.age(),
                        updateData.getHeight() != null ? updateData.getHeight() : existing.heightCm(),
                        updateData.getHairColor() != null ? updateData.getHairColor() : existing.hairColor(),
                        updateData.getBeardStyle() != null ? updateData.getBeardStyle() : existing.beardStyle(),
                        updateData.getEquipment() != null ? updateData.getEquipment() : existing.equipment()
                );
                vikings.set(i, updated);
                return updated;
            }
        }
        throw new RuntimeException("Viking named " + name + " not found");
    }

    public void deleteViking(String name) {
        boolean removed = vikings.removeIf(v -> v.name().equals(name));
        if (!removed) {
            throw new RuntimeException("Viking named '" + name + "' not found");
        }
    }
}

    