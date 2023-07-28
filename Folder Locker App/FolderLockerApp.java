import javax.swing.*;//to use swing properties
import java.awt.*;//to use awt properties
import java.awt.event.ActionEvent;//to use actionevent properties
import java.awt.event.ActionListener;//added listener
import java.io.File;//for input and output
import java.io.IOException;

public class FolderLockerApp extends JFrame {
    private JTextField folderPathField;
    private JButton browseButton;
    private JButton lockButton;
    private JButton unlockButton;

    public FolderLockerApp() { //constructor
        initializeUI();
        addEventListeners();
    }

    private void initializeUI() {
        setTitle("Folder Locker Application");
        setSize(430, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        folderPathField = new JTextField();
        add(folderPathField);

        browseButton = new JButton("Browse");
        add(browseButton);

        lockButton = new JButton("Lock");
        add(lockButton);

        unlockButton = new JButton("Unlock");
        add(unlockButton);
    }

    private void addEventListeners() {
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //to choose file

                int result = fileChooser.showOpenDialog(FolderLockerApp.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    folderPathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = folderPathField.getText().trim();
                if (!folderPath.isEmpty()) {
                    try {
                        File folder = new File(folderPath);
                        if (folder.exists() && folder.isDirectory()) {
                            // Perform folder locking logic here
                            // For this example, we'll just create a .lock file in the folder
                            File lockFile = new File(folder, ".lock");
                            lockFile.createNewFile();
                            JOptionPane.showMessageDialog(FolderLockerApp.this, "Folder locked successfully.");
                        } else {
                            JOptionPane.showMessageDialog(FolderLockerApp.this, "Invalid folder path or the folder does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(FolderLockerApp.this, "An error occurred while locking the folder.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(FolderLockerApp.this, "Please select a folder to lock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        unlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = folderPathField.getText().trim();
                if (!folderPath.isEmpty()) {
                    try {
                        File folder = new File(folderPath);
                        File lockFile = new File(folder, ".lock");
                        if (lockFile.exists()) {
                            lockFile.delete();
                            JOptionPane.showMessageDialog(FolderLockerApp.this, "Folder unlocked successfully.");
                        } else {
                            JOptionPane.showMessageDialog(FolderLockerApp.this, "The folder is already unlocked or does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(FolderLockerApp.this, "An error occurred while unlocking the folder.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(FolderLockerApp.this, "Please select a folder to unlock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FolderLockerApp().setVisible(true);
            }
        });
    }
}
