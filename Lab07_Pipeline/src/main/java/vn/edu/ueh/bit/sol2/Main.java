package vn.edu.ueh.bit.sol2;

import vn.edu.ueh.bit.sol2.entities.Polynomial;
import vn.edu.ueh.bit.sol2.entities.PolynomialItem;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
//        Derivative
        String apply = init()
                .andThen(derivative())
                .andThen(getResult())
                .apply("5x^3 - 6x^4 + 6x^9 + 7x + 2");

        System.out.println("Derivative result: " + apply);
    }

    private static Function<String, Polynomial> init() {
        return s -> {
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
                        polynomial.addPolynomialItem(new PolynomialItem(Float.parseFloat(sCoefficient), sExp.isEmpty() ? 0 : Integer.parseInt(sExp)));
                        sCoefficient = "";
                        sExp = "";
                        isPass = false;
                    }

                    if (isPass)
                        sExp += s.charAt(i);
                    else sCoefficient += s.charAt(i);
                }
            }

            return polynomial;
        };
    }

    private static Function<Polynomial, Polynomial> derivative() {
        return polynomial -> new Polynomial(
                polynomial.getPolynomial().stream().map(
                        polynomialItem ->
                                new PolynomialItem(
                                        polynomialItem.getCoefficient() * polynomialItem.getExp(),
                                        polynomialItem.getExp() - 1)
                ).toList());
    }

    private static Function<Polynomial, String> getResult() {
        return polynomial -> {
            String result = "";

            for (PolynomialItem polynomialItem : polynomial.getPolynomial()) {
                float coefficient = polynomialItem.getCoefficient();
                int exp = polynomialItem.getExp();

                if (coefficient == 0) continue;

                if (exp == 0) result += String.valueOf(coefficient);
                else {
                    if (coefficient > 0 && !result.isEmpty())
                        result += '+';

                    result += (String.valueOf(coefficient) + 'x');

                    if (exp != 1)
                        result += ('^' + String.valueOf(exp));
                }
            }

            return result;
        };
    }
}
