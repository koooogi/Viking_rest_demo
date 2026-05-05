package ru.mephi.vikingdemo.lambda_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.vikingdemo.lambda_service.VikingLambdaService;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@RestController
@Tag(name = "Filters", description = "Age, apperance and equipment filters")
@RequestMapping("/api/vikings/lambda")
public class VikingLambdaController {
    
    private final VikingLambdaService lambdaService;
    
    public VikingLambdaController(VikingLambdaService lambdaService){
        this.lambdaService = lambdaService;
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
    
    @GetMapping("/count/one-axe")
    @Operation(summary = "Count vikings with one axe")
    public Map<String, Object> countByOneAxe(){
        return Map.of("required", "one axe", "total count", lambdaService.countOneAxe());
    }

    @GetMapping("/count/two-axes")
    @Operation(summary = "Count vikings with two axes")
    public Map<String, Object> countByTwoAxes(){
        return Map.of("required", "two axes", "total count", lambdaService.countTwoAxes());
    }
}
