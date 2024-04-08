package vn.edu.ueh.bit.sol2.entities;

public class PolynomialItem {
    private float coefficient;
    private int exp;

    public PolynomialItem(float coefficient, int exp) {
        this.coefficient = coefficient;
        this.exp = exp;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "PolynomialItem{" +
                "coefficient=" + coefficient +
                ", exp=" + exp +
                '}';
    }
}
