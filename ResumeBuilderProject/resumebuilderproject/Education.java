package resumebuilderproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Education
 * Concrete section for all educational entries.
 * Internal class EducationEntry handles the data model for each degree/certificate.
 */
public class Education extends ResumeSection {
    private final List<EducationEntry> entries;

    public Education() {
        super("Education");
        this.entries = new ArrayList<>();
    }

    // Nested data model class for a single education entry
    public static class EducationEntry {
        private String degree;
        private String institution;
        private int graduationYear;
        private double gpa;

        public EducationEntry(String degree, String institution, int graduationYear, double gpa) {
            this.degree = degree;
            this.institution = institution;
            this.graduationYear = graduationYear;
            this.gpa = gpa;
        }
        
        // Getters and Setters (omitted for brevity)

        public String toFormattedString() {
            return String.format("%s, %s (Graduated: %d) - GPA: %.2f", degree, institution, graduationYear, gpa);
        }
    }

    public void addEntry(String degree, String institution, int graduationYear, double gpa) {
        this.entries.add(new EducationEntry(degree, institution, graduationYear, gpa));
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        for (EducationEntry entry : entries) {
            System.out.println(entry.toFormattedString());
        }
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        for (EducationEntry entry : entries) {
            sb.append(entry.toFormattedString()).append("\n");
        }
        return sb.toString().trim();
    }
}