package model;

import model.properties.AProperty;

import java.util.Random;

/**
 * Created by Marcus Baetz on 12.08.2016.
 *
 * @author Marcus Bätz
 */
public class Dice {

    public static int roll(int number, int sides){
        int value =0;
        try{
            Random r = new Random();
            for(int i=0; i < number;i++){
                value += r.nextInt(sides)+1;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return value;
    }

    public static Result roll(AProperty property){
        Random r = new Random();
        int oneDigit = r.nextInt(10);
        int[] results = new int[3];
        for(int i=0;i<3;i++) {
            results[i] = r.nextInt(10) * 10 + oneDigit;
            if(results[i] ==0)results[i]=100;
        }
        return new Result(results,property);
    }
}
