package vn.edu.ueh.bit.sol1;

import vn.edu.ueh.bit.sol1.entities.Polynomial;
import vn.edu.ueh.bit.sol1.entities.PolynomialItem;

import java.util.AbstractMap;

public class Main {
    public static void main(String[] args) {
        // Calculate polynomial value
        AbstractMap.SimpleImmutableEntry<String, Integer> entry = new AbstractMap.SimpleImmutableEntry<>("5x^3 - 6x^4 + 6x^9 + 7x + 2", 3);

        Double process = Pipeline.of(init())
                .withNextPipe(calcPolynomial())
                .process(entry);

        System.out.println("Result of polynomial: " + process);
    }

    private static Pipe<AbstractMap.SimpleImmutableEntry<String, Integer>, AbstractMap.SimpleImmutableEntry<Polynomial, Integer>> init() {
        return simpleImmutableEntry -> {
            String s = simpleImmutableEntry.getKey();

            Polynomial polynomial = new Polynomial();

            String sCoefficient = "";
            String sExp = "";
            boolean isPass = false;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ')
                    continue;
                else if (s.charAt(i) == 'x') {
                    if (sCoefficient.isEmpty())
                        sCoefficient = "1";
                    isPass = true;
                } else if (s.charAt(i) == '^') {
                    continue;
                } else {
                    if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                        polynomial.addPolynomialItem(new PolynomialItem(Float.parseFloat(sCoefficient), sExp.isEmpty() ? 1 : Integer.parseInt(sExp)));
                        sCoefficient = "";
                        sExp = "";
                        isPass = false;
                    }

                    if (isPass)
                        sExp += s.charAt(i);
                    else sCoefficient += s.charAt(i);
                }
            }

            if (!sCoefficient.isEmpty())
                polynomial.addPolynomialItem(new PolynomialItem(Float.parseFloat(sCoefficient), 0));

            return new AbstractMap.SimpleImmutableEntry<>(polynomial, simpleImmutableEntry.getValue());
        };
    }

    private static Pipe<AbstractMap.SimpleImmutableEntry<Polynomial, Integer>, Double> calcPolynomial() {
        return simpleImmutableEntry -> {
            double result = 0L;
            int x = simpleImmutableEntry.getValue();

            for (PolynomialItem item : simpleImmutableEntry.getKey().getPolynomial())
                result += item.getCoefficient() * Math.pow(x, item.getExp());

            return result;
        };
    }
}
