package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mephi.vikingdemo.model.VikingUpdateRequest;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;
    private VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    @Operation(summary = "Получить список созданных викингов", 
            operationId = "getAllVikings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/test")
    @Operation(summary = "Получить список тестовых викингов", 
            operationId = "getTest")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/post")
    public void addViking(){
        vikingListener.testAdd();
    }
    
    @PostMapping
    @Operation(summary = "Add new viking")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Viking deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Incorrect data")
    })
    public Viking addVikingFromJson(@RequestBody Viking viking){
        System.out.println("POST /api/vikings called with: " + viking);
        return vikingService.addViking(viking);
    }
    
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete viking")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Viking deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Viking not found")
    })
    public String deleteViking(@PathVariable String name){
        System.out.println("DELETE /api/vikings/" + name + " called");
        vikingService.deleteViking(name);
        return "Viking named " + name + " deleted";
    }
    
    @PatchMapping("/{name}")
    @Operation(summary = "Update viking")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Viking updated successfully"),
        @ApiResponse(responseCode = "404", description = "Viking not found")
    })
    public Viking updateViking(@PathVariable String name, @RequestBody VikingUpdateRequest updateData) {
        System.out.println("PATCH /api/vikings/" + name + " called");
        return vikingService.updateViking(name, updateData);
    }
}

