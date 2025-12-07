package cl.josueaguila.pnb.gui;

import javax.swing.*;
import java.awt.*;

public class EventosPanel extends JPanel {
    
    public EventosPanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        
        JLabel lblTitulo = new JLabel(
            "<html><center>" +
            "<h1 style='color: #FF6B35;'>🎮 Eventos y Torneos</h1>" +
            "<p style='margin-top: 20px; font-size: 14px;'>" +
            "Este módulo está en desarrollo y estará disponible próximamente." +
            "</p>" +
            "<p style='margin-top: 30px; font-size: 12px; color: #666;'>" +
            "Funcionalidades planificadas:" +
            "</p>" +
            "<ul style='text-align: left; font-size: 12px; color: #666;'>" +
            "<li>Gestión de torneos semanales</li>" +
            "<li>Inscripción de participantes</li>" +
            "<li>Registro de resultados</li>" +
            "<li>Rankings y estadísticas</li>" +
            "</ul>" +
            "</center></html>"
        );
        
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(lblTitulo, gbc);
    }
}
