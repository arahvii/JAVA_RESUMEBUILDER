package resumebuilderproject;

import java.util.List;

/**
 * Interests
 * Concrete section for personal interests.
 */
public class Interests extends ResumeSection {
    private final List<String> interests;

    public Interests(List<String> interests) {
        super("Interests");
        this.interests = interests;
    }

    private String listToString(List<String> list) {
        return String.join(" | ", list);
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        System.out.println(listToString(interests));
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        sb.append(listToString(interests));
        return sb.toString();
    }
}