package resumebuilderproject;

/**
 * AchievementEntry
 * Model class for a key achievement with a title and multi-line description.
 */
public class AchievementEntry {
    private String title;
    private String description; // Multi-line text for bullet points

    public AchievementEntry(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
}