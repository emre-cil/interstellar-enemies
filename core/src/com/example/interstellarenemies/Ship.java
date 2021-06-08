package com.example.interstellarenemies;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    //ship characteristics
    float shipSpeed,armor;  //world units per second

    //position & dimension
    Rectangle objectShape;

    //laser information
    float gunWidth, gunHeight;
    float gunSpeed;
    float shootTime;
    float lastShootTime = 0;

    //graphics
    TextureRegion shipTR, armorTR, gunTR;

    public Ship(float middleX, float middleY, float width, float height, float speed, float armor,
                float laserWidth, float gunHeight, float gunSpeed, float shootTime,
                TextureRegion shipTR, TextureRegion armorTR, TextureRegion gunTR) {
        this.shipSpeed = speed;
        this.armor = armor;
        this.gunSpeed = gunSpeed;
        this.shootTime = shootTime;
        this.shipTR = shipTR;
        this.armorTR = armorTR;
        this.gunTR = gunTR;
        this.gunWidth = laserWidth;
        this.gunHeight = gunHeight;
        this.objectShape = new Rectangle(middleX - width / 2, middleY - height / 2, width, height);
    }

    public void syncTime(float deltaTime) {
        lastShootTime += deltaTime;
    }

    public boolean canFireLaser() {
        return (lastShootTime - shootTime >= 0);
    }

    public abstract Gun[] guns(int laserCount);

    public boolean intersects(Rectangle otherRectangle) {
        return objectShape.overlaps(otherRectangle);
    }

    public boolean checkArmor() {
        if (armor > 0) {
            armor--;
            return false;
        }
        return true;
    }

    public void changePosition(float newX, float newY) {
        objectShape.setPosition(objectShape.x + newX, objectShape.y + newY);
    }

    public void draw(Batch batch) {
        batch.draw(shipTR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
        if (armor > 0) {
            batch.draw(armorTR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
        }
    }
}
