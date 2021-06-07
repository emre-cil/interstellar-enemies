package com.example.interstellarenemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class UserShip extends Ship {
    private float health;

    public UserShip(float middleX, float middleY, float width, float height, float health,float shipSpeed,
                    float armor, float laserWidth, float laserHeight, float laserSpeed,
                    float shootTime, TextureRegion shipTR, TextureRegion armorTR, TextureRegion laserTR) {
        super(middleX, middleY, width, height, shipSpeed, armor, laserWidth, laserHeight,
                laserSpeed, shootTime, shipTR, armorTR, laserTR);
        this.health =health;
    }

    @Override
    public Gun[] guns() {
        Gun[] laser = new Gun[2];
        laser[0] = new Gun(objectShape.x + objectShape.width * 0.07f, objectShape.y + objectShape.height * 0.45f,
                gunWidth, gunHeight,
                gunSpeed, gunTR);
        laser[1] = new Gun(objectShape.x + objectShape.width * 0.93f, objectShape.y + objectShape.height * 0.45f,
                gunWidth, gunHeight,
                gunSpeed, gunTR);
        lastShootTime = 0;
        return laser;
    }
    public float getHealth() {
        return health;
    }
    public void setHealth(float health) {
        this.health = health;
    }
}
