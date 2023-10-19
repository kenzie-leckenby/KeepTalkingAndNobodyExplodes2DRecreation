/**
 * Author: Kenzie Leckenby
 * Date: Apr 17, 2023
 * Description:
 */

import java.util.ArrayList;
import java.util.Random;

public class Wires {
    String[] wireTypes = {"red_wire_uncut","blue_wire_uncut","yellow_wire_uncut","white_wire_uncut","black_wire_uncut","no_wire"};
    String[] wires = new String[6];
    public Wires() {
        Random rand = new Random();
        boolean run = true;
        while (run) {
            for (int i = 0; i < wires.length; i++) {
                int randomWire = rand.nextInt(6);
                wires[i] = wireTypes[randomWire]; // Fills the array with random wires
            }
            ArrayList<String> cuttableWires = new ArrayList<>();
            for (int i = 0; i < wires.length; i++) {
                if (!wires[i].equals("no_wire")) {
                    cuttableWires.add(wires[i]);
                }
            }
            if (cuttableWires.size() >= 3) {
                run = false;
            }
        }
    }

    public boolean cutWire(int wireNumber) { // If true, it is the correct wire, if false it is the wrong wire
        ArrayList<String> cuttableWires = new ArrayList<>();

        for (int i = 0; i < wires.length; i++) {
            if (!wires[i].equals("no_wire")) {
                cuttableWires.add(wires[i]);
                if (cuttableWires.size() == wireNumber) {
                    System.out.println(wireNumber);
                    cutWireAt(i);
                }
            }
        }
        switch(cuttableWires.size()){
            case 3 -> {
                // The JButtons will change on their own regardless of these results
                if (!contains("red")) {
                    return wireNumber == 2;
                }
                else if (lastIndexOf("white") == cuttableWires.size() - 1) {
                    return wireNumber == cuttableWires.size();
                }
                else if (indexOf("blue") != lastIndexOf("blue")) {
                    return wireNumber - 1 == lastIndexOf("blue");
                }
                else if (wireNumber == 3) {
                    return true;
                }
            }
            case 4 -> {
                if (indexOf("red") != lastIndexOf("red") && BombInfo.getIfOdd(5)) {
                    return wireNumber - 1 == lastIndexOf("red");
                }
                else if (lastIndexOf("yellow") == cuttableWires.size() - 1 && !contains("red")) {
                    return wireNumber == 1;
                }
                else if (indexOf("blue") == lastIndexOf("blue")) {
                    return wireNumber == 1;
                }
                else if (indexOf("yellow") != lastIndexOf("yellow")) {
                    return wireNumber == 4;
                }
                else if (wireNumber == 2) {
                    return true;
                }
            }
            case 5 -> {
                if (lastIndexOf("black") == cuttableWires.size() - 1 && BombInfo.getIfOdd(5) && wireNumber == 5) {
                    return true;
                }
                else if (indexOf("red") == lastIndexOf("red") && indexOf("yellow") != lastIndexOf("yellow") && wireNumber == 1) {
                    return true;
                }
                else if (!contains("black") && wireNumber == 2) {
                    return true;
                }
                else if (wireNumber == 1) {
                    return true;
                }
                return false;
            }
            case 6 -> {
                if (!contains("yellow") && BombInfo.getIfOdd(5) && wireNumber == 3) {
                    return true;
                }
                else if (indexOf("yellow") == lastIndexOf("yellow") && indexOf("white") != lastIndexOf("white") && wireNumber == 4) {
                    return true;
                }
                else if (!contains("red_wire_uncut") && wireNumber == 6) {
                    return true;
                }
                else if (wireNumber == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public String wireAt (int pos) {
        return wires[pos];
    }

    public void cutWireAt (int pos) {
        wires[pos] = wires[pos].substring(0,wires[pos].lastIndexOf("_")) + "_cut";
    }

    private boolean contains(String color) {
        ArrayList<String> cuttableWires = new ArrayList<>();
        for (String wire : wires) {
            if (!wire.equals("no_wire")) {
                cuttableWires.add(wire);
            }
        }

        String uncut = color.concat("_wire_uncut");
        String cut = color.concat("_wire_cut");
        if (cuttableWires.contains(uncut)) {
            return true;
        }
        return cuttableWires.contains(cut);
    }

    private int indexOf(String color) {
        ArrayList<String> cuttableWires = new ArrayList<>();
        for (String wire : wires) {
            if (!wire.equals("no_wire")) {
                cuttableWires.add(wire);
            }
        }

        String uncut = color.concat("_wire_uncut");
        String cut = color.concat("_wire_cut");

        for (int i = 0; i < cuttableWires.size(); i++) {
            if (uncut.equals(cuttableWires.get(i))) {
                return i;
            }
            if (cut.equals(cuttableWires.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private int lastIndexOf(String color) {
        ArrayList<String> cuttableWires = new ArrayList<>();
        for (String wire : wires) {
            if (!wire.equals("no_wire")) {
                cuttableWires.add(wire);
            }
        }

        String uncut = color.concat("_wire_uncut");
        String cut = color.concat("_wire_cut");

        int lastIndex = -1;

        for (int i = 0; i < cuttableWires.size(); i++) {
            if (uncut.equals(cuttableWires.get(i))) {
                lastIndex = i;
            }
            if (cut.equals(cuttableWires.get(i))) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

}
