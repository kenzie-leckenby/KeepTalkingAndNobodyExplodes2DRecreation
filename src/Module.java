/**
 * Author: Kenzie Leckenby
 * Date: May 08, 2023
 * Time: 8:44 PM
 * Description:
 */

public class Module {
    Wires wires;
    Button button;
    Glyphs glyphs;
    SimonSays simonSays;
    public Module(int type) {
        switch (type) {
            case 0 -> wires = new Wires();
            case 1 -> button = new Button();
            case 2 -> glyphs = new Glyphs();
            case 3 -> simonSays = new SimonSays();
        }
    }

    public Wires getWires() {
        return wires;
    }

    public Button getButton() {
        return button;
    }

    public Glyphs getGlyphs() {
        return glyphs;
    }

    public SimonSays getSimonSays() {
        return simonSays;
    }

    public int getType() {
        if (wires != null) {
            return 0;
        }
        if (button != null) {
            return 1;
        }
        if (glyphs != null) {
            return 2;
        }
        if (simonSays != null) {
            return 3;
        }
        return -1;
    }
}
