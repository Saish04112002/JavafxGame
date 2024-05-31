package com.example.demo1;

//collision detection
public class Rectangle {

    // (x,y) represents top-left Corner of Rectangle
    double x;
    double y;
    double width;
    double height;


    //default const
    public Rectangle() {
        this.setPosition(0, 0);
        this.setSize(1, 1);
    }

     //parameterized const
    public Rectangle(double x, double y, double w, double h) {
        this.setPosition(x, y);
        this.setSize(w, h);
    }

    //set funcs
    public void setPosition(double x, double y) {
        this.x = x;

        this.y = y;
    }

    public void setSize(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public boolean overlaps(Rectangle other) {
        // 4 cases where there in no overlap:
        // 1:this is to the left of other
        // 2:this to the right of other
        // 3:this is above other
        // 4: other is above this
        boolean noOverlap = this.x + this.width < other.x ||      //ctrl+alt+L code format
                other.x + other.width < this.x ||
                this.y + this.height < other.y ||
                other.y + other.height < this.y;
        return !noOverlap; //double negative whther the 2 rect truly do overlap

    }

}
