package com.example.interstellarenemies;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private Camera camera;
    private Viewport viewport;

    private SpriteBatch batch;
    private TextureAtlas atlas;
    //    private Texture background;
    private TextureRegion[] backgrounds;
    private float backgroundHeight;
    //    private  int backgroundOffset;
    private TextureRegion shipTR, shieldTR, enemyShipTR, enemyShieldTR, laserTR, enemyLaserTR;
    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float bckgrndMaxScrollSpeed;

    private final int SPACE_WIDTH = 72;
    private final int SPACE_HEIGHT = 128;

    //game objects
    private Ship playerShip;
    private Ship enemyShip;


    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(SPACE_WIDTH, SPACE_HEIGHT, camera);

        atlas = new TextureAtlas("images.atlas");
        backgrounds = new TextureRegion[4];
        backgrounds[0] = atlas.findRegion("blackSpace");
        backgrounds[1] = atlas.findRegion("Starscape01");
        backgrounds[2] = atlas.findRegion("Starscape02");
        backgrounds[3] = atlas.findRegion("Starscape03");

        backgroundHeight = SPACE_HEIGHT * 2;
        bckgrndMaxScrollSpeed = (float) SPACE_HEIGHT / 4;

        //Regions
        shipTR = atlas.findRegion("ship4");
        enemyShipTR = atlas.findRegion("santelmo");
        shieldTR = atlas.findRegion("shield2");
        enemyShieldTR = atlas.findRegion("shield1");
        enemyShieldTR.flip(false, true);

        laserTR = atlas.findRegion("laserGreen03");
        enemyLaserTR = atlas.findRegion("fire");


        //set up game objects
        playerShip = new Ship(2, 3, 10, 10,
                SPACE_WIDTH / 2, SPACE_HEIGHT / 4,
                shipTR, shieldTR);
        enemyShip = new Ship(2, 1, 10, 10,
                SPACE_WIDTH / 2, SPACE_HEIGHT * 3 / 4,
                enemyShipTR, enemyShieldTR);


        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        batch.begin();

        renderBackground(delta);
        //enemy print
        enemyShip.print(batch);
        //player print
        playerShip.print(batch);

        batch.end();
    }

    private void renderBackground(float delta) {
        backgroundOffsets[0] += delta * bckgrndMaxScrollSpeed / 8;
        backgroundOffsets[1] += delta * bckgrndMaxScrollSpeed / 4;
        backgroundOffsets[2] += delta * bckgrndMaxScrollSpeed / 2;
        backgroundOffsets[3] += delta * bckgrndMaxScrollSpeed;

        for (int i = 0; i < backgroundOffsets.length; i++) {
            if (backgroundOffsets[i] > SPACE_HEIGHT)
                backgroundOffsets[i] = 0;
            batch.draw(backgrounds[i], 0, -backgroundOffsets[i],
                    SPACE_WIDTH, backgroundHeight);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }
}
