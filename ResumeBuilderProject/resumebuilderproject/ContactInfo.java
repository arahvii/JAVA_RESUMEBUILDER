package resumebuilderproject;

/**
 * ContactInfo
 * Stores personal and contact details.
 */
public class ContactInfo {
    private String name;
    private String email;
    private String phone;
    private String portfolio;
    private String address;

    public ContactInfo(String name, String email, String phone, String portfolio, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.portfolio = portfolio;
        this.address = address;
    }

    // --- Output Methods ---

    public void display() {
        System.out.println(name.toUpperCase());
        System.out.println("-----------------------------------");
        System.out.printf("Email: %s | Phone: %s | Location: %s%n", email, phone, address);
        if (portfolio != null && !portfolio.isEmpty()) {
            System.out.println("Portfolio/GitHub: " + portfolio);
        }
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name.toUpperCase()).append("\n");
        sb.append("----------------------------------------------------\n");
        sb.append(String.format("Email: %s | Phone: %s | Location: %s\n", email, phone, address));
        if (portfolio != null && !portfolio.isEmpty()) {
            sb.append("Portfolio/GitHub: ").append(portfolio).append("\n");
        }
        return sb.toString();
    }
}