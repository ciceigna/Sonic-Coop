package com.sonic.fangame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Pantallas.PantallaTitulo;

public class SonicProject extends Game {
	public static final int V_ANCHO = 520;
	public static final int V_ALTO = 390;
	public static final float PPM = 75;
	
	//Box2D Collision Bits
	public static final short BIT_VACIO = 0;
	
	public SpriteBatch batch;
	
	public static AssetManager admin;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		admin = new AssetManager();
		admin.load("audio/musica/a.wav", Music.class);
		admin.load("audio/sonidos/s_anillo.wav", Sound.class);
		admin.load("audio/sonidos/s_muerte.wav", Sound.class);
		admin.load("audio/sonidos/s_salto.wav", Sound.class);
		admin.finishLoading();
		
		setScreen(new PantallaTitulo(this));
	}
	@Override
	public void dispose() {
		super.dispose();
		admin.dispose();
		batch.dispose();
	}
	
	@Override
	public void render() {
		super.render();
		admin.update();
	}
}
