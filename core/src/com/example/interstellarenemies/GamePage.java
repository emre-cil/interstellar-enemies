package com.example.interstellarenemies;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import java.util.Random;

public class GamePage extends Game implements ApplicationListener {
	String shipName;
	Transfer transfer;
	float laserCount,health,armor,shipSpeed;

	public GamePage(String shipName, float laserCount, float health, float armor,float shipSpeed,Transfer transfer) {
		this.shipName = shipName;
		this.laserCount = laserCount;
		this.health = health;
		this.armor = armor;
		this.shipSpeed = shipSpeed;
		this.transfer = transfer;
	}

	GameScreen gameScreen;
	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen(shipName,laserCount,health,armor,shipSpeed,transfer);
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
