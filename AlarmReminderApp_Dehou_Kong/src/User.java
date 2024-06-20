/**
 * User.java
 *
 * Author: DEHOU KONG
 * Date: June 18, 2024
 *
 * Description: Class representing a user with preferences and credentials.
 */

import java.io.*;

public class User {
    private String username;
    private String password;
    private int fontSize;
    private String colorScheme;
    private String preferredRingtone;
    private String preferredTheme;
    private String preferredLanguage;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.fontSize = 14;
        this.colorScheme = "#000000";
        this.preferredRingtone = "ring.wav";
        this.preferredTheme = "Light";
        this.preferredLanguage = "English";
        loadPreferences();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getFontSize() {
        return fontSize;
    }


    public String getColorScheme() {
        return colorScheme;
    }

    public String getPreferredRingtone() {
        return preferredRingtone;
    }

    public String getPreferredTheme() {
        return preferredTheme;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void loadPreferences() {
        try (BufferedReader reader = new BufferedReader(new FileReader("userpreferences.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] prefInfo = line.split(",");
                if (prefInfo.length == 6 && prefInfo[0].equals(this.username)) {
                    this.fontSize = Integer.parseInt(prefInfo[1]);
                    this.colorScheme = prefInfo[2];
                    this.preferredRingtone = prefInfo[3];
                    this.preferredTheme = prefInfo[4];
                    this.preferredLanguage = prefInfo[5];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePreferences() {
        File inputFile = new File("userpreferences.txt");
        File tempFile = new File("userpreferences_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;
            while ((line = reader.readLine()) != null) {
                String[] prefInfo = line.split(",");
                if (prefInfo[0].equals(username)) {
                    writer.write(username + "," + fontSize + "," + colorScheme + "," + preferredRingtone + "," + preferredTheme + "," + preferredLanguage + "\n");
                    updated = true;
                } else {
                    writer.write(line + "\n");
                }
            }
            if (!updated) {
                writer.write(username + "," + fontSize + "," + colorScheme + "," + preferredRingtone + "," + preferredTheme + "," + preferredLanguage + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.err.println("Could not delete file");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.err.println("Could not rename file");
        }
    }

    public void updatePreferences(int fontSize, String colorScheme, String ringtone, String theme, String language) {
        this.fontSize = fontSize;
        this.colorScheme = colorScheme;
        this.preferredRingtone = ringtone;
        this.preferredTheme = theme;
        this.preferredLanguage = language;
        savePreferences();
    }

    // search user
    public static User getUserByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length >= 2 && userInfo[0].equals(username)) {
                    return new User(userInfo[0], userInfo[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
