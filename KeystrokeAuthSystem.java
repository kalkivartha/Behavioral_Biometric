import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class KeystrokeAuthSystem {

    private static final String PROFILE_PATH = "user_profile.txt";
    private static ArrayList<Long> dwellTimes = new ArrayList<>();
    private static ArrayList<Long> flightTimes = new ArrayList<>();
    private static long lastReleaseTime = 0;
    private static long pressTime = 0;

    public static void main(String[] args) {
        showHomePage();
    }

    private static void showHomePage() {
        JFrame frame = new JFrame("Behavioral Biometrics Login System");
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegisterPage();
        });

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        JPanel panel = new JPanel();
        panel.add(registerButton);
        panel.add(loginButton);

        frame.add(panel);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void showRegisterPage() {
        JFrame frame = new JFrame("Register Typing Pattern");
        JLabel label = new JLabel("Type your password: ");
        JPasswordField passwordField = new JPasswordField(20);
        JButton saveButton = new JButton("Save Pattern");

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(passwordField);
        panel.add(saveButton);

        frame.add(panel);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        resetCapture();

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pressTime = System.currentTimeMillis();
                if (lastReleaseTime != 0) {
                    flightTimes.add(pressTime - lastReleaseTime);
                }
            }

            public void keyReleased(KeyEvent e) {
                long releaseTime = System.currentTimeMillis();
                dwellTimes.add(releaseTime - pressTime);
                lastReleaseTime = releaseTime;
            }
        });

        saveButton.addActionListener(e -> {
            saveTypingProfile();
            JOptionPane.showMessageDialog(frame, "Profile saved successfully!");
            frame.dispose();
            showHomePage();
        });
    }

    private static void showLoginPage() {
        JFrame frame = new JFrame("Login Typing Pattern");
        JLabel label = new JLabel("Type your password: ");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        resetCapture();

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pressTime = System.currentTimeMillis();
                if (lastReleaseTime != 0) {
                    flightTimes.add(pressTime - lastReleaseTime);
                }
            }

            public void keyReleased(KeyEvent e) {
                long releaseTime = System.currentTimeMillis();
                dwellTimes.add(releaseTime - pressTime);
                lastReleaseTime = releaseTime;
            }
        });

        loginButton.addActionListener(e -> {
            boolean match = authenticateTypingPattern();
            if (match) {
                JOptionPane.showMessageDialog(frame, "Login successful! Typing pattern matched.");
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed! Typing pattern did not match.");
            }
            frame.dispose();
            showHomePage();
        });
    }

    private static void resetCapture() {
        dwellTimes.clear();
        flightTimes.clear();
        lastReleaseTime = 0;
    }

    private static void saveTypingProfile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PROFILE_PATH));
            for (Long dwell : dwellTimes) {
                writer.write(dwell + " ");
            }
            writer.newLine();
            for (Long flight : flightTimes) {
                writer.write(flight + " ");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean authenticateTypingPattern() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PROFILE_PATH));
            String dwellLine = reader.readLine();
            String flightLine = reader.readLine();
            reader.close();

            List<Long> storedDwell = new ArrayList<>();
            List<Long> storedFlight = new ArrayList<>();

            if (dwellLine != null) {
                for (String num : dwellLine.trim().split(" ")) {
                    storedDwell.add(Long.parseLong(num));
                }
            }
            if (flightLine != null) {
                for (String num : flightLine.trim().split(" ")) {
                    storedFlight.add(Long.parseLong(num));
                }
            }

            double dwellScore = calculateDistance(dwellTimes, storedDwell);
            double flightScore = calculateDistance(flightTimes, storedFlight);

            double threshold = 75.0; // You can tune this value

            System.out.println("Dwell Score: " + dwellScore);
            System.out.println("Flight Score: " + flightScore);

            return (dwellScore < threshold && flightScore < threshold);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static double calculateDistance(List<Long> actual, List<Long> stored) {
        if (actual.size() != stored.size()) return Double.MAX_VALUE;
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            sum += Math.pow(actual.get(i) - stored.get(i), 2);
        }
        return Math.sqrt(sum);
    }
} 
