package resumebuilderproject;

/**
 * SkillEntry
 * Model class for a single skill or skill category.
 */
public class SkillEntry {
    private String category; // e.g., "Programming Languages"
    private String skills;   // Comma-separated list of skills e.g., "Python, Java, C++"

    public SkillEntry(String category, String skills) {
        this.category = category;
        this.skills = skills;
    }

    // Getters and Setters
    public String getCategory() { return category; }
    public String getSkills() { return skills; }

    public void setCategory(String category) { this.category = category; }
    public void setSkills(String skills) { this.skills = skills; }
}