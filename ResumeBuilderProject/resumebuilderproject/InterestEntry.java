package resumebuilderproject;

/**
 * InterestEntry
 * Model class for a single interest entry, which is used by the Interests section class.
 */
public class InterestEntry {
    private String interestName;

    public InterestEntry(String interestName) {
        this.interestName = interestName;
    }

    // Getters and Setters
    public String getInterestName() { return interestName; }

    public void setInterestName(String interestName) { this.interestName = interestName; }
}