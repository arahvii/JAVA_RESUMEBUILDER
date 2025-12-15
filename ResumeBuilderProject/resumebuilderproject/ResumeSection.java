package resumebuilderproject;

/**
 * ResumeSection
 * Abstract base class for all sections of the resume.
 * Demonstrates Abstraction and Inheritance.
 */
public abstract class ResumeSection {
    private final String title;

    public ResumeSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Abstract method to display content to the console.
     * Demonstrates Polymorphism.
     */
    public abstract void display();

    /**
     * Abstract method to return formatted content as a String for file output.
     * Demonstrates Abstraction for file generation.
     */
    public abstract String toFormattedString();
}