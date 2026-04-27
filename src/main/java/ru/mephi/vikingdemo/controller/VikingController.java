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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "РћРїРµСЂР°С†РёРё СЃ РІРёРєРёРЅРіР°РјРё")
public class VikingController {

    private final VikingService vikingService;
    private VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }
    
    @GetMapping
    @Operation(summary = "РџРѕР»СѓС‡РёС‚СЊ СЃРїРёСЃРѕРє СЃРѕР·РґР°РЅРЅС‹С… РІРёРєРёРЅРіРѕРІ", 
            operationId = "getAllVikings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "РЎРїРёСЃРѕРє СѓСЃРїРµС€РЅРѕ РїРѕР»СѓС‡РµРЅ")
    })
    public List<Viking> getAllVikings() {
        System.out.println("GET /api/vikings called");
        return vikingService.findAll();
    }

    @GetMapping("/test")
    @Operation(summary = "РџРѕР»СѓС‡РёС‚СЊ СЃРїРёСЃРѕРє С‚РµСЃС‚РѕРІС‹С… РІРёРєРёРЅРіРѕРІ", 
            operationId = "getTest")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "РЎРїРёСЃРѕРє СѓСЃРїРµС€РЅРѕ РїРѕР»СѓС‡РµРЅ")
    })
    public List<String> test() {
        System.out.println("GET /api/vikings/test called");
        return List.of("Ragnar", "Bjorn");
    }
    
    @PostMapping("/post")
    @Operation(summary = "РЎРѕР·РґР°С‚СЊ РІРёРєРёРЅРіР° СЃРѕ СЃР»СѓС‡Р°Р№РЅС‹РјРё РїР°СЂР°РјРµС‚СЂР°РјРё", 
            operationId = "post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Р’РёРєРёРЅРі СѓСЃРїРµС€РЅРѕ СЃРѕР·РґР°РЅ")
    })
    public void addViking(){
        vikingListener.testAdd();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить викинга по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Викинг успешно удалён"),
        @ApiResponse(responseCode = "404", description = "Викинг не найден")
    })
    public String deleteViking(@PathVariable int id) {
        System.out.println("DELETE /api/vikings/" + id + " called");
        vikingService.deleteViking(id);
        vikingListener.refreshGui();
        return "Viking with ID " + id + " deleted";
    }
}
