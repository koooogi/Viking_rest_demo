package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import ru.mephi.vikingdemo.lambda_service.VikingLambdaService;

public class VikingDesktopFrame extends JFrame {

    private final VikingService vikingService;
    private final VikingTableModel tableModel;
    private final VikingLambdaService lambdaService;

    public VikingDesktopFrame(VikingService vikingService, VikingLambdaService lambdaService) {
        this.vikingService = vikingService;
        this.tableModel = new VikingTableModel(vikingService); 
        this.lambdaService = lambdaService;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        JTable vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(event -> onCreateViking());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(event -> refreshTable());
        
        JButton lambdaButton = new JButton("Viking Filters");
        lambdaButton.addActionListener(event -> {
        LambdaFrame lambdaFrame = new LambdaFrame(lambdaService);
        lambdaFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(lambdaButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void onCreateViking() {
        vikingService.createRandomViking();  
        tableModel.refresh();             
    }
    
    public void addNewViking(Viking viking) {
        refreshTable();  
    }

    public void refreshTable() {
        tableModel.refresh();  
    }
}