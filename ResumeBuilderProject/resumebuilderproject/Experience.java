package resumebuilderproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Experience
 * Concrete section for all professional experience entries.
 * Internal class ExperienceEntry handles the data model for each job.
 */
public class Experience extends ResumeSection {
    private final List<ExperienceEntry> entries;

    public Experience() {
        super("Professional Experience");
        this.entries = new ArrayList<>();
    }
    
    // Nested data model class for a single job
    public static class ExperienceEntry {
        private String title;
        private String company;
        private String duration;
        private String description; // Typically a bulleted list of achievements/tasks

        public ExperienceEntry(String title, String company, String duration, String description) {
            this.title = title;
            this.company = company;
            this.duration = duration;
            this.description = description;
        }

        // Getters and Setters (omitted for brevity)

        public String toFormattedString() {
            return String.format("%s at %s (%s)\n- %s", title, company, duration, description);
        }
    }

    public void addEntry(String title, String company, String duration, String description) {
        this.entries.add(new ExperienceEntry(title, company, duration, description));
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        for (ExperienceEntry entry : entries) {
            System.out.println(entry.toFormattedString());
        }
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        for (ExperienceEntry entry : entries) {
            sb.append(entry.toFormattedString()).append("\n");
        }
        return sb.toString().trim();
    }
}