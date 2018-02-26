package com.nickdnepr;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

public class Controller {

    private static int numberOfPoker;
    private static int numberOfNotEven;
    private static int numberOfSmallStreet;
    private static int numberOfBigStreet;
    private static int numberOfCare;
    private static Random rand;

    public static void throwDice(int numberOfThrows) {
        init();

        for (int i = 0; i < numberOfThrows; i++) {
            Model model = throwSingleDiceSet();
            checkAndLogSingleDice(model);
        }

        outputResults(numberOfThrows);
    }

    private static Model throwSingleDiceSet() {
        Vector<Integer> diceSet = new Vector<>();
        for (int i = 0; i < 5; i++) {
            int tmp = 1 + rand.nextInt(6);
            diceSet.add(tmp);
        }
        return new Model(diceSet);
    }

    //здесь проверяем модель на все возможные варианты
    private static void checkAndLogSingleDice(Model model) {
        if (checkForPoker(model)) {
//            System.out.println(model.getDiceState().toString());
            numberOfPoker++;
//если покер состоит из нечетных, то он также подходит под условие всех нечетных, учитываем это
            if (model.getDiceState().firstElement() % 2 != 0) {
                numberOfNotEven++;
//                System.out.println(model.getDiceState().toString() + " not even and poker");
                return;
            }
//            System.out.println(model.getDiceState().toString() + " poker");
            return;
        }

        if (checkForNotEven(model)) {
            numberOfNotEven++;
//            System.out.println(model.getDiceState().toString() + " not even");
        }

        if (checkForStreet(model)) {
            if (model.getDiceState().contains(1)) {
                numberOfSmallStreet++;
//                System.out.println(model.getDiceState().toString() + " small street");
                return;
            } else {
                numberOfBigStreet++;
//                System.out.println(model.getDiceState().toString() + " big street");
                return;
            }
        }

        if (checkForCare(model)) {
//            System.out.println(model.getDiceState().toString() + " care");
            numberOfCare++;
        }
    }

    private static boolean checkForPoker(Model model) {
        int first = model.getDiceState().firstElement();
        boolean poker = true;

        for (int i : model.getDiceState()) {
            if (i != first) {
                poker = false;
            }
        }
        return poker;
    }

    private static boolean checkForNotEven(Model model) {
        boolean notEven = true;
        for (int i : model.getDiceState()) {
            if (i % 2 == 0) {
                notEven = false;
            }
        }
        return notEven;
    }

    private static boolean checkForStreet(Model model) {
        boolean street = true;
        if (model.getDiceState().contains(1)) {
            for (int i = 2; i < 6; i++) {
                if (!model.getDiceState().contains(i)) {
                    street = false;
                    break;
                }
            }
        } else {
            for (int i = 2; i < 7; i++) {
                if (!model.getDiceState().contains(i)) {
                    street = false;
                    break;
                }
            }
        }

        return street;
    }

    public static boolean checkForCare(Model model) {
        boolean care = true;
        Model duplicate = new Model(new Vector<>(model.getDiceState()));
        int i = duplicate.getDiceState().firstElement();
        int numberOfSameStates = 0;
        duplicate.getDiceState().remove(new Integer(i));

        if (!duplicate.getDiceState().contains(i)) {
            i = duplicate.getDiceState().firstElement();
        }
        while (duplicate.getDiceState().contains(i)) {
            duplicate.getDiceState().remove(new Integer(i));
            numberOfSameStates++;
        }
        if (numberOfSameStates != 4) {
            care = false;
        }
        return care;
    }


    private static void init() {
        rand = new Random();
        numberOfPoker = 0;
        numberOfNotEven = 0;
        numberOfSmallStreet = 0;
        numberOfBigStreet = 0;
        numberOfCare = 0;
    }

    private static void outputResults(int numberOfThrows) {
        System.out.println("number of poker: " + numberOfPoker);
        System.out.println("number of not even: " + numberOfNotEven);
        System.out.println("number of small street: " + numberOfSmallStreet);
        System.out.println("number of big street: " + numberOfBigStreet);
        System.out.println("number of care: " + numberOfCare);
        System.out.println("Percent of poker is "+getPercents(numberOfPoker, numberOfThrows));
        System.out.println("Percent of not even is "+getPercents(numberOfNotEven, numberOfThrows));
        System.out.println("Percent of small street is "+getPercents(numberOfSmallStreet, numberOfThrows));
        System.out.println("Percent of big street is "+getPercents(numberOfBigStreet, numberOfThrows));
        System.out.println("Percent of care is "+getPercents(numberOfCare, numberOfThrows));
    }

    private static String getPercents(int numerator, int denominator) {
        StringBuilder builder = new StringBuilder("");
        double d = (double) numerator / denominator;
        BigDecimal decimal = new BigDecimal((double) numerator / denominator);
//        decimal.setScale(2, BigDecimal.ROUND_CEILING);
        builder.append(decimal.multiply(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_CEILING));
        builder.append("%");
        return builder.toString();
    }

}
