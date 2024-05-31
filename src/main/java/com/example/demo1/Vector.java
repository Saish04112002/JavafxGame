package com.example.demo1;

public class Vector {

    public double x;
    public double y;

    //default const
    public Vector() {

        this.set(0, 0);
    }

    //parameterized con
    public Vector(double x, double y) {

        this.set(x, y);
    }

    //setMethod takes doubleX dY
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }


    //Operations on vector
    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void multiply(double m) {  //* vector by constant m
        this.x *= m;
        this.y *= m;
    }

    public double getLength() {

        return Math.sqrt(this.x * this.x + this.y * y); //pythogrs theorm sqrt of a2 + b2
    }

    public void setLength(double L) {
        double currentLength = this.getLength();
        //if current length is 0 then current angle is undefined
        //assume current angle is 0(pointing to right)
        if (currentLength == 0) {
            this.set(L, 0);

        } else {
            //able to preserve current angle

            //Scale vector to have length 1 by dividing with currentLength
            this.multiply(1 / currentLength);
            //Scale vector to have Length L by * with L
            this.multiply(L);
        }

    }

    public double getAngle() {

        return Math.toDegrees(Math.atan2(this.y, this.x));
    }
//setAngle takes angle in degrees so get angle mai we take it as to degrees
    public void setAngle(double angleDegrees) {

        double L = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = L * Math.cos(angleRadians);
        this.y = L * Math.sin(angleRadians);

    }
}
