package ru.mephi.vikingdemo.lambda_service;

import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Service
public class VikingLambdaService {
    
    private VikingService vikingService;
    
    public VikingLambdaService(VikingService vikingService){
        this.vikingService = vikingService;
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
}
