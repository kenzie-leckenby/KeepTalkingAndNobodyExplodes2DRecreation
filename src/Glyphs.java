import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Kenzie Leckenby
 * Date: May 08, 2023
 * Time: 8:49 PM
 * Description:
 */

public class Glyphs {
    String[][] glyphMap = {
            {"balloon", "euro", "copyright", "six", "pitchfork", "six"},
            {"at", "balloon", "pumpkin", "paragraph", "smileyface", "euro"},
            {"upsidedowny", "leftc", "cursive", "bt", "bt", "tracks" },
            {"squigglyn", "cursive", "doublek", "squidknife", "rightc", "ae"},
            {"squidknife", "hollowstar", "meltedthree", "doublek", "paragraph", "pitchfork"},
            {"hookn", "hookn", "upsidedowny", "questionmark", "dragon", "nwithhat"},
            {"leftc", "questionmark", "hollowstar", "smileyface", "filledstar", "omega"}};

    ArrayList<String> chosenGlyphs;
    ArrayList<String> clicked;

    int column;
    int startingPos;

    public Glyphs () {
        Random rand = new Random();
        column = rand.nextInt(6);

        chosenGlyphs = new ArrayList<>();
        clicked = new ArrayList<>();
        startingPos = 0;

        for (int i = 0; i < 4; i++) {
            int glyphInt = rand.nextInt(7);
            if (!chosenGlyphs.contains(glyphMap[glyphInt][column])) {
                chosenGlyphs.add(glyphMap[glyphInt][column]);
                continue;
            }
            i--;
        }
    }

    public boolean clickGlyph (String glyphName) {
        for (int i = startingPos; i < 7; i++) {
            if (!chosenGlyphs.contains(glyphMap[i][column]) && !clicked.contains(glyphMap[i][column])) {
                continue;
            }
            if (glyphMap[i][column].equals(firstGlyph(startingPos)) && !glyphMap[i][column].equals(glyphName)) {
                clicked = new ArrayList<>();
                startingPos = 0;
                return false;
            }
            startingPos = i + 1;
            clicked.add(glyphName);
            return true;
        }
        return false;
    }

    public boolean hasBeenClicked (String glyphName) {
        return clicked.contains(glyphName);
    }

    public String glyphAt (int pos) {
        return chosenGlyphs.get(pos);
    }

    public boolean getCompletion() {
        return clicked.size() == 4;
    }

    public String firstGlyph(int startingPos) {
        for (int i = startingPos; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                if (glyphMap[i][column].equals(chosenGlyphs.get(j))) {
                    return chosenGlyphs.get(j);
                }
            }
        }
        return null;
    }
}
