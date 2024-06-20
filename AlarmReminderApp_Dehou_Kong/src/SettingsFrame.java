/**
 * SettingsFrame.java
 *
 * Author: DEHOU KONG
 * Date: June 17, 2024
 *
 * Description: Frame for setting user preferences such as font size and color scheme.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
    private User user;
    private AlarmReminderApp app;

    public SettingsFrame(User user, AlarmReminderApp app) {
        this.user = user;
        this.app = app;

        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Font Size
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Font Size:"), gbc);

        String[] fontSizes = {"12", "14", "16", "18", "20"};
        JComboBox<String> fontSizeComboBox = new JComboBox<>(fontSizes);
        fontSizeComboBox.setSelectedItem(String.valueOf(user.getFontSize()));
        gbc.gridx = 1;
        panel.add(fontSizeComboBox, gbc);

        // Color Scheme
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Color Scheme:"), gbc);

        String[] colorSchemes = {"Black", "White", "Red", "Green", "Blue", "Yellow", "Magenta", "Cyan"};
        JComboBox<String> colorSchemeComboBox = new JComboBox<>(colorSchemes);
        colorSchemeComboBox.setSelectedItem(user.getColorScheme());
        gbc.gridx = 1;
        panel.add(colorSchemeComboBox, gbc);

        // Ringtone
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Ringtone:"), gbc);

        String[] ringtones = {"ring.wav", "newclap.wav", "dog.wav", "scary.wav", "medic.wav", "bomb.wav", "telephone.wav", "hitring.wav"};
        JComboBox<String> ringtoneComboBox = new JComboBox<>(ringtones);
        ringtoneComboBox.setSelectedItem(user.getPreferredRingtone());
        gbc.gridx = 1;
        panel.add(ringtoneComboBox, gbc);

        // Theme (Coming soon)
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Theme:"), gbc);

        JButton themeButton = new JButton("Coming soon...");
        themeButton.setEnabled(false);
        gbc.gridx = 1;
        panel.add(themeButton, gbc);

        // Language (Coming soon)
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Language:"), gbc);

        JButton languageButton = new JButton("Coming soon...");
        languageButton.setEnabled(false);
        gbc.gridx = 1;
        panel.add(languageButton, gbc);

        // Save button
        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fontSize = Integer.parseInt((String) fontSizeComboBox.getSelectedItem());
                String colorScheme = (String) colorSchemeComboBox.getSelectedItem();
                String ringtone = (String) ringtoneComboBox.getSelectedItem();

                user.updatePreferences(fontSize, colorScheme, ringtone, user.getPreferredTheme(), user.getPreferredLanguage());
                JOptionPane.showMessageDialog(SettingsFrame.this, "Settings saved!");
                app.updateApplicationAppearance(fontSize, colorScheme);
            }
        });
    }
}
