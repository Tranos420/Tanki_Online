package tanki.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import tanki.util.SoundPlayer;

public class GarageWindow {
    private JPanel GaragePanel;
    private JLabel hullImageLabel;
    private JLabel turretImageLabel;
    private JComboBox<String> hullCombo;
    private JComboBox<String> turretCombo;
    private JButton startButton;
    private JButton exitButton;

    private final String ASSETS_PATH = "assets/";

    public GarageWindow() {
        // Populează listele
        hullCombo.setModel(new DefaultComboBoxModel<>(new String[]{"Hornet", "Viking", "Mammoth"}));
        turretCombo.setModel(new DefaultComboBoxModel<>(new String[]{"Smoky", "Firebird", "Freeze", "Railgun"}));

        // Evenimente pentru schimbarea imaginilor
        hullCombo.addActionListener(e -> updateHullImage());
        turretCombo.addActionListener(e -> updateTurretImage());

        // NU mai închidem întregul program — doar fereastra UI
        exitButton.addActionListener(e -> SwingUtilities.getWindowAncestor(GaragePanel).dispose());

        // Butonul Start doar arată selecția — fereastra principală o va prelua
        startButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Ai selectat:\n" +
                        turretCombo.getSelectedItem() + " + " +
                        hullCombo.getSelectedItem()));
        String soundPath = ASSETS_PATH + "sounds/sunet.wav";
        new Thread(() -> SoundPlayer.play(soundPath)).start();

        // Imagini default
        updateHullImage();
        updateTurretImage();
    }

    /** Actualizează imaginea pentru Hull */
    private void updateHullImage() {
        String selectedHull = hullCombo.getSelectedItem().toString().toLowerCase();
        String path = ASSETS_PATH + "hull_" + selectedHull + ".png";
        updateImage(hullImageLabel, path);

        String soundPath = ASSETS_PATH + "sounds/sunet.wav";
        new Thread(() -> SoundPlayer.play(soundPath)).start();
    }

    /** Actualizează imaginea pentru Tureta */
    private void updateTurretImage() {
        String selectedTurret = turretCombo.getSelectedItem().toString().toLowerCase();
        String path = ASSETS_PATH + "turret_" + selectedTurret + ".png";
        updateImage(turretImageLabel, path);

        // 🎵 Redare sunet specific turetei
        String soundPath = ASSETS_PATH + "sounds/" + selectedTurret + ".wav";
        new Thread(() -> SoundPlayer.play(soundPath)).start();
    }

    /** Încarcă imaginea într-un JLabel */
    private void updateImage(JLabel label, String path) {
        File file = new File(path);
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setText("");
        } else {
            label.setIcon(null);
            label.setText("Imagine lipsă");
        }
    }

    /** Lansare independentă (pentru testare directă) */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tanki Online RPG - Garaj");
        frame.setContentPane(new GarageWindow().GaragePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Deschide garajul în mod normal (non-modal, doar afișare) */
    public void showWindow() {
        JFrame frame = new JFrame("Tanki Online RPG - Garaj");
        frame.setContentPane(this.GaragePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Deschide garajul în mod blocant (modal) și returnează selecția */
    public SelectionResult getSelection() {
        final SelectionResult result = new SelectionResult();
        try {
            SwingUtilities.invokeAndWait(() -> {
                JDialog dialog = new JDialog((Frame) null, "Garage - Selectare", true);
                dialog.setContentPane(this.GaragePanel);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(900, 550);
                dialog.setLocationRelativeTo(null);

                startButton.addActionListener(e -> {
                    result.tureta = turretCombo.getSelectedItem().toString();
                    result.hull = hullCombo.getSelectedItem().toString();
                    dialog.dispose();
                    String soundPath = ASSETS_PATH + "sounds/sunet.wav";
                    new Thread(() -> SoundPlayer.play(soundPath)).start();
                });
                exitButton.addActionListener(e -> dialog.dispose());

                dialog.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /** Structură simplă pentru a transmite selecția */
    public static class SelectionResult {
        public String tureta;
        public String hull;
    }
}
