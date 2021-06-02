package com.example.interstellarenemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class UserShip extends Ship {
    private int health;

    public UserShip(float middleX, float middleY, float width, float height, int health,float shipSpeed,
                    int shield, float laserWidth, float laserHeight, float laserSpeed,
                    float shootTime, TextureRegion shipTR, TextureRegion armorTR, TextureRegion laserTR) {
        super(middleX, middleY, width, height, shipSpeed, shield, laserWidth, laserHeight,
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
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
