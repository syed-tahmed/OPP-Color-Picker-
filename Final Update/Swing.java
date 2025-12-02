import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout; // Added
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets; // Added
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Swing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Swing::createAndShowUI);
    }

    private static void createAndShowUI() {
        // ---------- FRAME ----------
        JFrame frame = new JFrame("Color Picker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900); // Increased height slightly to fit shades
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // ---------- HEADER ----------
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 16));

        JLabel title = new JLabel("Color Picker");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        topBar.add(title, BorderLayout.WEST);
        frame.add(topBar, BorderLayout.NORTH);

        // ---------- CENTER CONTAINER ----------
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        frame.add(center, BorderLayout.CENTER);

        // ---------- IMAGE CONTAINER ----------
        final int IMG_WIDTH = 780;
        final int IMG_HEIGHT = 340;

        // Note: Ensure "mmm/mmc.png" exists, or this will be blank
        ImageIcon rawIcon = new ImageIcon("img/mmc.png"); 
        Image scaledImg = rawIcon.getImage();
        if (rawIcon.getIconWidth() > 0) {
             scaledImg = rawIcon.getImage().getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        }
        
        JLabel imgLabel = new JLabel(new ImageIcon(scaledImg));
        imgLabel.setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));

        JPanel imageContainer = new JPanel(new BorderLayout());
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
        imageContainer.add(imgLabel, BorderLayout.CENTER);
        imageContainer.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        center.add(imageContainer);
        center.add(Box.createVerticalStrut(20));

        // ---------- TITLE LABEL ----------
        JLabel selectLabel = new JLabel("Select Your Color");
        selectLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        selectLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        center.add(selectLabel);
        center.add(Box.createVerticalStrut(15));

        // ---------- PREVIEW PANEL ----------
        final JPanel previewPanel = new JPanel(new GridBagLayout());
        previewPanel.setPreferredSize(new Dimension(720, 220));
        previewPanel.setBackground(new Color(0x5DADE2));
        previewPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        previewPanel.setOpaque(true);

        // ---------- SHADES PANEL (Bottom Container) ----------
        // This will hold the generated color boxes
        final JPanel shadesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        shadesPanel.setBackground(Color.white); // Light gray background
        shadesPanel.setPreferredSize(new Dimension(780, 120));
        shadesPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        // ---------- BUTTON ROW ----------
        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(Color.WHITE);
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        buttonRow.add(createColorButton("RED",    new Color(0xE74C3C), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("GREEN",  new Color(0x27AE60), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("BLUE",   new Color(0x3498DB), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("BLACK",  Color.BLACK,         previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("YELLOW", new Color(0xF1C40F), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("GRAY",   new Color(0x7F8C8D), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20)); 
        buttonRow.add(createColorButton("Purple", new Color(0x5b21b6), previewPanel));
        buttonRow.add(Box.createHorizontalStrut(20)); 
       
        buttonRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        center.add(buttonRow);
        center.add(Box.createVerticalStrut(28));

       
        
        // ---------- COPY BUTTON ----------
        JButton copyBtn = new JButton("Copy");
        copyBtn.setFocusPainted(false);
        copyBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        copyBtn.setBackground(Color.WHITE);
        copyBtn.setOpaque(true);
        copyBtn.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        copyBtn.setPreferredSize(new Dimension(130, 40)); // Fixed size for uniformity

        copyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = previewPanel.getBackground();
                String hex = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(hex), null);
                JOptionPane.showMessageDialog(previewPanel, "Color " + hex + " copied!", "Copied", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        
        
        
        
        // ---------- GENERATE BUTTON (NEW) ----------
        JButton generateBtn = new JButton("Generate");
        generateBtn.setFocusPainted(false);
        generateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setOpaque(true);
        generateBtn.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        generateBtn.setPreferredSize(new Dimension(130, 40)); // Fixed size for uniformity

        generateBtn.addActionListener(new ActionListener() {
           
        	
        	@Override
            public void actionPerformed(ActionEvent e) {
                shadesPanel.removeAll(); // Clear previous shades
                Color baseColor = previewPanel.getBackground();

                // Create 9 shades ranging from light to dark
                // We will generate 4 lighter, the base, and 4 darker
                for (int i = 0; i < 9; i++) {
                    float factor = (i - 4) * 0.2f; // -0.8 to +0.8 range roughly
                    Color shade = createShade(baseColor, factor);
                    
                    // Create a small panel for each shade
                    JPanel swatch = new JPanel(new BorderLayout());
                    swatch.setPreferredSize(new Dimension(70, 80));
                    swatch.setBackground(shade);
                    swatch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    
                    // Hex Label at bottom
                    String hex = String.format("#%02X%02X%02X", shade.getRed(), shade.getGreen(), shade.getBlue());
                    JLabel hexLabel = new JLabel("<html><center>" + (100 * (i+1)) + "<br>" + hex + "</center></html>");
                    hexLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
                    hexLabel.setHorizontalAlignment(JLabel.CENTER);
                    // Text color based on background brightness
                    if((shade.getRed()*0.299 + shade.getGreen()*0.587 + shade.getBlue()*0.114) < 128) {
                        hexLabel.setForeground(Color.WHITE); 
                    } else {
                        hexLabel.setForeground(Color.BLACK);
                    }
                    
                    swatch.add(hexLabel, BorderLayout.SOUTH);
                    shadesPanel.add(swatch);
                }
                shadesPanel.revalidate();
                shadesPanel.repaint();
            }
        });

        
        
        
        // ---------- ADDING BUTTONS TO PREVIEW PANEL ----------
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Add Copy Button at top center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0); // Spacing below Copy button
        previewPanel.add(copyBtn, gbc);

       
        
        // Add Generate Button below Copy Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        previewPanel.add(generateBtn, gbc);

        // Add preview wrapper
        JPanel previewWrapper = new JPanel(new BorderLayout());
        previewWrapper.setBackground(Color.WHITE);
        previewWrapper.add(previewPanel, BorderLayout.CENTER);
        previewWrapper.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        center.add(previewWrapper);
        
        // Add shades panel at the very bottom
        center.add(Box.createVerticalStrut(20));
        center.add(shadesPanel);

        frame.setVisible(true);
    }

    // ---------- HELPER FOR MIXING COLORS ----------
    // factor < 0 makes it lighter (mix with white), factor > 0 makes it darker (mix with black)
    private static Color createShade(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if (factor < 0) {
            // Lighter: Mix with White (255, 255, 255)
            // factor is negative, so we use Math.abs
            float p = Math.abs(factor); 
            // Clamp p to max 0.9 to avoid pure white
            if (p > 0.9f) p = 0.9f;
            
            r = (int) (r + (255 - r) * p);
            g = (int) (g + (255 - g) * p);
            b = (int) (b + (255 - b) * p);
        } else {
            // Darker: Mix with Black (0, 0, 0)
            float p = 1 - factor;
            // Clamp p
            if (p < 0.1f) p = 0.1f;
            
            r = (int) (r * p);
            g = (int) (g * p);
            b = (int) (b * p);
        }
        // Ensure bounds
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));

        return new Color(r, g, b);
    }

    // ---------- COLOR BUTTON FACTORY  ----------
    private static JButton createColorButton(String text, final Color color, final JPanel previewPanel) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(110, 40));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);

        final Color normalBg = Color.WHITE;
        final Color normalFg = Color.BLACK;

        btn.setBackground(normalBg);
        btn.setForeground(normalFg);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 18, 5, 18)
        ));

        btn.setToolTipText(text + " color");

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color);
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(normalBg);
                btn.setForeground(normalFg);
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewPanel.setBackground(color);
            }
        });
        return btn;
    }
}
