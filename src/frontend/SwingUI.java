package frontend;

import controllers.TicketController;
import models.DevelopmentTicket;
import models.Priority;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SwingUI extends JFrame {
    private TicketController controller;
    private DefaultTableModel tableModel;
    private JTable ticketTable;
    private TableRowSorter<DefaultTableModel> sorter;

    // Apple-style color palette (macOS/iOS)
    private final Color BG_APP = new Color(245, 245, 247); // #F5F5F7
    private final Color TEXT_PRIMARY = new Color(29, 29, 31); // #1D1D1F
    private final Color TEXT_SECONDARY = new Color(134, 134, 139); // #86868B
    private final Color COLOR_BLUE = new Color(0, 122, 255); // #007AFF
    private final Color COLOR_GREEN = new Color(52, 199, 89); // #34C759
    private final Color COLOR_RED = new Color(255, 59, 48); // #FF3B30
    private final Color COLOR_ORANGE = new Color(255, 149, 0); // #FF9500
    private final Color COLOR_PURPLE = new Color(175, 82, 222); // #AF52DE
    private final Color BORDER_COLOR = new Color(229, 229, 234); // #E5E5EA

    public SwingUI() {
        controller = new TicketController();
        setTitle("Tickets");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_APP);
        initComponents();
        refreshTable();
    }

    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(BG_APP);

        // Top Panel: Title and Search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Management Board");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Search Field
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        // Add placeholder
        searchField.putClientProperty("JTextField.placeholderText", "Search tickets...");
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center: Table
        String[] columns = {"ID", "Ticket Title", "Priority", "Assignee", "Current Phase"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        ticketTable.setRowSorter(sorter);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setRowHeight(45);
        ticketTable.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
        ticketTable.setForeground(TEXT_PRIMARY);
        ticketTable.setShowVerticalLines(false);
        ticketTable.setShowHorizontalLines(true);
        ticketTable.setGridColor(BORDER_COLOR);
        ticketTable.setIntercellSpacing(new Dimension(0, 0));
        ticketTable.setBackground(Color.WHITE);

        // Header Style (Minimalist Apple style)
        JTableHeader header = ticketTable.getTableHeader();
        header.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setForeground(TEXT_SECONDARY);
        header.setPreferredSize(new Dimension(100, 45));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        // Renderer for Phase column
        ticketTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String state = (String) value;
                setFont(new Font("Helvetica Neue", Font.BOLD, 14));
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if (!isSelected) {
                    switch (state) {
                        case "Open": c.setForeground(COLOR_BLUE); break;
                        case "In Progress": c.setForeground(COLOR_ORANGE); break;
                        case "Under Review": c.setForeground(COLOR_PURPLE); break;
                        case "Completed": c.setForeground(COLOR_GREEN); break;
                        case "Cancelled": c.setForeground(COLOR_RED); break;
                        default: c.setForeground(TEXT_PRIMARY);
                    }
                }
                return c;
            }
        });

        // Renderer for Priority column
        ticketTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String priorityStr = value != null ? value.toString() : "";
                setFont(new Font("Helvetica Neue", Font.BOLD, 14));
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected) {
                    switch (priorityStr) {
                        case "Critical": c.setForeground(COLOR_RED); break;
                        case "High": c.setForeground(COLOR_ORANGE); break;
                        case "Medium": c.setForeground(COLOR_BLUE); break;
                        case "Low": c.setForeground(COLOR_GREEN); break;
                    }
                }
                return c;
            }
        });

        // Double click listener for detailed view
        ticketTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // Double click
                    int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                    String id = (String) tableModel.getValueAt(modelRow, 0);
                    showDetailedView(id);
                }
            }
        });

        // Table container with rounded borders
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel: Buttons and Actions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Create ticket button on the left
        JButton createBtn = styleButton("Create New Ticket", COLOR_BLUE, Color.WHITE);
        createBtn.addActionListener(e -> createTicketDialog());
        bottomPanel.add(createBtn, BorderLayout.WEST);

        // Action buttons on the right
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actionsPanel.setOpaque(false);
        
        JButton startWorkBtn = styleButton("Start", Color.WHITE, COLOR_BLUE);
        startWorkBtn.addActionListener(e -> executeAction("start"));
        
        JButton reviewBtn = styleButton("Review", Color.WHITE, COLOR_PURPLE);
        reviewBtn.addActionListener(e -> executeAction("review"));
        
        JButton approveBtn = styleButton("Approve", Color.WHITE, COLOR_GREEN);
        approveBtn.addActionListener(e -> executeAction("approve"));
        
        JButton rejectBtn = styleButton("Reject", Color.WHITE, COLOR_ORANGE);
        rejectBtn.addActionListener(e -> executeAction("reject"));

        JButton cancelBtn = styleButton("Cancel", Color.WHITE, COLOR_RED);
        cancelBtn.addActionListener(e -> executeAction("cancel"));

        actionsPanel.add(startWorkBtn);
        actionsPanel.add(reviewBtn);
        actionsPanel.add(approveBtn);
        actionsPanel.add(rejectBtn);
        actionsPanel.add(cancelBtn);

        bottomPanel.add(actionsPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void showDetailedView(String id) {
        controller.getAllTickets().stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .ifPresent(ticket -> {
                JDialog dialog = new JDialog(this, "Ticket Details", true);
                dialog.setSize(500, 600);
                dialog.setLocationRelativeTo(this);
                
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel.setBackground(BG_APP);
                
                // Info Panel
                JPanel infoPanel = new JPanel(new GridLayout(6, 2, 5, 10));
                infoPanel.setOpaque(false);
                infoPanel.add(new JLabel("ID:")); infoPanel.add(new JLabel("<b>" + ticket.getId() + "</b>"));
                infoPanel.add(new JLabel("Title:")); infoPanel.add(new JLabel(ticket.getTitle()));
                infoPanel.add(new JLabel("Phase:")); infoPanel.add(new JLabel(ticket.getPhaseName()));
                infoPanel.add(new JLabel("Priority:")); infoPanel.add(new JLabel(ticket.getPriority().toString()));
                infoPanel.add(new JLabel("Assignee:")); infoPanel.add(new JLabel(ticket.getAssignee()));
                infoPanel.add(new JLabel("Description:")); infoPanel.add(new JLabel(ticket.getDescription()));
                
                panel.add(infoPanel, BorderLayout.NORTH);
                
                // Audit Logs
                JLabel logLabel = new JLabel("Audit Logs:");
                logLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
                
                JTextArea logArea = new JTextArea();
                logArea.setEditable(false);
                for (String log : ticket.getAuditLogs()) {
                    logArea.append(log + "\n");
                }
                
                JPanel logPanel = new JPanel(new BorderLayout(0, 5));
                logPanel.setOpaque(false);
                logPanel.add(logLabel, BorderLayout.NORTH);
                logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
                
                panel.add(logPanel, BorderLayout.CENTER);
                
                JButton closeBtn = styleButton("Close", Color.WHITE, TEXT_PRIMARY);
                closeBtn.addActionListener(e -> dialog.dispose());
                
                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                bottom.setOpaque(false);
                bottom.add(closeBtn);
                
                panel.add(bottom, BorderLayout.SOUTH);
                
                dialog.add(panel);
                dialog.setVisible(true);
            });
    }

    // Custom Button with hover animations and rounded corners
    private JButton styleButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Hover and click animation colors
                if (getModel().isPressed()) {
                    g2.setColor(bg.equals(Color.WHITE) ? new Color(230, 230, 235) : bg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.equals(Color.WHITE) ? new Color(245, 245, 247) : bg.brighter());
                } else {
                    g2.setColor(bg);
                }
                
                // Draw rounded rectangle background (Corner radius = 20)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Draw thin colored border for secondary buttons
                if (bg.equals(Color.WHITE)) {
                    g2.setColor(fg);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                }
                
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        return btn;
    }

    // macOS Style shake animation for errors
    private void shakeAnimation() {
        Point original = getLocation();
        Timer timer = new Timer(20, new ActionListener() {
            int counter = 0;
            int offset = 12;
            public void actionPerformed(ActionEvent e) {
                if (counter >= 10) {
                    setLocation(original);
                    ((Timer)e.getSource()).stop();
                } else {
                    setLocation(original.x + (counter % 2 == 0 ? offset : -offset), original.y);
                    offset -= 1;
                    counter++;
                }
            }
        });
        timer.start();
    }

    private void createTicketDialog() {
        JTextField idField = new JTextField(10);
        JTextField titleField = new JTextField(20);
        JTextField descField = new JTextField(20);
        JTextField assignField = new JTextField(20);
        JComboBox<Priority> priorityCombo = new JComboBox<>(Priority.values());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(BG_APP);
        panel.add(new JLabel("Ticket ID:")); panel.add(idField);
        panel.add(new JLabel("Title:")); panel.add(titleField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Assignee:")); panel.add(assignField);
        panel.add(new JLabel("Priority:")); panel.add(priorityCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "New Ticket", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String msg = controller.createTicket(
                idField.getText(), 
                titleField.getText(),
                descField.getText(),
                (Priority) priorityCombo.getSelectedItem(),
                assignField.getText()
            );
            
            if (msg.startsWith("Error")) {
                shakeAnimation();
                JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                refreshTable();
            }
        }
    }

    private void executeAction(String actionType) {
        int viewRow = ticketTable.getSelectedRow();
        if (viewRow == -1) {
            shakeAnimation();
            return;
        }

        int modelRow = ticketTable.convertRowIndexToModel(viewRow);
        String id = (String) tableModel.getValueAt(modelRow, 0);
        String resultMessage = "";

        switch (actionType) {
            case "start": resultMessage = controller.startWork(id); break;
            case "review": resultMessage = controller.sendToReview(id); break;
            case "approve": resultMessage = controller.approve(id); break;
            case "reject": resultMessage = controller.reject(id); break;
            case "cancel": resultMessage = controller.cancel(id); break;
        }

        if (resultMessage.startsWith("Error")) {
            shakeAnimation();
            final String finalMsg = resultMessage;
            Timer t = new Timer(300, e -> JOptionPane.showMessageDialog(this, finalMsg, "Transition Error", JOptionPane.ERROR_MESSAGE));
            t.setRepeats(false);
            t.start();
        } else {
            refreshTable();
            // Re-select the row based on ID since rows might have shifted due to sorting
            for (int i = 0; i < ticketTable.getRowCount(); i++) {
                int mRow = ticketTable.convertRowIndexToModel(i);
                if (tableModel.getValueAt(mRow, 0).equals(id)) {
                    ticketTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<DevelopmentTicket> tickets = controller.getAllTickets();
        for (DevelopmentTicket t : tickets) {
            tableModel.addRow(new Object[]{
                t.getId(), 
                t.getTitle(), 
                t.getPriority(),
                t.getAssignee(),
                t.getPhaseName()
            });
        }
    }
}
