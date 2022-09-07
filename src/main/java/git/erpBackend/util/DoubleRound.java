package git.erpBackend.util;

import com.google.common.math.DoubleMath;

import java.math.RoundingMode;

public class DoubleRound {

    public static double round2Places(double price){
        int round = DoubleMath.roundToInt(price, RoundingMode.DOWN);
        double x = price - round;
        x *= 100;
        int i1 = DoubleMath.roundToInt(x, RoundingMode.DOWN);

        price = round + (double) i1/100;
        return price;
    }

}
