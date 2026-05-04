package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.EquipmentItem;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class VikingTableModel extends AbstractTableModel {

    private final String[] columns = {"Name", "Age", "Height (cm)", "Hair color", "Beard style", "Equipment"};
    private final VikingService vikingService;

    public VikingTableModel(VikingService vikingService) {
        this.vikingService = vikingService; 
    }

    @Override
    public int getRowCount() {
        return vikingService.findAll().size();  
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Viking> vikings = vikingService.findAll(); 
        Viking viking = vikings.get(rowIndex);
        
        return switch (columnIndex) {        
            case 0 -> viking.name();
            case 1 -> viking.age();
            case 2 -> viking.heightCm();
            case 3 -> viking.hairColor();
            case 4 -> viking.beardStyle();
            case 5 -> formatEquipment(viking.equipment());
            default -> "";
        };
    }

    private String formatEquipment(List<EquipmentItem> equipment) {
        if (equipment == null || equipment.isEmpty()) {
            return "—";
        }
        return equipment.stream()
                .map(item -> item.name() + " [" + item.quality() + "]")
                .collect(Collectors.joining(", "));
    }

    public void refresh() {
        fireTableDataChanged();
    }
}