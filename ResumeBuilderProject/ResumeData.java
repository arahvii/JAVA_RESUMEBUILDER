/**
 * ResumeData
 * Data model for a single entry in the ResumeBuilderGUI JTable.
 * Encapsulates the field name (e.g., "Name") and its value (e.g., "NAME1").
 * NOTE: This version runs without a package for easier command-line execution.
 */
public class ResumeData {
    private String fieldName;
    private String value;

    public ResumeData(String fieldName, String value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    // Getters for table display
    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }
    
    // Setter (if editing is allowed, and required for table updates)
    public void setValue(String value) {
        this.value = value;
    }
}