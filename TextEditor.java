import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class TextEditor implements ActionListener {
    private JFrame frame;
    private JMenuBar menuBar;
    private JTextArea textArea;
    private JMenu file, edit;
    private JMenuItem newFile, openFile, saveFile, cut, copy, paste, selectAll, close;

    TextEditor() {
        frame = new JFrame();
        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        newFile = new JMenuItem("New");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        close = new JMenuItem("Close");

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(close);

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);

        menuBar.add(file);
        menuBar.add(edit);

        frame.setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);

        frame.setSize(500, 500);
        frame.setTitle("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == newFile) {
            textArea.setText("");
        } else if (actionEvent.getSource() == openFile) {
            openFile();
        } else if (actionEvent.getSource() == saveFile) {
            saveFile();
        } else if (actionEvent.getSource() == cut) {
            textArea.cut();
        } else if (actionEvent.getSource() == copy) {
            textArea.copy();
        } else if (actionEvent.getSource() == paste) {
            textArea.paste();
        } else if (actionEvent.getSource() == selectAll) {
            textArea.selectAll();
        } else if (actionEvent.getSource() == close) {
            System.exit(0);
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileReader fileReader = new FileReader(file);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
