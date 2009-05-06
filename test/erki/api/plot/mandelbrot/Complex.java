package erki.api.plot.mandelbrot;

public class Complex {
    
    private double re, im;
    
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    
    public double re() {
        return re;
    }
    
    public double im() {
        return im;
    }
    
    public double abs() {
        return Complex.abs(this);
    }
    
    public static Complex add(Complex c1, Complex c2) {
        double re = c1.re() + c2.re();
        double im = c1.im() + c2.im();
        return new Complex(re, im);
    }
    
    public static Complex sub(Complex c1, Complex c2) {
        double re = c1.re() - c2.re();
        double im = c1.im() - c2.im();
        return new Complex(re, im);
    }
    
    public static Complex mult(Complex c1, Complex c2) {
        double re = c1.re() * c2.re() - c1.im() * c2.im();
        double im = c1.re() * c2.im() + c1.im() * c2.re();
        return new Complex(re, im);
    }
    
    public static Complex div(Complex c1, Complex c2) {
        double denom = (c2.re() * c2.re() + c2.im() * c2.im());
        double re = (c1.re() * c2.re() + c1.im() * c2.im()) / denom;
        double im = (c1.im() * c2.re() - c1.re() * c2.im()) / denom;
        return new Complex(re, im);
    }
    
    public static double abs(Complex c) {
        return Math.sqrt(c.re() * c.re() + c.im() * c.im());
    }
}
