package com.example.interstellarenemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Gun {
    TextureRegion textureR;
    Rectangle objectShape;
    float gunSpeed;

    public Gun(float middleX, float bottomY, float width, float height, float gunSpeed, TextureRegion textureR) {
        this.objectShape = new Rectangle(middleX - width / 2, bottomY, width, height);
        this.textureR = textureR;
        this.gunSpeed = gunSpeed;
    }

    public void draw(Batch batch) {
        batch.draw(textureR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
    }

}