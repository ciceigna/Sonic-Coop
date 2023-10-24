package com.sonic.fangame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Pantallas.PantallaJuego;

public class SonicProject extends Game {
	public static final int V_ANCHO = 520;
	public static final int V_ALTO = 390;
	public static final float PPM = 75;
	
	public SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new PantallaJuego(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
