/**
 * ViewPreferencesFrame.java
 *
 * Author: DEHOU KONG
 * Date: June 17, 2024
 *
 * Description: Frame for viewing current user preferences.
 */

import javax.swing.*;
import java.awt.*;

public class ViewPreferencesFrame extends JFrame {
    private User user;

    public ViewPreferencesFrame(User user) {
        this.user = user;

        setTitle("View Preferences");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Username:"));
        panel.add(new JLabel(user.getUsername()));

        panel.add(new JLabel("Font Size:"));
        panel.add(new JLabel(String.valueOf(user.getFontSize())));

        panel.add(new JLabel("Color Scheme:"));
        panel.add(new JLabel(user.getColorScheme()));

        panel.add(new JLabel("Ringtone:"));
        panel.add(new JLabel(user.getPreferredRingtone()));

        add(panel);
    }
}
