package resumebuilderproject;

/**
 * LanguageEntry
 * Model class for a single language and proficiency level.
 */
public class LanguageEntry {
    private String language;
    private String proficiency; // e.g., "Native", "Fluent", "Intermediate"

    public LanguageEntry(String language, String proficiency) {
        this.language = language;
        this.proficiency = proficiency;
    }

    // Getters and Setters
    public String getLanguage() { return language; }
    public String getProficiency() { return proficiency; }

    public void setLanguage(String language) { this.language = language; }
    public void setProficiency(String proficiency) { this.proficienc