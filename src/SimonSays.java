import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Kenzie Leckenby
 * Date: May 08, 2023
 * Time: 8:49 PM
 * Description:
 */

public class SimonSays {

    ArrayList<String> colorOrder;
    String currentHighest;
    int rounds;

    public SimonSays () {
        Random rand = new Random();
        ArrayList<String> colorOrder = new ArrayList<>();
        rounds = rand.nextInt(3,6);
        addRandColor();
    }

    public boolean clickColor (String color, int strikes) {
        if (BombInfo.getIfContainVowel()) {
            switch (strikes) {
                case 0 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
                case 1 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
                case 2 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
            }
        }
        else {
            switch (strikes) {
                case 0 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
                case 1 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
                case 2 -> {
                    if (currentHighest.equals("simonsays_red") && color.equals("simonsays_yellow")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_blue") && color.equals("simonsays_green")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_green") && color.equals("simonsays_blue")) {
                        addRandColor();
                        return true;
                    }
                    if (currentHighest.equals("simonsays_yellow") && color.equals("simonsays_red")) {
                        addRandColor();
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private void addRandColor() {
        Random rand = new Random();
        switch(rand.nextInt(4)) {
            case 0 -> colorOrder.add("simonsays_red");
            case 1 -> colorOrder.add("simonsays_blue");
            case 2 -> colorOrder.add("simonsays_green");
            case 3 -> colorOrder.add("simonsays_yellow");
        }
        currentHighest = colorOrder.get(colorOrder.size() - 1);
    }

    public int getSize () {
        return colorOrder.size();
    }

    public String getColor (int pos) {
        return colorOrder.get(pos);
    }


}
