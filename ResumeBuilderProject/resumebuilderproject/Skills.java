package resumebuilderproject;

import java.util.List;

/**
 * Skills
 * Concrete section for technical and language skills.
 */
public class Skills extends ResumeSection {
    private final List<String> technicalSkills;
    private final List<String> languages;

    public Skills(List<String> technicalSkills, List<String> languages) {
        super("Skills");
        this.technicalSkills = technicalSkills;
        this.languages = languages;
    }

    private String listToString(List<String> list) {
        return String.join(" | ", list);
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        System.out.println("Technical: " + listToString(technicalSkills));
        System.out.println("Languages: " + listToString(languages));
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        sb.append("Technical: ").append(listToString(technicalSkills)).append("\n");
        sb.append("Languages: ").append(listToString(languages));
        return sb.toString();
    }
}