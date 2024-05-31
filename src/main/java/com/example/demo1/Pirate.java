package com.example.demo1;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;

public class Pirate extends Application {
    private String keyName;

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    int score;

    boolean gameOver = false;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("PirateShip Battle");

        Image img = new Image("Images/One Pi.jpeg");
        mainStage.getIcons().add(img);
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);


        Canvas canvas = new Canvas(1000, 800);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);
        //Handle continuous inputs(as long as key is pressed)
        ArrayList<String> KeyPressedList = new ArrayList<String>();

        // Handle discrete inputs (Once per key press)
        ArrayList<String> KeyJustPressedList = new ArrayList<String>();

        mainScene.setOnKeyPressed(
                (KeyEvent event) ->
                {
                    String KeyName = event.getCode().toString();

                    // Avoid adding Duplicates to list
                    if (!KeyPressedList.contains(KeyName)) {
                        KeyPressedList.add(KeyName);
                        KeyJustPressedList.add(KeyName);
                    }

                }
        );

        // up and down keypressed values itll hold

        mainScene.setOnKeyReleased(
                (KeyEvent event) ->
                {
                    String KeyName = event.getCode().toString();

                    if (KeyPressedList.contains(KeyName)) {
                        KeyPressedList.remove(KeyName); // contains then remove , but mostly upper case works

                    }
                }
        );


        Sprite background = new Sprite("Images/Ocean DD.png");
        background.position.set(400, 300);


        Sprite Ship = new Sprite("Images/Ship (2).png");
        Ship.position.set(100, 300);
        Ship.velocity.set(50, 0);


        ArrayList<Sprite> laserList = new ArrayList<>();
        ArrayList<Sprite> DragonList = new ArrayList<>();


        int DragonCount = 7;
        for (int n = 0; n < DragonCount; n++) {
            Sprite Dragon = new Sprite("Images/Dragon 100X100.png");
            double x = 500 * Math.random() + 300; //300 -800 ke range mai
            double y = 400 * Math.random() + 100; // 100-500 Ke range mai
            Dragon.position.set(x, y);
            double angle = 360 * Math.random();
            Dragon.velocity.setLength(50);
            Dragon.velocity.setAngle(angle);
            DragonList.add(Dragon);
        }

        score = 0;
       

        AnimationTimer gameloop = new AnimationTimer() {

            public void handle(long nanotime) {

                // process user input
                if (KeyPressedList.contains("LEFT"))
                    Ship.rotation -= 3; // 3*60 180 deg rotation per sec

                if (KeyPressedList.contains("RIGHT"))
                    Ship.rotation += 3;


                if (KeyPressedList.contains("UP")) {
                    Ship.velocity.setLength(50);
                    Ship.velocity.setAngle(Ship.rotation);
                } else {
                    Ship.velocity.setLength(0);
                }

                if (KeyJustPressedList.contains("SPACE")) {
                    Sprite laser = new Sprite("Images/orb_red-opengameart-10x10.png");
                    laser.position.set(Ship.position.x, Ship.position.y);
                    laser.velocity.setLength(400);
                    laser.velocity.setAngle(Ship.rotation);
                    laserList.add(laser);
                }

                // after processing user input , clear justPressedList
                KeyJustPressedList.clear();


                //javafx mai 60ps animation , so every time handle is called 1/60th of time is passed
                Ship.update(1 / 60.0);

                for (Sprite Dragon : DragonList)
                    Dragon.update(1 / 60.0);

                //update Lasers ; destroy if 2 secs passed
                for (int n = 0; n < laserList.size(); n++) {

                    Sprite laser = laserList.get(n);
                    laser.update(1 / 60.0);
                    if (laser.elapsedTime > 1 )
                        laserList.remove(n);
                }

                //when laser overlaps dragon , remove both
                for (int laserNum = 0; laserNum < laserList.size(); laserNum++) {
                    Sprite laser = laserList.get(laserNum);
                    for (int dragNum = 0; dragNum < DragonList.size(); dragNum++) {
                        Sprite Dragon = DragonList.get(dragNum);
                        if (laser.overlaps(Dragon)) {
                            laserList.remove(laserNum);
                            DragonList.remove(dragNum);
                            score += 100;

                        }

                        if (DragonList.isEmpty() && !gameOver) {
                            gameOver = true;
                        }
                    }

                }

                //Rendering of bg,ship,laser,dragon
                background.render(context);
                Ship.render(context);
                for (Sprite laser : laserList)
                    laser.render(context);
                for (Sprite Dragon : DragonList)
                    Dragon.render(context);

                //draw Score on screennn
                context.setFill(Color.WHITE);
                context.setStroke(Color.HOTPINK);
                context.setFont(new Font("Arial Black", 48));
                context.setLineWidth(3);
                String text = "Score:" + score;
                int textX = 20;
                int textY = 700;
                context.fillText(text, textX, textY);
                context.strokeText(text, textX, textY);
                if (gameOver) {
                    displayGameOverMessage();  // Display the game over message
                }

            }

            //Displaying Message after the Game is over
            private void displayGameOverMessage() {
                String emoji = "\uD83E\uDD47";

                String message = emoji + "Congratulations! Your score is " + score + " and you played really well" + emoji;


                Text text = new Text(message);
                text.setFont(new Font("Arial Black", 27));
                double messageWidth = text.getLayoutBounds().getWidth();

                double messageX = (canvas.getWidth() - messageWidth) / 2;
                double messageY = canvas.getHeight() / 2;

                context.setFill(Color.BLACK);
                context.setStroke(Color.GREEN);
                context.setFont(new Font("Arial Black", 27));
                context.setLineWidth(3);
                context.fillText(message, messageX, messageY);
                context.strokeText(message, messageX, messageY);
            }


        };
        gameloop.start();
        mainStage.show();
    }

}

