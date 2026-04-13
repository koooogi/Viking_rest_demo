package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;
    private final VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    public List<Viking> getAllVikings() {
        return vikingService.findAll();
    }

    @GetMapping("/test")
    public List<String> test() {
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping
    @Operation(summary = "Добавить нового викинга")
    public Viking addViking(@RequestBody Viking viking) {
        return vikingService.addViking(viking);
    }
    
    @PostMapping("/post")
    public void addVikingViaSwing() {
        vikingListener.testAdd();
    }

    @PutMapping("/{name}")
    @Operation(summary = "Перезаписать викинга")
    public Viking replaceViking(@PathVariable String name, @RequestBody Viking newViking) {
        return vikingService.replaceViking(name, newViking);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить викинга")
    public String deleteViking(@PathVariable String name) {
        vikingService.deleteViking(name);
        return "Viking '" + name + "' deleted";
    }
}