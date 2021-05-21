package com.example.interstellarenemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {
    float speed;
    int shield;
    float xPos,yPos;
    float width, height;

    TextureRegion shipTR,shieldTR;

    public Ship(float speed, int shield,float width, float height, float xPos, float yPos, TextureRegion shipTR, TextureRegion shieldTR) {
        this.speed = speed;
        this.shield = shield;
        this.xPos = xPos - width/2;
        this.yPos = yPos - height/2;
        this.width = width;
        this.height = height;
        this.shipTR = shipTR;
        this.shieldTR = shieldTR;
    }

    public void print(Batch batch){
        batch.draw(shipTR,xPos,yPos,width,height);
        if (shield > 0){
            batch.draw(shieldTR,xPos,yPos,width,height);
        }

    }

}
