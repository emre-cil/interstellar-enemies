package com.example.interstellarenemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Destroying {
    private Animation<TextureRegion> destroyAnimation;
    private float destroyTimer;
    TextureRegion[][] textureRegion2D;
    TextureRegion[] textureRegionTiles;

    private Rectangle box;


    Destroying(Texture texture, Rectangle box, float totalAnimationTime) {
        this.box = box;

        //parting image.
        textureRegion2D = TextureRegion.split(texture, 256, 256);

        //getting each destroy image.
       textureRegionTiles = new TextureRegion[9];
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                textureRegionTiles[count] = textureRegion2D[i][j];
                count++;
            }
        }

        destroyAnimation = new Animation<TextureRegion>(totalAnimationTime/8, textureRegionTiles);
        destroyTimer = 0;
    }

    public void sync(float deltaTime) {
        destroyTimer += deltaTime;
    }

    public void draw (SpriteBatch batch) {
        batch.draw(destroyAnimation.getKeyFrame(destroyTimer), box.x-7.5f, box.y-8, box.width+15, box.height+15);
    }

    public boolean isFinished() {
        return destroyAnimation.isAnimationFinished(destroyTimer);
    }

}
