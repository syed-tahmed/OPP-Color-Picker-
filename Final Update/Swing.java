import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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

public class colornew {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(colornew::createAndShowUI);
    }

    private static void createAndShowUI() {
       
    	
    	// ---------- FRAME ----------
        JFrame frame = new JFrame("Color Picker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
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

        
        
        // ---------- IMAGE ----------
        final int IMG_WIDTH = 780;
        final int IMG_HEIGHT = 340;

        ImageIcon rawIcon = new ImageIcon("icc/mmc.png");
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

       
        
        // ---------- SELECT COLOR LABEL ----------
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

        
        
        // ---------- SHADES PANEL ----------
        final JPanel shadesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        shadesPanel.setBackground(Color.white);
        shadesPanel.setPreferredSize(new Dimension(780, 120));
        shadesPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

       
        
        // ---------- MAIN COLOR BUTTONS ----------
        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(Color.WHITE);
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        buttonRow.add(createColorButton("RED", new Color(0xE74C3C), previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("GREEN", new Color(0x27AE60), previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("BLUE", new Color(0x3498DB), previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("BLACK", Color.BLACK, previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("YELLOW", new Color(0xF1C40F), previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("GRAY", new Color(0x7F8C8D), previewPanel, shadesPanel));
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(createColorButton("PURPLE", new Color(0x5b21b6), previewPanel, shadesPanel));

        buttonRow.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        center.add(buttonRow);
        center.add(Box.createVerticalStrut(28));

       
        
        // ---------- COPY BUTTON ----------
        JButton copyBtn = new JButton("Copy");
        copyBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        copyBtn.setBackground(Color.WHITE);
        copyBtn.setFocusPainted(false);
        copyBtn.setOpaque(true);
        copyBtn.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));

        copyBtn.addActionListener(e -> {
            Color c = previewPanel.getBackground();
            String hex = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(hex), null);
            JOptionPane.showMessageDialog(previewPanel, "Color " + hex + " copied!", "Copied",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        
        
        
        // ---------- GENERATE SHADES BUTTON ----------
        JButton generateBtn = new JButton("Generate");
        generateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        generateBtn.setBackground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.setOpaque(true);
        generateBtn.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shadesPanel.removeAll();
                Color baseColor = previewPanel.getBackground();

                for (int i = 0; i < 9; i++) {
                    float factor = (i - 4) * 0.2f;
                    Color shade = createShade(baseColor, factor);

                    JPanel swatch = new JPanel(new BorderLayout());
                    swatch.setPreferredSize(new Dimension(70, 80));
                    swatch.setBackground(shade);
                    swatch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    String hex = String.format("#%02X%02X%02X", shade.getRed(), shade.getGreen(),
                            shade.getBlue());

                    JLabel hexLabel = new JLabel("<html><center>" + (100 * (i + 1)) + "<br>" + hex
                            + "</center></html>");
                    hexLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
                    hexLabel.setHorizontalAlignment(JLabel.CENTER);

                    double brightness = (shade.getRed() * 0.299 + shade.getGreen() * 0.587
                            + shade.getBlue() * 0.114);

                    hexLabel.setForeground(brightness < 128 ? Color.WHITE : Color.BLACK);

                    //  CLICK TO COPY SUB-COLOR 
                    swatch.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(new StringSelection(hex), null);
                            JOptionPane.showMessageDialog(shadesPanel, hex + " copied!", "Copied",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            swatch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            swatch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                        }
                    });

                    swatch.add(hexLabel, BorderLayout.SOUTH);
                    shadesPanel.add(swatch);
                }

                shadesPanel.revalidate();
                shadesPanel.repaint();
            }
        });

        // ---------- PREVIEW PANEL BUTTON LAYOUT ----------
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        previewPanel.add(copyBtn, gbc);

        gbc.gridy =1;
        previewPanel.add(generateBtn, gbc);

        JPanel previewWrapper = new JPanel(new BorderLayout());
        previewWrapper.setBackground(Color.WHITE);
        previewWrapper.add(previewPanel, BorderLayout.CENTER);
        previewWrapper.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        center.add(previewWrapper);

        center.add(Box.createVerticalStrut(20));
        center.add(shadesPanel);

        frame.setVisible(true);
    }

    // ---------- SHADE GENERATOR ----------
    private static Color createShade(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if (factor < 0) {
            float p = Math.abs(factor);
            if (p > 0.9f) p = 0.9f;
            r = (int) (r + (255 - r) * p);
            g = (int) (g + (255 - g) * p);
            b = (int) (b + (255 - b) * p);
        } else {
            float p = 1 - factor;
            if (p < 0.1f) p = 0.1f;
            r = (int) (r * p);
            g = (int) (g * p);
            b = (int) (b * p);
        }

        return new Color(r, g, b);
    }

    // ---------- MAIN COLOR BUTTON CREATOR ----------
    private static JButton createColorButton(String text, final Color color, final JPanel previewPanel, final JPanel shadesPanel) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(110, 40));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);

        final Color normalBg = Color.WHITE;
        final Color normalFg = Color.BLACK;

        btn.setBackground(normalBg);
        btn.setForeground(normalFg);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 18, 5, 18)));

        
        
      //mouse hover effect 
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

        btn.addActionListener(e -> {
            previewPanel.setBackground(color);
            shadesPanel.removeAll();
            shadesPanel.revalidate();
            shadesPanel.repaint();
        });

        return btn;
    }
}
