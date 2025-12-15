package resumebuilderproject;

/**
 * EducationEntry
 * Model class for a single educational entry (degree, institution, dates).
 */
public class EducationEntry {
    private String degree;
    private String institution;
    private String duration; // e.g., "Jan 2024 - Jan 2025"

    public EducationEntry(String degree, String institution, String duration) {
        this.degree = degree;
        this.institution = institution;
        this.duration = duration;
    }

    // Getters and Setters
    public String getDegree() { return degree; }
    public String getInstitution() { return institution; }
    public String getDuration() { return duration; }

    public void setDegree(String degree) { this.degree = degree; }
    public void setInstitution(String institution) { this.institution = institution; }
    public void setDuration(String duration) { this.duration = duration; }
}