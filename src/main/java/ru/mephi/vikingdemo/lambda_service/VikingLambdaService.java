package ru.mephi.vikingdemo.lambda_service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.repository.VikingStorage;
import ru.mephi.vikingdemo.service.VikingService;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Service
public class VikingLambdaService {
    
    private final VikingService vikingService;
    private final VikingStorage vikingStorage;
    
    //first
    public VikingLambdaService(VikingService vikingService, VikingStorage vikingStorage){
        this.vikingService = vikingService;
        this.vikingStorage = vikingStorage;
    }
    
    private long count(Predicate<Viking> predicate){
        return vikingService.findAll().stream().filter(predicate).count();
    }
    
    public long countByGreaterAge(int age){
        return count(l -> l.age() > age);
    }
    
    public long countBySmallerAge(int age){
        return count(l -> l.age() < age);
    }
    
    public long countByBetweenAge(int min, int max){
        return count(l -> l.age() >= min && l.age() <= max);
    }
    
    public long countByOuterAge(int min, int max){
        return count(l -> l.age() < min || l.age() > max);
    }
    
    public long countBeardAndHairColor(BeardStyle beard, HairColor hair){
        return count(l -> l.beardStyle() == beard && l.hairColor() == hair);
    }
    
    public long countOneAxe(){
        return count(l -> countAxes(l) == 1);
    }
    
    public long countTwoAxes(){
        return count(l -> countAxes(l) == 2);
    }
    
    private long countAxes(Viking viking){
        if(viking.equipment() == null){
            return 0;
        }
        return viking.equipment().stream().filter(e -> e.name() != null && e.name().toLowerCase().contains("axe")).count();
    }
    
    //second
    public Optional<Viking> getRandomVikingByHeight(){
    List<Viking> vikings = vikingService.findAll().stream().filter(v -> v.heightCm() > 180).collect(Collectors.toList());
    
    if (vikings.isEmpty()) {
        return Optional.empty();
    }
    
    int randomIndex = new Random().nextInt(vikings.size());
    return Optional.of(vikings.get(randomIndex));
    }
    
    public List<Viking> getVikingsWithLegendaryEquipment(){
    return vikingService.findAll().stream().filter(v -> v.equipment() != null && v.equipment().stream()
            .anyMatch(e -> "Legendary".equalsIgnoreCase(e.quality()))).collect(Collectors.toList());
    }
    
    public List<Viking> getRedBeardedSortedByAge(){
    return vikingService.findAll().stream().filter(v -> v.hairColor() == HairColor.Red).sorted(Comparator.comparingInt(v -> v.age()))
            .collect(Collectors.toList());
    }
    
    //third
    public List<Integer> getAllIds(){
        return vikingStorage.getAllIds();
    }
    
    public int getMaxId(){
        return vikingStorage.getAllIds().stream().mapToInt(id -> id).max().orElse(0);
    }
    
    public List<Integer> getEvenIds(){
        return vikingStorage.getAllIds().stream().filter(id -> id % 2 == 0).sorted().toList();
    }
}
