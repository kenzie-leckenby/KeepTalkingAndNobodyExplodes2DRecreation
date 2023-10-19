import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Author: Kenzie Leckenby
 * Date: Apr 12, 2023
 * Description:
 */

public class Window implements ActionListener {
    static int screenScale;

    JFrame frame = new JFrame("DefuseOrDie");

    // General Info on the bomb
    BombInfo bomb;

    // Different Mini-games
    Module[] module;

    //
    int secondsLeft, modules, clockDelay;
    Timer countTime;

    int[] moduleCompletion;

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu modules, difficulty;
        JRadioButtonMenuItem rbModules;
        JMenuItem rbDifficulty;

        // Creates Menu Bar
        menuBar = new JMenuBar();

        // Menu Bar Items
        modules = new JMenu("Modules");
        difficulty = new JMenu("Bomb Setting");
        menuBar.add(modules);
        menuBar.add(difficulty);

        // < Modules Menu Buttons >
        ButtonGroup modulesGroup = new ButtonGroup();
        rbModules = new JRadioButtonMenuItem("One");
        rbModules.setActionCommand("m1");
        rbModules.setSelected(true);
        modulesGroup.add(rbModules);
        modules.add(rbModules);
        rbModules.addActionListener(this);

        rbModules = new JRadioButtonMenuItem("Two");
        rbModules.setActionCommand("m2");
        modulesGroup.add(rbModules);
        modules.add(rbModules);
        rbModules.addActionListener(this);

        rbModules = new JRadioButtonMenuItem("Three");
        rbModules.setActionCommand("m3");
        modulesGroup.add(rbModules);
        modules.add(rbModules);
        rbModules.addActionListener(this);

        rbModules = new JRadioButtonMenuItem("Four");
        rbModules.setActionCommand("m4");
        modulesGroup.add(rbModules);
        modules.add(rbModules);
        rbModules.addActionListener(this);

        rbModules = new JRadioButtonMenuItem("Five");
        rbModules.setActionCommand("m5");
        modulesGroup.add(rbModules);
        modules.add(rbModules);
        rbModules.addActionListener(this);

        // < Difficulty Menu Buttons >
        ButtonGroup difficultyGroup = new ButtonGroup();
        rbDifficulty = new JMenuItem("Generate New");
        rbDifficulty.setActionCommand("newGen");
        difficultyGroup.add(rbDifficulty);
        difficulty.add(rbDifficulty);
        rbDifficulty.addActionListener(this);

        return menuBar;
    }

    public Container currentScreen() {
        JPanel currentButtons = new JPanel();
        currentButtons.setLayout(null);
        currentButtons.setOpaque(true);

        modules = 1;

        if (bomb == null) {
            bomb = new BombInfo();
            moduleCompletion = new int[6];
            module = new Module[6];
            clockDelay = 1000;
            secondsLeft = 300;
            initializeClock();

            moduleCompletion[0] = 1;
            module[0] = new Module(0);
            wireModule(0, currentButtons);

            moduleCompletion[1] = 0;
            infoModule(currentButtons);

            moduleCompletion[2] = 1;
            module[2] = new Module(2);
            glyphModule(2, currentButtons);

            moduleCompletion[3] = 1;
            module[3] = new Module(2);
            glyphModule(3, currentButtons);

            moduleCompletion[4] = 1;
            module[4] = new Module(0);
            wireModule(4, currentButtons);

            moduleCompletion[5] = 1;
            module[5] = new Module(0);
            wireModule(5, currentButtons);


        }
        for (int i = 0; i < 6; i++) {
            if (i == 1) {
                infoModule(currentButtons);
                continue;
            }
            switch (module[i].getType()) {
                case 0 -> {
                    wireModule(i, currentButtons);
                }
                case 1 -> {

                }
                case 2 -> {
                    glyphModule(i, currentButtons);
                }
                case 3 -> {
                    simonModule(i, currentButtons);
                }
            }
        }
        return currentButtons;
    }

    // Creates and updates the wire mini-game
    public void wireModule(int module, JPanel panel) {
        int totalWires = 0;
        for(int i = 0; i < 6; i++) {
            if (!this.module[module].getWires().wireAt(i).equals("no_wire") && this.module[module].getWires().wireAt(i).contains("uncut")) {
                totalWires++;
                addTile(module, 0, i, this.module[module].getWires().wireAt(i) + "." + totalWires + "|" + module, this.module[module].getWires().wireAt(i), panel);
                continue;
            }
            else if (this.module[module].getWires().wireAt(i).contains("cut")) {
                totalWires++;
                addTile(module, 0, i, this.module[module].getWires().wireAt(i), panel);
                continue;
            }
            addTile(module, 0, i, this.module[module].getWires().wireAt(i), panel);
        }

        // Changes the status light
        switch (moduleCompletion[module]) {
            case 0 -> addTile(module, 5, 0, "status_light_red", panel);
            case 1 -> addTile(module, 5, 0, "status_light_off", panel);
            case 2 -> addTile(module, 5, 0, "status_light_green", panel);
        }

        addTile(module, 5, 1, "empty_strip", panel);
    }

    // Creates and updates the glyph mini-game
    public void glyphModule(int module, JPanel panel) {
        for (int i = 0; i < 4; i++) {
            if (this.module[module].getGlyphs().hasBeenClicked(this.module[module].getGlyphs().glyphAt(i))) {
                addTile(module, (i * 3) % 6, ((i/2)*3) + 1, this.module[module].getGlyphs().glyphAt(i) + "_green", panel);
                continue;
            }
            addTile(module, (i * 3) % 6, ((i/2)*3) + 1, this.module[module].getGlyphs().glyphAt(i) + "." + "glyph" + "|" + module,this.module[module].getGlyphs().glyphAt(i) + "_off", panel);
        }

        // Changes the status light
        switch (moduleCompletion[module]) {
            case 0 -> addTile(module, 5, 0, "status_light_red", panel);
            case 1 -> addTile(module, 5, 0, "status_light_off", panel);
            case 2 -> addTile(module, 5, 0, "status_light_green", panel);
        }

        addTile(module, 5, 1, "empty_strip", panel);
        addTile(module, 0, 0, "empty_5x1", panel);
        addTile(module, 2, 1, "empty_1x2", panel);
        addTile(module, 0, 3, "empty_2x1", panel);
        addTile(module, 2, 3, "empty_tile", panel);
        addTile(module, 3, 3, "empty_2x1", panel);
        addTile(module, 2, 4, "empty_1x2", panel);
    }

    // Creates and updates the simonsays mini-game
    public void simonModule(int module, JPanel panel) {

    }
    // Bomb info module that contains the timer, batteries,
    public void infoModule(JPanel panel) {
        int minutes = secondsLeft / 60;
        int secondsFirst = (secondsLeft % 60) / 10;
        int secondsSecond = (secondsLeft % 60) % 10;

        switch (minutes) {
            case 0 -> addTile(1, 1, 0, "digital_zero", panel);
            case 1 -> addTile(1, 1, 0, "digital_one", panel);
            case 2 -> addTile(1, 1, 0, "digital_two", panel);
            case 3 -> addTile(1, 1, 0, "digital_three", panel);
            case 4 -> addTile(1, 1, 0, "digital_four", panel);
            case 5 -> addTile(1, 1, 0, "digital_five", panel);
            case 6 -> addTile(1, 1, 0, "digital_six", panel);
            case 7 -> addTile(1, 1, 0, "digital_seven", panel);
            case 8 -> addTile(1, 1, 0, "digital_eight", panel);
            case 9 -> addTile(1, 1, 0, "digital_nine", panel);
        }
        switch (secondsFirst) {
            case 0 -> addTile(1, 3, 0, "digital_zero", panel);
            case 1 -> addTile(1, 3, 0, "digital_one", panel);
            case 2 -> addTile(1, 3, 0, "digital_two", panel);
            case 3 -> addTile(1, 3, 0, "digital_three", panel);
            case 4 -> addTile(1, 3, 0, "digital_four", panel);
            case 5 -> addTile(1, 3, 0, "digital_five", panel);
        }
        switch (secondsSecond) {
            case 0 -> addTile(1, 4, 0, "digital_zero", panel);
            case 1 -> addTile(1, 4, 0, "digital_one", panel);
            case 2 -> addTile(1, 4, 0, "digital_two", panel);
            case 3 -> addTile(1, 4, 0, "digital_three", panel);
            case 4 -> addTile(1, 4, 0, "digital_four", panel);
            case 5 -> addTile(1, 4, 0, "digital_five", panel);
            case 6 -> addTile(1, 4, 0, "digital_six", panel);
            case 7 -> addTile(1, 4, 0, "digital_seven", panel);
            case 8 -> addTile(1, 4, 0, "digital_eight", panel);
            case 9 -> addTile(1, 4, 0, "digital_nine", panel);
        }

        if (moduleCompletion[1] == 1) {
            addTile(1, 2, 2, "strike_light_on", panel);
            addTile(1, 3, 2, "strike_light_off", panel);
        }
        else if (moduleCompletion[1] >= 2) {
            addTile(1, 2, 2, "strike_light_on", panel);
            addTile(1, 3, 2, "strike_light_on", panel);
        }
        else {
            addTile(1, 2, 2, "strike_light_off", panel);
            addTile(1, 3, 2, "strike_light_off", panel);
        }

        for (int i = 0; i < 4; i++) {
            if (i < bomb.getBatteries()) {
                addTile(1, i + 1, 4, "battery_true", panel);
                continue;
            }
            addTile(1, i + 1, 4, "battery_false", panel);
        }

        for (int i = 0; i < 6; i++) {
            addSerialNumberValue(bomb.getSerialNumberAt(i) + "", i, panel);
        }
        addTile(1, 2, 0, "digital_semi-colon_on", panel);
        addTile(1, 1, 3, "serial_number_plate", panel);
        addTile(1, 0, 0, "empty_tile", panel);
        addTile(1, 5, 0, "empty_tile", panel);
        addTile(1, 1, 2, "empty_tile", panel);
        addTile(1, 4, 2, "empty_tile", panel);
        addTile(1, 0, 1, "empty_strip", panel);
        addTile(1, 5, 1, "empty_strip", panel);
    }

    private boolean isComplete () {
        for (int i = 0; i < 6; i++) {
            if (moduleCompletion[i] != 2 && i != 1) {
                return false;
            }
        }
        return true;
    }

    private void initializeClock() {
        countTime = new Timer (clockDelay, clock);
        countTime.setInitialDelay(0);
        countTime.start();
    }

    // Creates and sets the position for JLabels
    public JLabel tileImage (ImageIcon image, int module, int posX, int posY) {
        JLabel tile = new JLabel(image);
        tile.setBounds(((module % 3) * (96 * screenScale)) + (posX * (16 * screenScale)), ((module / 3) * (96 * screenScale)) + (posY * (16 * screenScale)), image.getIconWidth(), image.getIconHeight());
        return tile;
    }

    // Creates and sets the position for JButtons
    public JButton tileButton (ImageIcon image,int module, int posX, int posY, String actionCMD, String path) {
        JButton button = new JButton(image);
        button.setBounds(((module % 3) * (96 * screenScale)) + (posX * (16 * screenScale)), ((module / 3) * (96 * screenScale)) + (posY * (16 * screenScale)), image.getIconWidth(), image.getIconHeight());
        button.setBorderPainted(false);

        button.setActionCommand(actionCMD);
        button.addActionListener(this);
        button.setRolloverEnabled(true);
        button.setRolloverIcon(getImageIcon(path + "_ro"));
        return button;
    }

    // Adds a JButton to the JPanel of the current frame
    public void addTile (int module, int posX, int posY, String ActionCMD, String image, JPanel panel) {
        ImageIcon icon = getImageIcon(image);
        panel.add(tileButton(icon, module, posX, posY, ActionCMD, image));
    }

    // Adds a JLabel to the JPanel of the current frame
    public void addTile (int module, int posX, int posY, String image, JPanel panel) {
        ImageIcon icon = getImageIcon(image);
        panel.add(tileImage(icon, module, posX, posY));
    }

    public void addSerialNumberValue (String image, int posX, JPanel panel) {
        ImageIcon icon = getImageIcon(image);
        panel.add(serialNumberLabel(icon, posX));
    }

    public JLabel serialNumberLabel (ImageIcon image, int posX) {
        JLabel serialNumber = new JLabel(image);
        serialNumber.setBounds((129 * screenScale) + (posX * (screenScale * 8)), 49 * screenScale, image.getIconWidth(), image.getIconHeight());
        return serialNumber;
    }

    public void winPop() {
        Object[] options = {"Play Again", "Save & Exit"};
        int optionChose = JOptionPane.showOptionDialog(frame,
                "You Survived !",
                "Win Popup",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                getImageIcon("status_light_green"),
                options,
                options[0]);
        if (optionChose == JOptionPane.YES_OPTION) {
            bomb = null;
            frame.setContentPane(currentScreen());
            frame.revalidate();
        }
        else if (optionChose == JOptionPane.NO_OPTION) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void losePop() {
        Object[] options = {"Play Again", "Save & Exit"};
        int optionChose = JOptionPane.showOptionDialog(frame,
                "You Died !",
                "Loss Popup",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                getImageIcon("status_light_red"),
                options,
                options[0]);
        if (optionChose == JOptionPane.YES_OPTION) {
            bomb = null;
            frame.setContentPane(currentScreen());
            frame.revalidate();
        }
        else if (optionChose == JOptionPane.NO_OPTION) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }


    // Creates a scaled image icon
    protected static ImageIcon getImageIcon(String path) {
        path = "/Tileset/" + path + ".png";
        java.net.URL imgURL = Window.class.getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            return new ImageIcon(getScaledImage(icon.getImage(), icon.getIconWidth() * screenScale, icon.getIconHeight() * screenScale));
        } else {
            return null;
        }
    }

    private static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    private static void createAndShowGUI() {
        // Creates and sets up the content pane
        Window window = new Window();
        window.frame.setJMenuBar(window.createMenuBar());

        // Checks the screen size
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) size.getWidth();
        screenWidth -= 300;
        window.screenScale = screenWidth/288;
        window.frame.setContentPane(window.currentScreen());

        // Display the window
        window.frame.setSize((288 * window.screenScale) + 15, (192 * window.screenScale) + 60);
        window.frame.setVisible(true);

        window.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Run GUI codes in Event-Dispatching thread for thread-safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Generate New
        if (e.getActionCommand().equals("newGen")) {
            countTime.stop();
            bomb = null;
            frame.setContentPane(currentScreen());
            frame.revalidate();
        }

        // Wires Module
        else if (e.getActionCommand().substring(0, e.getActionCommand().indexOf(".")).contains("wire")) {
            if (module[Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1))].getWires().cutWire(Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf(".") + 1, e.getActionCommand().indexOf("|"))))) {
                moduleCompletion[(Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1)))] = 2;
            }
            else {
                moduleCompletion[Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1))] = 0;
                moduleCompletion[1]++;
                clockDelay -= moduleCompletion[1] * 250;
            }
            frame.setContentPane(currentScreen());
            frame.revalidate();
            if (isComplete()) {
                countTime.stop();
                winPop();
            }
            if (moduleCompletion[1] == 3) {
                countTime.stop();
                losePop();
            }
        }

        // Glyphs Module
        else if (e.getActionCommand().substring(e.getActionCommand().indexOf(".") + 1, e.getActionCommand().indexOf("|")).contains("glyph")) {
            if (module[Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1))].getGlyphs().clickGlyph(e.getActionCommand().substring(0, e.getActionCommand().indexOf(".")))) {
                if (module[Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1))].getGlyphs().getCompletion()) {
                    moduleCompletion[(Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1)))] = 2;
                }
                if (isComplete()) {
                    countTime.stop();
                    winPop();
                }
            }
            else {
                moduleCompletion[Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf("|") + 1))] = 0;
                moduleCompletion[1]++;
                clockDelay -= moduleCompletion[1] * 250;
                
            }
            frame.setContentPane(currentScreen());
            frame.revalidate();
            if (isComplete()) {
                countTime.stop();
                winPop();
            }
            if (moduleCompletion[1] == 3) {
                countTime.stop();
                losePop();
            }
        }
    }

    ActionListener clock = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            secondsLeft--;
            if (secondsLeft <= 0) {
                countTime.stop();
                losePop();
            }
            frame.setContentPane(currentScreen());
            frame.revalidate();

        }
    };
}
