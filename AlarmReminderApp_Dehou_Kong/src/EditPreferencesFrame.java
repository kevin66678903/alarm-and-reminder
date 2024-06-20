/**
 * EditPreferencesFrame.java
 *
 * Author: DEHOU KONG
 * Date: June 15, 2024
 *
 * Description: Frame for editing user preferences.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EditPreferencesFrame extends JFrame {
    private JTextField textFieldFontSize;
    private JTextField textFieldColorScheme;
    private JTextField textFieldTheme;
    private JTextField textFieldLanguage;
    private User currentUser;

    public EditPreferencesFrame(User user) {
        this.currentUser = user;
        setTitle("Edit Preferences");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JLabel lblFontSize = new JLabel("Font Size:");
        lblFontSize.setBounds(68, 52, 78, 16);
        getContentPane().add(lblFontSize);

        textFieldFontSize = new JTextField();
        textFieldFontSize.setBounds(158, 46, 130, 26);
        getContentPane().add(textFieldFontSize);
        textFieldFontSize.setColumns(10);

        JLabel lblColorScheme = new JLabel("Color Scheme:");
        lblColorScheme.setBounds(68, 92, 78, 16);
        getContentPane().add(lblColorScheme);

        textFieldColorScheme = new JTextField();
        textFieldColorScheme.setBounds(158, 86, 130, 26);
        getContentPane().add(textFieldColorScheme);
        textFieldColorScheme.setColumns(10);

        JLabel lblTheme = new JLabel("Theme:");
        lblTheme.setBounds(68, 132, 78, 16);
        getContentPane().add(lblTheme);

        textFieldTheme = new JTextField();
        textFieldTheme.setBounds(158, 126, 130, 26);
        getContentPane().add(textFieldTheme);
        textFieldTheme.setColumns(10);

        JLabel lblLanguage = new JLabel("Language:");
        lblLanguage.setBounds(68, 172, 78, 16);
        getContentPane().add(lblLanguage);

        textFieldLanguage = new JTextField();
        textFieldLanguage.setBounds(158, 166, 130, 26);
        getContentPane().add(textFieldLanguage);
        textFieldLanguage.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fontSize = textFieldFontSize.getText();
                String colorScheme = textFieldColorScheme.getText();
                String theme = textFieldTheme.getText();
                String language = textFieldLanguage.getText();
                if (savePreferences(fontSize, colorScheme, theme, language)) {
                    JOptionPane.showMessageDialog(null, "Preferences saved successfully");
                    new ViewPreferencesFrame(currentUser).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save preferences");
                }
            }
        });
        btnSave.setBounds(158, 204, 117, 29);
        getContentPane().add(btnSave);
    }

    private boolean savePreferences(String fontSize, String colorScheme, String theme, String language) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("preferences.txt", true))) {
            bw.write(currentUser.getUsername() + "," + fontSize + ";" + colorScheme + ";" + theme + ";" + language);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
