package com.example.interstellarenemies;

import com.badlogic.gdx.Game;
import java.util.Random;

public class GamePage extends Game {

	GameScreen gameScreen;
	public static Random random = new Random();

	@Override
	public void create() {
		gameScreen = new GameScreen();
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
