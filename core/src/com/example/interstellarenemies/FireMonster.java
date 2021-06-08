package com.example.interstellarenemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Batch;


public class FireMonster extends Ship {

    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0 ;
    float directionChangeFrequency = 0.75f;


    public FireMonster(float middleX, float middleY, float width, float height, float monsterSpeed, float armor,
                     float fireWidth, float fireHeight, float fireSpeed, float shootTime,
                     TextureRegion monsterTR, TextureRegion armorTR, TextureRegion fireTR) {
        super(middleX, middleY, width, height, monsterSpeed, armor, fireWidth, fireHeight, fireSpeed,
                shootTime, monsterTR, armorTR, fireTR);

        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector() {
        double bearing = GamePage.random.nextDouble()*6.283185; //0 to 2*PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void syncTime(float deltaTime) {
        super.syncTime(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }


    @Override
    public Gun[] guns(int laserCount) {
        Gun[] fire = new Gun[1];
        fire[0] = new Gun(objectShape.x + objectShape.width / 2 , objectShape.y - gunHeight,
                gunWidth, gunHeight,
                gunSpeed, gunTR);
        lastShootTime = 0;

        return fire;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(shipTR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
        if (armor > 0) {
            batch.draw(armorTR, objectShape.x, objectShape.y - objectShape.height * 0.2f, objectShape.width, objectShape.height);
        }
    }
}