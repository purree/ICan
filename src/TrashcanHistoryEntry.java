import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TrashcanHistoryEntry {
    private Date timestamp;
    private double temperature;
    private double trashLevel;
    private CanStatus canStatus;
    private Date lastEmptied;

    public TrashcanHistoryEntry(double temperature, double trashLevel, CanStatus canStatus, Date lastEmptied) {
        this.timestamp = new Date();
        this.temperature = temperature;
        this.trashLevel = trashLevel;
        this.canStatus = canStatus;
        this.lastEmptied = lastEmptied;
    }

    public TrashcanHistoryEntry(JSONObject Job) {
        this.timestamp = new Date();
        try {
            this.temperature = Job.getDouble("temperature");
            this.trashLevel = Job.getDouble("trashLevel");
            this.lastEmptied = new Date(Long.parseLong(Job.getString("lastEmptied")));
            this.canStatus = CanStatus.valueOf(Job.getString("canStatus"));
        } catch (JSONException ex) {
            System.out.println(ex.toString());
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "TrashcanHistoryEntry{" +
                "timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", trashLevel=" + trashLevel +
                ", lastEmptied=" + lastEmptied +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"timestamp\":\"" + timestamp +
                "\", \"temperature\":\"" + temperature +
                "\", \"trashLevel\":\"" + trashLevel +
                "\", \"lastEmptied\":\"" + lastEmptied.toString() +
                "\", \"canStatus\":\"" + canStatus.toString() + "\"}";
    }
}
