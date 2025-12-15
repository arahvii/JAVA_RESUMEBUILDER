package resumebuilderproject;

/**
 * Summary
 * Concrete section for the professional summary.
 */
public class Summary extends ResumeSection {
    private String content;

    public Summary(String content) {
        super("Summary");
        this.content = content;
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        System.out.println(content);
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        sb.append(content);
        return sb.toString();
    }
}