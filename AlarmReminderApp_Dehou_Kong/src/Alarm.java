import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alarm {
    private int hour;
    private int minute;
    private String ringtone;
    private String username;
    private LocalDateTime nextTriggerTime;
    private String frequency;

    public Alarm(int hour, int minute, String ringtone, String username, LocalDateTime nextTriggerTime) {
        this.hour = hour;
        this.minute = minute;
        this.ringtone = ringtone;
        this.username = username;
        this.nextTriggerTime = nextTriggerTime;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean shouldTrigger(LocalDateTime currentTime) {
        return currentTime.isAfter(nextTriggerTime);
    }

    public void updateNextTriggerTime() {
        switch (frequency) {
            case "Daily":
                nextTriggerTime = nextTriggerTime.plusDays(1);
                break;
            case "Weekly":
                nextTriggerTime = nextTriggerTime.plusWeeks(1);
                break;
            case "Monthly":
                nextTriggerTime = nextTriggerTime.plusMonths(1);
                break;
            default:
                break;
        }
    }

    public String getRingtone() {
        return ringtone;
    }

    @Override
    public String toString() {
        return "Alarm: " + nextTriggerTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", Ringtone: " + ringtone + ", Frequency: " + frequency
                ;
    }
}


