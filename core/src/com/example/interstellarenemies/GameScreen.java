package com.example.interstellarenemies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import sun.awt.windows.ThemeReader;

public class GameScreen implements Screen {


    //objects
    private UserShip userShip;
    private LinkedList<FireMonster> fireMonsters;
    private LinkedList<Gun> userLasers;
    private LinkedList<Gun> fireMonsterFires;
    private LinkedList<Destroying> destroyings;

    //libgdx screen config.
    private Camera screenCamera;
    private Viewport vPort;

    //additional images.
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;
    private TextureRegion[] backgrounds;

    private TextureRegion userShipTR, userArmorTR, monsterArmorTR, monsterTR, userLaserTR, monsterFire;

    //screen rolling.
    private float[] bgrOffset = {0, 0};
    private float bgrMaxScrollSpeed;
    private float spawnTimeDifference = 1f;
    private float spawnTimer = 0;

    //space settings
    private final float spaceWidth = 72;
    private final float spaceHeight = 128;
    private float bgrHeight = spaceHeight * 2;

    private boolean isPaused = false;


    //Screen HUD
    private int score = 0;
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    GameScreen() {

        screenCamera = new OrthographicCamera();
        vPort = new StretchViewport(spaceWidth, spaceHeight, screenCamera);

        //scrolling speed
        bgrMaxScrollSpeed = (float) (spaceHeight) / 4;

        //image importing.
        textureAtlas = new TextureAtlas("images.atlas");

        //background images
        backgrounds = new TextureRegion[2];
        backgrounds[0] = textureAtlas.findRegion("blackSpace");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");

        //object images.
        userShipTR = textureAtlas.findRegion("ship4");
        monsterTR = textureAtlas.findRegion("santelmo");
        userArmorTR = textureAtlas.findRegion("shield2");
        monsterArmorTR = textureAtlas.findRegion("shield1");
        userLaserTR = textureAtlas.findRegion("laserGreen03");
        monsterFire = textureAtlas.findRegion("fire");
        explosionTexture = new Texture("destroy.png");

        monsterArmorTR.flip(false, true);

        //set up game objects
        userShip = new UserShip(spaceWidth / 2, spaceHeight / 4,
                10, 10,
                10, 52, 3,
                0.4f, 4, 50, 0.5f,
                userShipTR, userArmorTR, userLaserTR);

        fireMonsters = new LinkedList<>();

        userLasers = new LinkedList<>();
        fireMonsterFires = new LinkedList<>();

        destroyings = new LinkedList<>();

        batch = new SpriteBatch();

        showHud();
    }

    private void showHud() {
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("vt323.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 75;
        fontParameter.borderWidth = 1.8f;
        fontParameter.borderColor = new Color().add(Color.BLACK);
        fontParameter.color = new Color().add(Color.valueOf("F92672"));
        font = fontGenerator.generateFont(fontParameter);

        //adaptive to font space
        font.getData().setScale(0.07f);
        font.setUseIntegerPositions(false);

        //other operations
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = spaceWidth * 2 / 3 - hudLeftX;
        hudCentreX = spaceWidth / 3;
        hudRow1Y = spaceHeight - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = spaceWidth / 3;
        fontGenerator.dispose();
    }

    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Armor", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Health", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", userShip.armor), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", userShip.getHealth()), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);

    }


    @Override
    public void render(float deltaTime) {
        screenCamera.update();
        if (!isPaused) {
            batch.begin();
            //scrolling background
            renderBackground(deltaTime);
            //screen touch.
            touch(deltaTime);
            userShip.syncTime(deltaTime);

            spawnMonster(deltaTime);

            ListIterator<FireMonster> monsterListIterator = fireMonsters.listIterator();
            while (monsterListIterator.hasNext()) {
                FireMonster fireMonster = monsterListIterator.next();
                enemyMovement(fireMonster, deltaTime);
                fireMonster.syncTime(deltaTime);
                fireMonster.draw(batch);
            }
            //show user ship in screen.
            userShip.draw(batch);

            //lasers
            renderGuns(deltaTime);

            //when hit the ship do calculations
            whenHitToShip();

            //destroying.
            renderDestroying(deltaTime);

            //hud rendering
            updateAndRenderHUD();

            batch.end();
        }

    }


    private void spawnMonster(float deltaTime) {
        spawnTimer += deltaTime;

        if (spawnTimer > spawnTimeDifference) {
            fireMonsters.add(new FireMonster(GamePage.random.nextFloat() * (spaceWidth - 10) + 5,
                    spaceHeight - 5,
                    10, 10,
                    48, 1,
                    4, 4, 50, 0.8f,
                    monsterTR, monsterArmorTR, monsterFire));
            spawnTimer -= spawnTimeDifference;
        }
    }

    private void touch(float deltaTime) {
        float screenTopLimit, screenBottomLimit, screenLeftEdgeLimit, screenRightEdgeLimit;
        screenLeftEdgeLimit = -userShip.objectShape.x;
        screenBottomLimit = -userShip.objectShape.y;
        screenRightEdgeLimit = spaceWidth - userShip.objectShape.x - userShip.objectShape.width;
        screenTopLimit = (float) spaceHeight / 2 - userShip.objectShape.y - userShip.objectShape.height;


        if (Gdx.input.isTouched()) {
            //touch position.
            float xTouch = Gdx.input.getX();
            float yTouch = Gdx.input.getY();


            //translate phone screen position to game space position.
            Vector2 touchPoint = new Vector2(xTouch, yTouch);
            touchPoint = vPort.unproject(touchPoint);

            //calculate the x and y differences
            Vector2 playerShipCentre = new Vector2(
                    userShip.objectShape.x + userShip.objectShape.width / 2,
                    userShip.objectShape.y + userShip.objectShape.height / 2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            // vibration distance.
            float touchVibration = 0.6f;
            if (touchDistance > touchVibration) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y + 5;

                //scale to the maximum speed of the ship
                float goX = xTouchDifference / touchDistance * userShip.shipSpeed * deltaTime;
                float goY = yTouchDifference / touchDistance * userShip.shipSpeed * deltaTime;

                goX = vectorMove(goX, screenRightEdgeLimit, screenLeftEdgeLimit);
                goY = vectorMove(goY, screenTopLimit, screenBottomLimit);

                userShip.changePosition(goX, goY);
            }
        }
    }

    private void enemyMovement(FireMonster monster, float deltaTime) {
        float screenLeftEdgeLimit, screenRightEdgeLimit, screenTopLimit, screenBottomLimit;

        screenLeftEdgeLimit = -monster.objectShape.x;
        screenBottomLimit = (float) spaceHeight / 2 - monster.objectShape.y;
        screenRightEdgeLimit = spaceWidth - monster.objectShape.x - monster.objectShape.width;
        screenTopLimit = spaceHeight - monster.objectShape.y - monster.objectShape.height;

        float goX = monster.getDirectionVector().x * monster.shipSpeed * deltaTime;
        float goY = monster.getDirectionVector().y * monster.shipSpeed * deltaTime;

        goX = vectorMove(goX, screenRightEdgeLimit, screenLeftEdgeLimit);
        goY = vectorMove(goY, screenTopLimit, screenBottomLimit);

        monster.changePosition(goX, goY);
    }

    private float vectorMove(float go, float screenLimit1, float screenLimit2) {
        float temp;
        if (go > 0) temp = Math.min(go, screenLimit1);
        else temp = Math.max(go, screenLimit2);
        return temp;
    }

    private void whenHitToShip() {
        //check if it hits the enemy.
        ListIterator<Gun> gunListIterator = userLasers.listIterator();
        while (gunListIterator.hasNext()) {
            Gun laser = gunListIterator.next();
            ListIterator<FireMonster> enemyShipListIterator = fireMonsters.listIterator();
            while (enemyShipListIterator.hasNext()) {
                FireMonster enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.objectShape)) {
                    //contact with enemy ship
                    if (enemyShip.checkArmor()) {
                        enemyShipListIterator.remove();
                        destroyings.add(new Destroying(explosionTexture, new Rectangle(enemyShip.objectShape),
                                0.7f));
                        score += 75;
                    }
                    gunListIterator.remove();
                    break;
                }
            }
        }
        //checks if bullet hit the ship
        gunListIterator = fireMonsterFires.listIterator();
        while (gunListIterator.hasNext()) {
            Gun fire = gunListIterator.next();
            if (userShip.intersects(fire.objectShape)) {
                //if destroyed.
                if (userShip.checkArmor()) {
                    userShip.setHealth(userShip.getHealth() - 10);

                }


                gunListIterator.remove();
                if (userShip.getHealth() <= 0) {
                    destroyings.add(new Destroying(explosionTexture, new Rectangle(userShip.objectShape), 1.6f));

                    pause();


                }
            }
        }
    }

    //this method renders destroy operations.
    private void renderDestroying(float deltaTime) {
        ListIterator<Destroying> destroyingListIterator = destroyings.listIterator();
        while (destroyingListIterator.hasNext()) {
            Destroying destroying = destroyingListIterator.next();
            destroying.sync(deltaTime);
            if (destroying.isFinished()) {
                destroyingListIterator.remove();
            } else {
                destroying.draw(batch);
            }
        }
    }

    //this method renders guns
    private void renderGuns(float deltaTime) {
        //add user guns
        if (userShip.canFireLaser()) {
            Gun[] lasers = userShip.guns();
            userLasers.addAll(Arrays.asList(lasers));
        }

        //add monsters and guns
        ListIterator<FireMonster> monsterListIterator = fireMonsters.listIterator();
        while (monsterListIterator.hasNext()) {
            FireMonster fireMonster = monsterListIterator.next();
            if (fireMonster.canFireLaser()) {
                Gun[] fires = fireMonster.guns();
                fireMonsterFires.addAll(Arrays.asList(fires));
            }
        }

        //show user guns on screen
        drawAndDeleteGuns(userLasers, deltaTime, 1);
        //show monster guns on screen
        drawAndDeleteGuns(fireMonsterFires, deltaTime, -1);
    }

    //this method draw guns on screen.
    private void drawAndDeleteGuns(LinkedList<Gun> guns, float deltaTime, int direction) {
        ListIterator<Gun> iterator = guns.listIterator();
        while (iterator.hasNext()) {
            Gun gun = iterator.next();
            gun.draw(batch);
            gun.objectShape.y += gun.gunSpeed * deltaTime * direction;
            //fire delete
            if (gun.objectShape.y + gun.objectShape.height < 0) {
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime) {
        //sync background according to given speeds.
        bgrOffset[0] += deltaTime * bgrMaxScrollSpeed / 8;
        bgrOffset[1] += deltaTime * bgrMaxScrollSpeed / 4;

        //move background layers.
        for (int i = 0; i < bgrOffset.length; i++) {
            if (bgrOffset[i] > spaceHeight)
                bgrOffset[i] = 0;
            //draw background.
            batch.draw(backgrounds[i], 0, -bgrOffset[i], spaceWidth, bgrHeight);
        }
    }

    @Override
    public void resize(int width, int height) {
        vPort.update(width, height, true);
        batch.setProjectionMatrix(screenCamera.combined);
    }

    @Override
    public void pause() {
        isPaused = true;
    }


    @Override
    public void dispose() {
        batch.dispose();
    }


    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
