import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ResumeBuilderGUI
 * Main application window using Java Swing to collect resume data.
 * Now generates a LaTeX (.tex) file for PDF compilation.
 */
public class ResumeBuilderGUI extends JFrame {

    private List<ResumeData> resumeEntries;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public ResumeBuilderGUI() {
        super("Java Swing Resume Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        resumeEntries = new ArrayList<>();
        initializeData();
        
        setupTable();
        setupControls();
        
        // Initial call to adjust heights after the table is fully set up
        adjustRowHeights();

        setVisible(true);
    }
    
    // Initializes the table with default resume data
    private void initializeData() {
        resumeEntries.add(new ResumeData("Name", "Jane Doe"));
        resumeEntries.add(new ResumeData("Role", "Senior Software Engineer"));
        resumeEntries.add(new ResumeData("Email", "jane.doe@email.com"));
        resumeEntries.add(new ResumeData("LinkedIn", "https://linkedin.com/in/janedoe"));
        
        // Example of a LONG text entry that requires wrapping
        resumeEntries.add(new ResumeData("Professional Summary", "Highly motivated Full-Stack Software Developer with extensive experience across the entire development lifecycle, specializing in complex enterprise solutions using Java, Spring Boot, and modern React/Angular frontend frameworks. Proven ability to deliver scalable and efficient code in fast-paced Agile environments. Seeking to leverage skills in a challenging technical role."));
        
        resumeEntries.add(new ResumeData("Experience", "Software Engineer at Tech Solutions Company Corp. | Apr 2025 - Present. Developed and maintained core microservices for customer data processing, resulting in a 20% reduction in latency. Led a team of three junior developers in the migration to a new cloud platform. Optimized database queries and deployed containerized services using Kubernetes."));
        
        resumeEntries.add(new ResumeData("Key Achievements", "Performance Optimization Reduced the average API response time for a major product by 40ms by implementing query caching and optimizing database indexes. Mentored new hires."));
        resumeEntries.add(new ResumeData("Education", "Bachelor of Science in Computer Science | 2018 - 2022 | University of Technology"));
        resumeEntries.add(new ResumeData("Skills", "Programming: Java, Python, C++, TypeScript; Frameworks: Spring Boot, React, Node.js; Cloud/DevOps: AWS, Docker, Kubernetes, Jenkins; Databases: PostgreSQL, MongoDB"));
        resumeEntries.add(new ResumeData("Interests", "Cybersecurity & Ethical Hacking; Actively researching security vulnerabilities and participating in bug bounty programs."));
    }

    private void setupTable() {
        String[] columnNames = {"Field Name", "Value"};
        
        Object[][] rowData = new Object[resumeEntries.size()][2];
        for (int i = 0; i < resumeEntries.size(); i++) {
            ResumeData data = resumeEntries.get(i);
            rowData[i][0] = data.getFieldName();
            rowData[i][1] = data.getValue();
        }

        tableModel = new DefaultTableModel(rowData, columnNames) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 1; // Only the Value column is editable
             }
        };
        
        dataTable = new JTable(tableModel);
        
        // Apply the custom renderer/editor to the 'Value' column (index 1)
        dataTable.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer());
        dataTable.getColumnModel().getColumn(1).setCellEditor(new TextAreaEditor());
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listener to update data model when table cell is edited and adjust heights
        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) { 
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (col == 1) { 
                    String newValue = (String) tableModel.getValueAt(row, col);
                    if (row >= 0 && row < resumeEntries.size()) {
                        resumeEntries.get(row).setValue(newValue);
                        // Call adjustment method only after data change
                        SwingUtilities.invokeLater(() -> adjustRowHeights());
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Calculates the required height for every row based on the wrapped content 
     * and sets the row heights on the table. This prevents flickering.
     */
    private void adjustRowHeights() {
        TextAreaRenderer renderer = new TextAreaRenderer();
        for (int row = 0; row < dataTable.getRowCount(); row++) {
            // Check if the table is ready (has columns/data)
            if (dataTable.getColumnModel().getColumnCount() < 2) continue; 
            
            // Get the width of the column the renderer will use
            int columnWidth = dataTable.getColumnModel().getColumn(1).getWidth();
            
            // Must have a minimum width to calculate wrapping correctly
            if (columnWidth < 50) return; 

            // Set the width of the temporary renderer component for correct size calculation
            renderer.setSize(columnWidth, Integer.MAX_VALUE); 
            
            // Get the value
            Object value = dataTable.getValueAt(row, 1);
            renderer.setText(value == null ? "" : value.toString());
            
            // Calculate the minimum preferred height required to show all wrapped text
            int preferredHeight = (int) renderer.getPreferredSize().getHeight();
            
            // Ensure a minimum height is maintained (e.g., for single lines)
            int minHeight = dataTable.getFontMetrics(dataTable.getFont()).getHeight() + 6; 
            int newHeight = Math.max(preferredHeight, minHeight);
            
            // Set the row height only if it needs to change
            if (dataTable.getRowHeight(row) != newHeight) {
                dataTable.setRowHeight(row, newHeight);
            }
        }
    }


    private void setupControls() {
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Add New Entry");
        // Renamed button text to reflect the new output format
        JButton generateLatexButton = new JButton("Generate PDF (.tex)");
        
        addButton.addActionListener(new AddEntryListener());
        generateLatexButton.addActionListener(new GenerateLatexListener());
        
        controlPanel.add(addButton);
        controlPanel.add(generateLatexButton);
        
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class AddEntryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fieldName = JOptionPane.showInputDialog(ResumeBuilderGUI.this, "Enter Field Name:");
            if (fieldName == null || fieldName.trim().isEmpty()) return;

            String value = JOptionPane.showInputDialog(ResumeBuilderGUI.this, "Enter Value for " + fieldName + ":");
            if (value == null || value.trim().isEmpty()) return;

            ResumeData newEntry = new ResumeData(fieldName, value);
            resumeEntries.add(newEntry);
            
            tableModel.addRow(new Object[]{newEntry.getFieldName(), newEntry.getValue()});
            
            // Adjust heights for all rows after adding a new one
            SwingUtilities.invokeLater(() -> adjustRowHeights());
        }
    }
    
    private class GenerateLatexListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            generateLatexOutput(resumeEntries, "resume_output.tex"); 
        }
    }

    /**
     * Generates a complete LaTeX document string from the resume data and saves it.
     * @param entries List of resume data entries.
     * @param filename Output filename (.tex).
     */
    private void generateLatexOutput(List<ResumeData> entries, String filename) {
        
        // 1. Convert List to Map for easier lookup of header fields
        Map<String, String> dataMap = entries.stream()
            .collect(Collectors.toMap(
                d -> d.getFieldName().toLowerCase(), 
                ResumeData::getValue,
                (v1, v2) -> v1 // handle duplicates by keeping the first one
            ));

        // 2. Extract key header fields
        String name = dataMap.getOrDefault("name", "Name Here");
        String role = dataMap.getOrDefault("role", "Professional Role");
        String email = dataMap.getOrDefault("email", "email@example.com");
        String linkedin = dataMap.getOrDefault("linkedin", "LinkedIn URL");
        
        // 3. Build the LaTeX content
        StringBuilder latexContent = new StringBuilder();
        
        latexContent.append("\\documentclass[11pt, a4paper]{article}\n");
        
        // --- UNIVERSAL PREAMBLE BLOCK ---
        latexContent.append("\\usepackage[a4paper, top=2.5cm, bottom=2.5cm, left=2cm, right=2cm]{geometry}\n");
        latexContent.append("\\usepackage{fontspec}\n");
        latexContent.append("\\usepackage{parskip}\n"); // Provides space between paragraphs instead of indentation
        latexContent.append("\\usepackage[english, bidi=basic, provide=*]{babel}\n");
        latexContent.append("\\babelprovide[import, onchar=ids fonts]{english}\n");
        latexContent.append("\\babelfont{rm}{Noto Sans}\n");
        latexContent.append("\\usepackage{array}\n"); // For table/array formatting
        latexContent.append("\\usepackage{hyperref}\n\n");
        // ---------------------------------
        
        latexContent.append("\\begin{document}\n\n");
        
        // --- Title/Header Section ---
        latexContent.append("\\begin{center}\n");
        latexContent.append("    \\textbf{\\Huge ").append(name).append("}\\\\\n");
        latexContent.append("    \\large\\textit{").append(role).append("}\n");
        latexContent.append("\\end{center}\n");
        
        latexContent.append("\\hrule\n");
        latexContent.append("\\vspace{0.1cm}\n");
        
        // Contact Information
        latexContent.append("\\small\\begin{center}\n");
        latexContent.append("    \\texttt{Email: ").append(email).append("} $|$ \n");
        latexContent.append("    \\texttt{LinkedIn: ").append(linkedin).append("}\n");
        latexContent.append("\\end{center}\n");
        latexContent.append("\\hrule\n");
        latexContent.append("\\vspace{0.5cm}\n");
        
        // --- Resume Content Sections ---
        for (ResumeData data : entries) {
            String field = data.getFieldName();
            String value = data.getValue()
                               // Simple LaTeX escaping: replace & with \& (for math/commands)
                               .replace("&", "\\&");
            String lowerField = field.toLowerCase();

            // Skip fields already used in the header
            if (lowerField.equals("name") || lowerField.equals("role") || 
                lowerField.equals("email") || lowerField.equals("linkedin")) {
                continue;
            }
            
            // Simple Section structure
            latexContent.append("\\section*{").append(field.toUpperCase()).append("}\n");
            latexContent.append(value).append("\n\n");
        }
        
        latexContent.append("\\end{document}\n");
        
        
        // 4. Save the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(latexContent.toString());

            JOptionPane.showMessageDialog(ResumeBuilderGUI.this, 
                "Success! Resume content written to file: " + filename + 
                "\n(Check your current directory for this .tex file to view the PDF preview.)",
                "PDF Generation Complete", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ResumeBuilderGUI.this, 
                "An error occurred while writing the file: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // CUSTOM RENDERER AND EDITOR FOR TEXT WRAPPING (UNCHANGED)
    // =========================================================
    
    /**
     * Custom TableCellRenderer to wrap text using a JTextArea.
     */
    static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            setText(value == null ? "" : value.toString());
            
            // Set colors based on selection state
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }

    /**
     * Custom TableCellEditor that uses the JTextArea for multiline editing.
     */
    class TextAreaEditor extends DefaultCellEditor {
        protected JTextArea textArea;

        public TextAreaEditor() {
            super(new JCheckBox());
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            // Put the JTextArea inside a JScrollPane for long edits
            editorComponent = new JScrollPane(textArea);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            textArea.setText(value != null ? value.toString() : "");
            return editorComponent;
        }

        @Override
        public Object getCellEditorValue() {
            return textArea.getText();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ResumeBuilderGUI();
        });
    }
}