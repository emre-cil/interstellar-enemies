package com.example.interstellarenemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class UserShip extends Ship {
    private float health;
    TextureAtlas barAtlas;
    TextureRegion barLocation;

    public UserShip(float middleX, float middleY, float width, float height, float health, float shipSpeed,
                    float armor, float laserWidth, float laserHeight, float laserSpeed,
                    float shootTime, TextureRegion shipTR, TextureRegion armorTR, TextureRegion laserTR) {
        super(middleX, middleY, width, height, shipSpeed, armor, laserWidth, laserHeight,
                laserSpeed, shootTime, shipTR, armorTR, laserTR);
        this.health = health;
        barAtlas = new TextureAtlas("bar.atlas");

    }

    @Override
    public Gun[] guns(int laserCount) {
        Gun[] laser;
        if (laserCount == 1) {
            laser = new Gun[1];
            laser[0] = new Gun(objectShape.x + objectShape.width / 2, objectShape.y + objectShape.height,
                    gunWidth, gunHeight,
                    gunSpeed, gunTR);

        } else {
            laser = new Gun[2];
            laser[0] = new Gun(objectShape.x + objectShape.width * 0.07f, objectShape.y + objectShape.height * 0.45f,
                    gunWidth, gunHeight,
                    gunSpeed, gunTR);
            laser[1] = new Gun(objectShape.x + objectShape.width * 0.93f, objectShape.y + objectShape.height * 0.45f,
                    gunWidth, gunHeight,
                    gunSpeed, gunTR);
        }
        lastShootTime = 0;
        return laser;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }


    public void draw(Batch batch, float maxHealth) {
        int healthDraw = (int)((health/maxHealth)*100);
        if (healthDraw > 90)
            barLocation = barAtlas.findRegion("100");
        else if (healthDraw > 80)
            barLocation = barAtlas.findRegion("90");
        else if (healthDraw > 70)
            barLocation = barAtlas.findRegion("80");
        else if (healthDraw > 60)
            barLocation = barAtlas.findRegion("70");
        else if (healthDraw > 50)
            barLocation = barAtlas.findRegion("60");
        else if (healthDraw > 40)
            barLocation = barAtlas.findRegion("50");
        else if (healthDraw > 30)
            barLocation = barAtlas.findRegion("40");
        else if (healthDraw > 20)
            barLocation = barAtlas.findRegion("30");
        else if (healthDraw > 10)
            barLocation = barAtlas.findRegion("20");
        else if (healthDraw > 0)
            barLocation = barAtlas.findRegion("10");

        batch.draw(shipTR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
        batch.draw(barLocation, objectShape.x, objectShape.y - 2, 10, 0.7f);
        if (armor > 0) {
            batch.draw(armorTR, objectShape.x, objectShape.y, objectShape.width, objectShape.height);
        }
    }
}
