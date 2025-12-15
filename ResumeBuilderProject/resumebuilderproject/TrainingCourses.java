package resumebuilderproject;

/**
 * TrainingCourses
 * Concrete section for training and certifications.
 */
public class TrainingCourses extends ResumeSection {
    private String courseName;
    private String organization;

    public TrainingCourses(String courseName, String organization) {
        super("Training/Courses");
        this.courseName = courseName;
        this.organization = organization;
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        System.out.printf("%s from %s%n", courseName, organization);
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        sb.append(String.format("%s from %s", courseName, organization));
        return sb.toString();
    }
}