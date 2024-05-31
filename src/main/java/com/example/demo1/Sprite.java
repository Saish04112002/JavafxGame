package com.example.demo1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
//Sptie on Screen entity which can move around the screen
public class Sprite {

    public Vector position;
    public Vector velocity;
    public double rotation; // degrees
    public Rectangle boundary;
    public Image image;

    public double elapsedTime; // seconds

   //Default const , initialize
    public Sprite() {
        this.position = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Rectangle();
        elapsedTime = 0;
    }

    //parameterrized const will run default const first and then setImage method
    public Sprite(String imageFileName) {
        this();
        setImage(imageFileName);
    }

    //methd , set img then on that sets boundaries , width and height
    public void setImage(String imageFilename) {
        this.image = new Image(imageFilename);
        this.boundary.setSize(this.image.getWidth(), this.image.getHeight());
    }

// Update position qhwn sprite mooves
    //method boundary , updates the bound and then returns it
    public Rectangle getBoundary() {
        this.boundary.setPosition(this.position.x, this.position.y);
        return this.boundary;
    }

    public boolean overlaps(Sprite other) {

        return this.getBoundary().overlaps(other.getBoundary());
    }


    public void wrap(double screenWidth, double screenHeight) {

        double halfWidth = this.image.getWidth() / 2;
        double halfHeight = this.image.getHeight() / 2;
        //TODO: take into acc center of object
        if (this.position.x + halfWidth < 0)
            this.position.x = screenWidth + halfWidth;
        if (this.position.x > screenWidth + halfWidth)
            this.position.x = -halfWidth;
        if (this.position.y + halfHeight < 0)
            this.position.y = screenHeight + halfHeight;
        if (this.position.y > screenHeight + halfHeight)
            this.position.y = -halfHeight;

    }

//update methhod with terms of time
    public void update(double deltaTime) {
        // Increase elapsed time for the spriteeee
        this.elapsedTime += deltaTime;
        //update the position acc to velocityyy
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        //wrap around screen
        this.wrap(1000, 800);
    }

    // render draws sprite on the screen ,GraphicsContext draws object on canvas
    //to render = x y posi and rotate
    public void render(GraphicsContext context) {
        context.save();

        context.translate(this.position.x, this.position.y);
        context.rotate(this.rotation);
        context.translate(-this.image.getWidth() / 2, -this.image.getHeight() / 2); //rotate full origin hota hai by default so to avoid that , center align
        context.drawImage(this.image, 0, 0);
        context.restore(); //save and restor ensures ye sab ise render mai applicable hai
    }

}

