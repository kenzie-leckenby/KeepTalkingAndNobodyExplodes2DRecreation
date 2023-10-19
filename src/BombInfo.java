/**
 * Author: Kenzie Leckenby
 * Date: Apr 18, 2023
 * Description:
 */

import java.util.Random;
import java.util.Scanner;

public class BombInfo {
    public static int modules, minutes, batteries;
    public static String serialNumber;
    public BombInfo () {
        Random rand = new Random();
        serialNumber = "";

        // Chooses an amount of batteries between 0 - 4
        batteries = rand.nextInt(5);

        // Chooses 6 characters to form the serial number of the bomb
        for (int i = 0; i < 6; i++) {
            int letterOrNum = rand.nextInt(2);
            if (letterOrNum == 0 && i != 5) {
                char randChar = (char) rand.nextInt(65, 91);
                serialNumber = serialNumber.concat(randChar + "");
                continue;
            }
            serialNumber = serialNumber.concat(rand.nextInt(10) + "");

        }
    }

    // Returns true of the inputted position is odd
    public static boolean getIfOdd (int serialNumberPosition) {
        if (serialNumber.charAt(serialNumberPosition) % 2 != 0) {
            return true;
        }
        return false;
    }

    public static boolean getIfContainVowel () {
        for (int i = 0; i < serialNumber.length(); i++) {
            if ((serialNumber.charAt(i) != 'A' || serialNumber.charAt(i) != 'E' || serialNumber.charAt(i) != 'I' || serialNumber.charAt(i) != 'O' || serialNumber.charAt(i) != 'U') && ((int)serialNumber.charAt(i) < 48 || (int)serialNumber.charAt(i) > 57)) {
                return false;
            }
        }
        return true;
    }

    public int getBatteries () {
        return batteries;
    }

    public char getSerialNumberAt (int pos) {
        return serialNumber.charAt(pos);
    }
}
