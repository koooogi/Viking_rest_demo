package ru.mephi.vikingdemo.lambda_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.lambda_service.VikingLambdaService;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@RestController
@Tag(name = "Filters", description = "Age, apperance and equipment filters")
@RequestMapping("/api/vikings/lambda")
public class VikingLambdaController {
    
    private final VikingLambdaService lambdaService;
    private final VikingListener vikingListener;
    
    //first
    public VikingLambdaController(VikingLambdaService lambdaService, VikingListener vikingListener){
        this.lambdaService = lambdaService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping("/count/age-greater/{age}")
    @Operation(summary = "Get age greater than given")
    public Map<String, Object> countByGreaterAge(@PathVariable int age){
        return Map.of("age limit", "age > " + age, "total count", lambdaService.countByGreaterAge(age));
    }
    
    @GetMapping("/count/age-less/{age}")
    @Operation(summary = "Get age less than given")
    public Map<String, Object> countBySmallerAge(@PathVariable int age){
        return Map.of("age limit", "age < " + age, "total count", lambdaService.countBySmallerAge(age));
    }
    
    @GetMapping("/count/age-between")
    @Operation(summary = "Get age between min and max")
    public Map<String, Object> countByBetweenAge(@RequestParam int min, @RequestParam int max){
        return Map.of("age range", min + " <= age <= " + max, "total count", lambdaService.countByBetweenAge(min, max));
    }
    
    @GetMapping("/count/age-outside")
    @Operation(summary = "Get age outside min and max")
    public Map<String, Object> countByOuterAge(@RequestParam int min, @RequestParam int max){
        return Map.of("age range", "age < " + min + " OR age > " + max, "total count", lambdaService.countByOuterAge(min, max));
    }
    
    @GetMapping("/count/beard-hair")
    @Operation(summary = "Count by beard style and hair color")
    public Map<String, Object> countByBeardAndHair(@RequestParam BeardStyle beard, @RequestParam HairColor hair){
        return Map.of("beardStyle", beard, "hairColor", hair, "total count", lambdaService.countBeardAndHairColor(beard, hair)
        );
    }
    
    @GetMapping("/count/axes")
    @Operation(summary = "Count vikings with 1 or 2 axes")
    public Map<String, Object> countByAxes() {
        return Map.of(
            "required", "1 or 2 axes",
            "total count", lambdaService.countByAxes());
    }
    
    //second
    @GetMapping("/random-by-height")
    @Operation(summary = "Get random viking taller than 180 cm")
    public Map<String, Object> getRandomTallViking(){
    Optional<Viking> viking = lambdaService.getRandomVikingByHeight();
    
    if(viking.isPresent()){
        return Map.of("found", true, "viking", viking.get());
    }else{
        return Map.of("found", false, "message", "No vikings taller than 180 cm");
        }
    }
    
    @GetMapping("/legendary-equipment")
    @Operation(summary = "Get vikings with legendary equipment")
    public Map<String, Object> getVikingsWithLegendaryEquipment(){
        List<Viking> vikings = lambdaService.getVikingsWithLegendaryEquipment();
        return Map.of("total count", vikings.size(), "vikings", vikings);
    }
    
    @GetMapping("/red-bearded-sorted")
    @Operation(summary = "Get red-bearded vikings sorted by age")
    public Map<String, Object> getRedBeardedSortedByAge(){
        List<Viking> vikings = lambdaService.getRedBeardedSortedByAge();
        return Map.of("total count", vikings.size(), "vikings", vikings);
    }
    
    //third
    @GetMapping("/ids/all")
    @Operation(summary = "Get all viking IDs")
    public Map<String, Object> getAllIds(){
        List<Integer> ids = lambdaService.getAllIds();
        return Map.of("total count", ids.size(),"ids", ids);
    }
    
    @GetMapping("/ids/max")
    @Operation(summary = "Get maximum viking ID")
    public Map<String, Object> getMaxId(){
        int maxId = lambdaService.getMaxId();
        if(maxId > 0){
            return Map.of("max ID", maxId);
        }else{
            return Map.of("message", "No vikings in database");
        }
    }
    
    @GetMapping("/ids/even")
    @Operation(summary = "Get even viking IDs")
    public Map<String, Object> getEvenIds(){
        List<Integer> evenIds = lambdaService.getEvenIds();
        return Map.of("total count", evenIds.size(), "even IDs", evenIds);
    }
    
    //mass_generation
    @PostMapping("/generate/{count}")
    @Operation(summary = "Generate vikings (max 100)")
    public Map<String, Object> generateVikings(@PathVariable int count) {
        List<Viking> vikings = lambdaService.generateVikings(count);
        vikingListener.refreshGui();
        return Map.of("generated", vikings.size(), "names", vikings.stream().map(Viking::name).collect(Collectors.toList()));
    }
}
