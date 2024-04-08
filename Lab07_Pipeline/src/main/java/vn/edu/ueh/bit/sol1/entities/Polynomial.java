package vn.edu.ueh.bit.sol1.entities;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private List<PolynomialItem> polynomial;

    public Polynomial() {
        polynomial = new ArrayList<>();
    }

    public Polynomial(List<PolynomialItem> polynomial) {
        this.polynomial = polynomial;
    }

    public List<PolynomialItem> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(List<PolynomialItem> polynomial) {
        this.polynomial = polynomial;
    }

    public void addPolynomialItem(PolynomialItem polynomialItem) {
        polynomial.add(polynomialItem);
    }
}
