package UserInterface;

import newSegments.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SuppressWarnings("ALL")
public class mainGUI extends JFrame {

    private Dimension filepath = new Dimension(280, 18);

    //Gender
    private JLabel genderLabel = new JLabel("Gender: ");
    private ButtonGroup genderGroup = new ButtonGroup();
    private JRadioButton genderMale = new JRadioButton("Male");
    private JRadioButton genderFemale = new JRadioButton("Female");
    private String gender = "M";

    //Read Depth
    private JLabel rdLabel = new JLabel("Read Depth file");
    private TextFieldStyle rdFilename = new TextFieldStyle(false, filepath);
    private String rdFilepath;
    private ButtonStyle chooseRD = new ButtonStyle("readDepth File");

    //SVdetect
    private JLabel svLabel = new JLabel("SVdetect file");
    private TextFieldStyle svFilename = new TextFieldStyle(false, filepath);
    private String svFilepath;
    private ButtonStyle chooseSV = new ButtonStyle("SVdetect File");

    //Reference Genome
    private JLabel refMatchLabel = new JLabel("Match the output to the reference genome?"),
            refFileLabel = new JLabel("Reference Genome file");
    private ButtonGroup refGroup = new ButtonGroup();
    private JRadioButton refYes = new JRadioButton("Yes");
    private JRadioButton refNo = new JRadioButton("No");
    private TextFieldStyle refFilename = new TextFieldStyle(false, filepath);
    private String refFilepath;
    private ButtonStyle chooseRef = new ButtonStyle("Genome File");
    private String match = "No";

    //Output file
    private JLabel outputLabel = new JLabel("Output file name: ");
    private TextFieldStyle outputName = new TextFieldStyle(true, new Dimension(250, 20));
    private JLabel extension = new JLabel(".csv");


    private ButtonStyle runButton = new ButtonStyle("Run");
    private TextAreaStyle console = new TextAreaStyle(true);
    private JProgressBar progressBar = new JProgressBar();

    public mainGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 2));
        optionsPanel.setPreferredSize(new Dimension(600,200));
        optionsPanel.setMaximumSize(optionsPanel.getPreferredSize());
        Font mainFont = new Font("Arial", 0, 13);
        FlowLayout flowCenter = new FlowLayout(FlowLayout.CENTER);
        FlowLayout centerVgap = new FlowLayout(FlowLayout.CENTER, 0, 20);
        Dimension chooseFile = new Dimension(100,18);

        //Gender Panel
        JPanel genderPanel = new JPanel();
        genderLabel.setFont(mainFont);
        genderFemale.setFont(mainFont);
        genderMale.setFont(mainFont);
        genderPanel.setLayout(flowCenter);
        genderPanel.add(genderLabel);
        genderGroup.add(genderMale);
        genderMale.addActionListener(new RadioButtons());
        genderMale.setSelected(true);
        genderGroup.add(genderFemale);
        genderFemale.addActionListener(new RadioButtons());
        genderPanel.add(genderMale);
        genderPanel.add(genderFemale);

        //Read Depth Panel
        JPanel rdPanel = new JPanel();
        rdPanel.setLayout(flowCenter);
        rdLabel.setFont(mainFont);
        chooseRD.addActionListener(new ChooseFile());
        chooseRD.setPreferredSize(chooseFile);
        rdPanel.add(rdLabel);
        rdPanel.add(chooseRD);
        rdPanel.add(rdFilename);

        //SVdetect Panel
        JPanel svPanel = new JPanel();
        svPanel.setLayout(flowCenter);
        svLabel.setFont(mainFont);
        chooseSV.addActionListener(new ChooseFile());
        chooseSV.setPreferredSize(chooseFile);
        svPanel.add(svLabel);
        svPanel.add(chooseSV);
        svPanel.add(svFilename);

        //Reference Genome Match Panel
        JPanel refMatchPanel = new JPanel();
        refMatchLabel.setFont(mainFont);
        refMatchPanel.setLayout(flowCenter);
        refYes.setFont(mainFont);
        refNo.setFont(mainFont);
        refGroup.add(refNo);
        refNo.setSelected(true);
        refNo.addActionListener(new RadioButtons());
        refGroup.add(refYes);
        refYes.addActionListener(new RadioButtons());
        refMatchPanel.add(refMatchLabel);
        refMatchPanel.add(refNo);
        refMatchPanel.add(refYes);



        //Reference Genome File Panel
        JPanel refFilePanel = new JPanel();
        refFileLabel.setFont(mainFont);
        refFileLabel.setForeground(Color.GRAY);
        chooseRef.setEnabled(false);
        refFilePanel.setLayout(flowCenter);
        chooseRef.addActionListener(new ChooseFile());
        chooseRef.setPreferredSize(chooseFile);
        refFilePanel.add(refFileLabel);
        refFilePanel.add(chooseRef);
        refFilePanel.add(refFilename);

        //Output Panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(flowCenter);
        outputLabel.setFont(mainFont);
        outputName.setFont(mainFont);
        extension.setFont(mainFont);
        outputPanel.add(outputLabel);
        outputPanel.add(outputName);
        outputPanel.add(extension);

        //Run Panel
        JPanel runPanel = new JPanel();
        runPanel.setLayout(centerVgap);
        runButton.addActionListener(new RunApp());
        runButton.setPreferredSize(new Dimension(70, 20));
        runPanel.add(runButton);

        //Progress Panel
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(flowCenter);
        progressPanel.setPreferredSize(new Dimension(600,300));
        progressPanel.setMaximumSize(progressPanel.getPreferredSize());
        JScrollPane scroll = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(550, 200));
        progressBar.setPreferredSize(new Dimension(550, 25));
        progressBar.setFont(mainFont);
        progressBar.setString("Not started");
        progressBar.setForeground(Color.decode("#0587a8"));
        progressBar.setStringPainted(true);
        progressPanel.add(runPanel);
        progressPanel.add(progressBar);
        progressPanel.add(scroll);

        optionsPanel.add(genderPanel);
        optionsPanel.add(rdPanel);
        optionsPanel.add(refMatchPanel);
        optionsPanel.add(svPanel);
        optionsPanel.add(refFilePanel);
        optionsPanel.add(outputPanel);
        mainPanel.add(optionsPanel);
        mainPanel.add(progressPanel);
        this.add(mainPanel);
    }

    class ChooseFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser c = new JFileChooser("../MetaCNV/Input Files");
            int rVal = c.showOpenDialog(mainGUI.this);
            if (actionEvent.getActionCommand().equals("readDepth File")) {
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    rdFilename.setText(c.getSelectedFile().getName().toString());
                    rdFilepath = c.getCurrentDirectory().toString()+ "/" + c.getSelectedFile().getName().toString();
                    rdLabel.setForeground(Color.DARK_GRAY);
                }
            } else {
                if (actionEvent.getActionCommand().equals("SVdetect File")){
                    if (rVal == JFileChooser.APPROVE_OPTION) {
                        svFilename.setText(c.getSelectedFile().getName().toString());
                        svFilepath = c.getCurrentDirectory().toString()+ "/" + c.getSelectedFile().getName().toString();
                        svLabel.setForeground(Color.DARK_GRAY);
                    }
                } else{
                    if (actionEvent.getActionCommand().equals("Genome File")){
                        if (rVal == JFileChooser.APPROVE_OPTION) {
                            refFilename.setText(c.getSelectedFile().getName().toString());
                            refFilepath = c.getCurrentDirectory().toString()+ "/" + c.getSelectedFile().getName().toString();
                            refFileLabel.setForeground(Color.DARK_GRAY);
                        }
                    }
                }
            }
        }
    }

    class RadioButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("Male")){
                gender = "M";
            } else {
                if (actionEvent.getActionCommand().equals("Female")){
                    gender = "F";
                } else {
                    if (actionEvent.getActionCommand().equals("No")) {
                        match = "No";
                        chooseRef.setEnabled(false);
                        refFileLabel.setForeground(Color.GRAY);
                    }
                    else {
                        if (actionEvent.getActionCommand().equals("Yes")) {
                            match = "Yes";
                            chooseRef.setEnabled(true);
                            refFileLabel.setForeground(Color.DARK_GRAY);
                        }
                    }
                }
            }
        }
    }

    class RunApp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Run")) {
                if (!rdFilename.getText().equals("")){
                    if (!svFilename.getText().equals("")) {
                        if (refYes.isSelected()) {
                            if (!refFilename.getText().equals("")) {
                                if (!outputName.getText().equals("")) {

                                    outputLabel.setForeground(Color.DARK_GRAY);
                                    //Opening Input Files
                                    FileInputStream readDepthInput = null, SVdetectInput = null,
                                            refGenomeInput = null;
                                    {
                                        try {
                                            readDepthInput =
                                                    new FileInputStream(rdFilepath);
                                            SVdetectInput =
                                                    new FileInputStream(svFilepath);
                                            refGenomeInput =
                                                    new FileInputStream(refFilepath);
                                        } catch (FileNotFoundException FnF) {
                                            System.out.println("File not found " + FnF.getMessage());
                                            System.exit(1);
                                        }
                                    }
                                    newSegments ns = new newSegments();
                                    console.append("Reconstructing the segmentation...\n");
                                    console.update(console.getGraphics());
                                    progressBar.setString("0%");
                                    progressBar.setValue(0);
                                    progressBar.update(progressBar.getGraphics());
                                    ns.getInputValues(readDepthInput, SVdetectInput,
                                            refGenomeInput, gender, match,
                                            outputName.getText(), progressBar, console);
                                    console.append("Finnished.\n");
                                } else {
                                    outputLabel.setForeground(Color.RED);
                                }
                            } else {
                                refFileLabel.setForeground(Color.RED);
                            }
                        } else {
                            if (!outputName.getText().equals("")){
                                outputLabel.setForeground(Color.DARK_GRAY);
                                //Opening Input Files
                                FileInputStream readDepthInput = null, SVdetectInput = null;
                                {
                                    try {
                                        readDepthInput =
                                                new FileInputStream(rdFilepath);
                                        SVdetectInput =
                                                new FileInputStream(svFilepath);
                                    } catch (FileNotFoundException FnF) {
                                        System.out.println("File not found " + FnF.getMessage());
                                        System.exit(1);
                                    }
                                }
                                newSegments ns = new newSegments();
                                console.append("Reconstructing the segmentation...\n");
                                console.update(console.getGraphics());
                                progressBar.setString("0%");
                                progressBar.setValue(0);
                                progressBar.update(progressBar.getGraphics());
                                ns.getInputValues(readDepthInput, SVdetectInput,
                                        null, gender, match,
                                        outputName.getText(), progressBar, console);
                                console.append("Finnished.\n");
                            } else {
                                outputLabel.setForeground(Color.RED);
                                if (outputName.getText().equals("")){
                                    outputLabel.setForeground(Color.RED);
                                }
                            }
                        }
                    } else {
                        svLabel.setForeground(Color.RED);
                        if (refYes.isSelected() && refFilename.getText().equals("")) {
                            refFileLabel.setForeground(Color.RED);
                        }
                        if (outputName.getText().equals("")){
                            outputLabel.setForeground(Color.RED);
                        }
                    }
                } else {
                    rdLabel.setForeground(Color.RED);
                    if (svFilename.getText().equals("")) {
                        svLabel.setForeground(Color.RED);
                    }
                    if (refYes.isSelected() && refFilename.getText().equals("")) {
                        refFileLabel.setForeground(Color.RED);
                    }
                    if (outputName.getText().equals("")){
                        outputLabel.setForeground(Color.RED);
                    }
                }
            }
        }
    }

    protected static void run(JFrame frame, int width, int height) {
        frame.setTitle("MetaCNV");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
        frame.setVisible(true);
    }
}
