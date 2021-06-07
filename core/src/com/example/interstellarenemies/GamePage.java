package com.example.interstellarenemies;

import com.badlogic.gdx.Game;
import java.util.Random;

public class GamePage extends Game {
	String shipName;
	float laserCount,health,armor,shipSpeed;

	public GamePage(String shipName, float laserCount, float health, float armor,float shipSpeed) {
		this.shipName = shipName;
		this.laserCount = laserCount;
		this.health = health;
		this.armor = armor;
		this.shipSpeed = shipSpeed;
	}

	GameScreen gameScreen;
	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen(shipName,laserCount,health,armor,shipSpeed);
		setScreen(gameScreen);

	}



	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
