/**
 * SetAlarmFrame.java
 *
 * Author: DEHOU KONG
 * Date: June 16, 2024
 *
 * Description: Frame for setting a new alarm with time, ringtone, and frequency.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SetAlarmFrame extends JFrame {
    private User user;
    private AlarmReminderApp app;
    private JComboBox<String> ringtoneComboBox;
    private JComboBox<String> frequencyComboBox;

    public SetAlarmFrame(User user, AlarmReminderApp app) {
        this.user = user;
        this.app = app;

        setTitle("Set Alarm");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Hour:"));
        JTextField hourField = new JTextField();
        panel.add(hourField);

        panel.add(new JLabel("Minute:"));
        JTextField minuteField = new JTextField();
        panel.add(minuteField);

        panel.add(new JLabel("Frequency:"));
        String[] frequencies = {"Once", "Daily", "Weekly", "Monthly"};
        frequencyComboBox = new JComboBox<>(frequencies);
        panel.add(frequencyComboBox);

        panel.add(new JLabel("Ringtone:"));
        String[] ringtones = {"ring.wav", "newclap.wav", "dog.wav", "scary.wav", "medic.wav", "bomb.wav", "telephone.wav", "hitring.wav", "Custom"};
        ringtoneComboBox = new JComboBox<>(ringtones);
        ringtoneComboBox.setSelectedItem(user.getPreferredRingtone());
        panel.add(ringtoneComboBox);

        ringtoneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Custom".equals(ringtoneComboBox.getSelectedItem())) {
                    try {
                        Desktop.getDesktop().open(new File("ringtones"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int hour = Integer.parseInt(hourField.getText());
                int minute = Integer.parseInt(minuteField.getText());
                String frequency = (String) frequencyComboBox.getSelectedItem();
                String ringtone = (String) ringtoneComboBox.getSelectedItem();

                LocalDateTime nextTriggerTime = LocalDateTime.now().withHour(hour).withMinute(minute);

                Alarm alarm = new Alarm(hour, minute, ringtone, user.getUsername(), nextTriggerTime);
                alarm.setFrequency(frequency);
                app.addAlarm(alarm);
                dispose();
            }
        });

        add(panel);
    }
}
