package ru.mephi.vikingdemo.gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.mephi.vikingdemo.lambda_service.VikingLambdaService;
import ru.mephi.vikingdemo.model.Viking;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class LambdaFrame extends JFrame{
    
        private final JTextArea textArea = new JTextArea(20, 50);
    
    public LambdaFrame(VikingLambdaService lambdaService){
        setTitle("Viking Filters");
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
        
        updateContent(lambdaService);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateContent(lambdaService));
        add(refreshButton, BorderLayout.SOUTH);
    }
    
    public void updateContent(VikingLambdaService lambdaService){
        StringBuilder sb = new StringBuilder();
        
        lambdaService.getRandomVikingByHeight().ifPresentOrElse(v -> sb.append("Random viking >180cm: ").append(v.name()).append("\n\n"),() -> sb.append("No vikings >180cm\n\n"));

        List<Viking> legendary = lambdaService.getVikingsWithLegendaryEquipment();
        sb.append("Legendary equipment: ").append(legendary.size()).append(" vikings\n");
        legendary.forEach(v -> sb.append("  • ").append(v.name()).append("\n"));
        
        List<Viking> redBearded = lambdaService.getRedBeardedSortedByAge();
        sb.append("\nRed-bearded by age: ").append(redBearded.size()).append(" vikings\n");
        redBearded.forEach(v -> sb.append("  • ").append(v.name()).append(" (").append(v.age()).append(")\n"));
        
        textArea.setText(sb.toString());
    }
}
