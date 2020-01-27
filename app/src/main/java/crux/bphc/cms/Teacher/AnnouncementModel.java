package crux.bphc.cms.Teacher;

public class AnnouncementModel
{
    String announcementText;
    String timestamp;

    public AnnouncementModel(String announcementText, String timestamp) {
        this.announcementText = announcementText;
        this.timestamp = timestamp;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
