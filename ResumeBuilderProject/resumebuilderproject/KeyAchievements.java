package resumebuilderproject;

/**
 * KeyAchievements
 * Placeholder section for achievements.
 */
public class KeyAchievements extends ResumeSection {
    private String achievement;

    public KeyAchievements(String achievement) {
        super("Key Achievements");
        this.achievement = achievement;
    }

    @Override
    public void display() {
        System.out.println("\n--- " + getTitle().toUpperCase() + " ---");
        System.out.println(achievement);
    }

    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(getTitle().toUpperCase()).append(" ---\n");
        sb.append(achievement);
        return sb.toString();
    }
}