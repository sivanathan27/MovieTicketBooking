package movie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.text.SimpleDateFormat;

public class MovieBookingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Movie Ticket Booking");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Movie selection
            JLabel movieLabel = new JLabel("Select Movie:");
            movieLabel.setBounds(30, 30, 100, 25);
            frame.add(movieLabel);

            String[] movies = {"Avengers: Endgame", "Inception", "The Matrix"};
            JComboBox<String> movieComboBox = new JComboBox<>(movies);
            movieComboBox.setBounds(150, 30, 200, 25);
            frame.add(movieComboBox);

            // Date selection
            JLabel dateLabel = new JLabel("Select Date:");
            dateLabel.setBounds(30, 70, 100, 25);
            frame.add(dateLabel);

            JFormattedTextField dateField = new JFormattedTextField(new javax.swing.text.DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
            dateField.setBounds(150, 70, 200, 25);
            frame.add(dateField);

            // Time selection
            JLabel timeLabel = new JLabel("Select Time:");
            timeLabel.setBounds(30, 110, 100, 25);
            frame.add(timeLabel);

            JFormattedTextField timeField = new JFormattedTextField(new javax.swing.text.DateFormatter(new SimpleDateFormat("HH:mm")));
            timeField.setBounds(150, 110, 200, 25);
            frame.add(timeField);

            // Seats selection
            JLabel seatsLabel = new JLabel("Number of Seats:");
            seatsLabel.setBounds(30, 150, 150, 25);
            frame.add(seatsLabel);

            JTextField seatsField = new JTextField();
            seatsField.setBounds(150, 150, 200, 25);
            frame.add(seatsField);

            // Submit button
            JButton submitButton = new JButton("Book Now");
            submitButton.setBounds(150, 200, 100, 30);
            frame.add(submitButton);

            // Action listener for the submit button
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String movie = (String) movieComboBox.getSelectedItem();
                    String date = dateField.getText();
                    String time = timeField.getText();
                    String seats = seatsField.getText();

                    // Validate input
                    if (movie == null || date.isEmpty() || time.isEmpty() || seats.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generate ticket bill content
                    String ticketBill = String.format("Movie: %s\nDate: %s\nTime: %s\nSeats: %s",
                        movie, date, time, seats);

                    // Show ticket bill in a dialog
                    JTextArea textArea = new JTextArea(ticketBill);
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(frame, scrollPane, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);

                    // Print the ticket
                    printTicket(ticketBill);
                }
            });

            // Make the frame visible
            frame.setVisible(true);
        });
    }

    private static void printTicket(String ticketBill) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Set font and draw the ticket bill
                g.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g.drawString(ticketBill, 100, 100);

                return PAGE_EXISTS;
            }
        });

        boolean printAccepted = printerJob.printDialog();
        if (printAccepted) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
