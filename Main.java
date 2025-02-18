import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class GeoDistanceCalculator {
public static void main(String[] args) {
JFrame frame = new JFrame("Geo Distance Calculator");
frame.setSize(400, 300);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(null);
JLabel latLabel = new JLabel("Latitude:");
latLabel.setBounds(50, 50, 80, 25);
frame.add(latLabel);
JTextField latText = new JTextField(20);
latText.setBounds(150, 50, 165, 25);
frame.add(latText);
JLabel lonLabel = new JLabel("Longitude:");
lonLabel.setBounds(50, 100, 80, 25);
frame.add(lonLabel);
JTextField lonText = new JTextField(20);
lonText.setBounds(150, 100, 165, 25);
frame.add(lonText);
JButton searchButton = new JButton("Search Location");
searchButton.setBounds(50, 150, 150, 25);
frame.add(searchButton);
JButton calculateButton = new JButton("Calculate Distance");
calculateButton.setBounds(210, 150, 150, 25);
frame.add(calculateButton);
JButton clearButton = new JButton("Clear");
clearButton.setBounds(210, 150, 150, 25);
frame.add(clearButton);
JLabel resultLabel = new JLabel("");
resultLabel.setBounds(50, 200, 300, 25);
frame.add(resultLabel);
double initialLat = 22.56544;
double initialLon = 103.22106;
searchButton.addActionListener(new ActionListener () {
@Override
public void actionPerformed(ActionEvent e) {
String latitude = latText.getText();
String longitude = lonText.getText();
openWebSearch(latitude, longitude);
}
});
calculateButton.addActionListener(new ActionListener () {
@Override
public void actionPerformed(ActionEvent e) {
try {
double userLat = Double.parseDouble(latText.getText());
double userLon = Double.parseDouble(lonText.getText());
double distance = haversine (initialLat, initialLon, userLat, userLon);
resultLabel.setText("Distance: " + String.format("%.2f", distance) + " km");
} catch (NumberFormatException ex) {
resultLabel.setForeground(Color.RED);
resultLabel.setText("Invalid input!");
}
}
});
clearButton.addActionListener(new ActionListener () {
@Override
public void actionPerformed(ActionEvent e) {
longitudeField.setText("");
latitudeField.setText("");
}
});
frame.setVisible(true);
}
private void searchCoordinates(String longitude, String latitude) {
if (longitude.isEmpty() || latitude.isEmpty()) {
JOptionPane.showMessageDialog(this, "Please enter both longitude and latitude.",
"Error", JOptionPane.ERROR_MESSAGE);
return;
}
try {
String query = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," +
longitude;
Desktop.getDesktop(). Browse (new URI (query));
} catch (Exception e) {
JOptionPane.showMessageDialog(this, "Failed to open browser.", "Error",
JOptionPane.ERROR_MESSAGE);
}
}
public static void openWebSearch(String latitude, String longitude) {
String query = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," +
longitude;
if (Desktop.isDesktopSupported()) {
try {
Desktop.getDesktop().browse(new URI(query));
} catch (IOException | URISyntaxException e) {
e.printStackTrace();
}
}
}
public static double haversine (double lat1, double lon1, double lat2, double lon2) {
final int R = 6371;
double latDistance = Math.toRadians(lat2 - lat1);
double lonDistance = Math.toRadians(lon2 - lon1);
double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
return R * c;
}
}
