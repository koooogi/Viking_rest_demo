package ru.mephi.vikingdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Schema(description = "Data to update")

public class VikingUpdateRequest {
    
    @Schema(description = "Age", example = "31")
    private Integer age;
    
    @Schema(description = "Height in cm", example = "184")
    private Integer heightCm;
    
    @Schema(description = "Hair color", example = "Blond")
    private HairColor hairColor;
    
    @Schema(description = "Beard shape")
    private BeardStyle beardStyle;
    
    @Schema(description = "Equipment")
    private List<EquipmentItem> equipment;
    
    public Integer getAge(){
        return age; 
    }
    public void setAge(Integer age){
        this.age = age; 
    }

    public Integer getHeight(){ 
        return heightCm; 
    }
    public void setHeight(Integer heightCm) { 
        this.heightCm = heightCm; 
    }
    
    public HairColor getHairColor(){ 
        return hairColor; 
    }
    public void setHairColor(HairColor hairColor){ 
        this.hairColor = hairColor; 
    }
    
    public BeardStyle getBeardStyle(){ 
        return beardStyle; 
    }
    public void setBeardStyle(BeardStyle beardStyle){ 
        this.beardStyle = beardStyle; 
    }
    
    public List<EquipmentItem> getEquipment(){ 
        return equipment; 
    }
    public void setEquipment(List<EquipmentItem> equipment){ 
        this.equipment = equipment; 
    }
}
