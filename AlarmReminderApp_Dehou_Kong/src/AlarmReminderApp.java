/**
 * AlarmReminderApp.java
 *
 * Author: DEHOU KONG
 * Date: June 18, 2024
 *
 * Description: Main application class for the Alarm and Reminder App.
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;

public class AlarmReminderApp extends JFrame {
    private User currentUser;
    private JLabel currentTimeLabel;
    private Timer timer;
    private List<Alarm> alarms = new ArrayList<>();
    private DefaultListModel<String> alarmListModel;

    public AlarmReminderApp(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Alarm and Reminder App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        updateApplicationAppearance(currentUser.getFontSize(), currentUser.getColorScheme());

        currentTimeLabel = new JLabel();
        currentTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentTimeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(currentTimeLabel, BorderLayout.NORTH);
        updateCurrentTime();

        timer = new Timer(1000, e -> {
            updateCurrentTime();
            checkAlarms();
        });
        timer.start();

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // 使用 GridLayout 并设置间距

        JButton btnAlarm = new JButton("Alarm");
        JButton btnMemo = new JButton("Memo");
        JButton btnPreferences = new JButton("Preferences");
        JButton btnSettings = new JButton("Settings");

        Dimension buttonSize = new Dimension(150, 50);
        btnAlarm.setPreferredSize(buttonSize);
        btnMemo.setPreferredSize(buttonSize);
        btnPreferences.setPreferredSize(buttonSize);
        btnSettings.setPreferredSize(buttonSize);

        buttonPanel.add(btnAlarm);
        buttonPanel.add(btnMemo);
        buttonPanel.add(btnPreferences);
        buttonPanel.add(btnSettings);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        alarmListModel = new DefaultListModel<>();
        JList<String> alarmList = new JList<>(alarmListModel);
        JScrollPane scrollPane = new JScrollPane(alarmList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnAlarm.addActionListener(e -> new SetAlarmFrame(currentUser, this).setVisible(true));
        btnMemo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Memo is coming soon..."));
        btnPreferences.addActionListener(e -> new ViewPreferencesFrame(currentUser).setVisible(true));
        btnSettings.addActionListener(e -> new SettingsFrame(currentUser, this).setVisible(true));

        alarmList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedAlarm = alarmList.getSelectedValue();
                if (selectedAlarm != null) {
                    int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this alarm?", "Delete Alarm", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        deleteAlarm(selectedAlarm);
                    }
                }
            }
        });

        loadAlarms();
    }

    private void updateCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentTimeLabel.setText(now.format(formatter));
    }

    private void checkAlarms() {
        LocalDateTime now = LocalDateTime.now();
        for (Alarm alarm : alarms) {
            if (alarm.shouldTrigger(now)) {
                playRingtone(alarm.getRingtone());
                alarm.updateNextTriggerTime();
            }
        }
        saveAlarms(); // Save the updated trigger times to the file
    }

    private void playRingtone(String ringtone) {
        try {
            File soundFile = new File("ringtones/" + ringtone);
            if (soundFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.err.println("Ringtone file not found: " + ringtone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAlarms() {
        alarms.clear();
        alarmListModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("alarms.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] alarmInfo = line.split(",");
                if (alarmInfo.length == 6 && alarmInfo[3].equals(currentUser.getUsername())) {
                    int hour = Integer.parseInt(alarmInfo[0]);
                    int minute = Integer.parseInt(alarmInfo[1]);
                    String ringtone = alarmInfo[2];
                    String frequency = alarmInfo[4];
                    LocalDateTime nextTriggerTime = LocalDateTime.parse(alarmInfo[5], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    alarms.add(new Alarm(hour, minute, ringtone, frequency, nextTriggerTime));
                    alarmListModel.addElement(alarmInfo[0] + " - " + alarmInfo[1] + " - " + alarmInfo[2] + " - " + alarmInfo[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
        alarmListModel.addElement(alarm.toString());
        saveAlarms();
    }

    private void deleteAlarm(String alarmString) {
        Alarm alarmToRemove = null;
        for (Alarm alarm : alarms) {
            if (alarm.toString().equals(alarmString)) {
                alarmToRemove = alarm;
                break;
            }
        }
        if (alarmToRemove != null) {
            alarms.remove(alarmToRemove);
            alarmListModel.removeElement(alarmString);
            saveAlarms();
        }
    }

    private void saveAlarms() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alarms.txt"))) {
            for (Alarm alarm : alarms) {
                writer.write(alarm.toString() + "," + currentUser.getUsername() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateApplicationAppearance(int fontSize, String colorScheme) {
        Font font = new Font("Serif", Font.PLAIN, fontSize);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);

        Color color;
        switch (colorScheme.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "white":
                color = Color.WHITE;
                break;
            case "red":
                color = Color.RED;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "magenta":
                color = Color.MAGENTA;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            default:
                color = Color.BLACK;
                System.err.println("Invalid color scheme format: " + colorScheme);
        }

        UIManager.put("Label.foreground", color);
        UIManager.put("Button.foreground", color);
        UIManager.put("TextField.foreground", color);
        UIManager.put("TextArea.foreground", color);

        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
