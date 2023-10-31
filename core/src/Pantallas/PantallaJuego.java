package Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.fangame.SonicProject;
import Escenas.Hud;
import Herramientas.B2CreaMundos;
import Herramientas.WorldContactListener;
import Sprites.Sonic;

public class PantallaJuego implements Screen {
	//base
	private SonicProject juego;
	private OrthographicCamera camJuego;
	private Viewport vistaJuego; 
	private TextureAtlas atlas;
	private Sonic jugador;
	//hud
	private Hud hud;
	
	//mapa tiled
	private TmxMapLoader cargaMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderizar;
	
	//box2d obj. y colisiones
	private World mundo;
	private Box2DDebugRenderer b2dr;
	
	private float tiempoEspera = 0.85f;
	private boolean cambioPantalla = false;
	
	private Music musica;
	
	public PantallaJuego(SonicProject juego) {
        this.juego = juego;
		atlas = new TextureAtlas("texturaSonic.atlas");
		
		camJuego = new OrthographicCamera();
		vistaJuego = new FitViewport(SonicProject.V_ANCHO / SonicProject.PPM,SonicProject.V_ALTO / SonicProject.PPM,camJuego);
		
		 
		//hud
		hud = new Hud(juego.batch);
		
		//tiled
		cargaMapa = new TmxMapLoader();
		mapa = cargaMapa.load("prueba.tmx");
		renderizar = new OrthogonalTiledMapRenderer(mapa, 1 / SonicProject.PPM);
		
		camJuego.position.set(vistaJuego.getWorldWidth() / 2, vistaJuego.getWorldHeight() / 2, 0);
		
		//box2d obj. y colisiones
		mundo = new World(new Vector2(0, -9), true);
		b2dr = new Box2DDebugRenderer();
		
		new B2CreaMundos(mundo, mapa);
		
		jugador = new Sonic(mundo, this);
		
		mundo.setContactListener(new WorldContactListener());
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	@Override
	public void show() {
	}
	
	public void handleInput(float dt) {
		if(jugador.estadoActual != Sonic.Estado.MUERTO) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(0, 6f), jugador.b2cuerpo.getWorldCenter(), true);
				SonicProject.admin.get("audio/sonidos/s_salto.wav", Sound.class).play();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				jugador.golpe();
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(0.1f, 0), jugador.b2cuerpo.getWorldCenter(), true);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				jugador.b2cuerpo.applyLinearImpulse(new Vector2(-0.1f, 0), jugador.b2cuerpo.getWorldCenter(), true);
			}
		}
	}
	
	public void update (float dt) {
		handleInput(dt);
		
		mundo.step(1/60f, 6, 2);
		
		jugador.update(dt);
		
	    camJuego.position.set(jugador.b2cuerpo.getPosition().x, jugador.b2cuerpo.getPosition().y, 0);
		
		camJuego.update();
		renderizar.setView(camJuego);
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderizar.render();
		
		//box2d
		b2dr.render(mundo, camJuego.combined);
		
		juego.batch.setProjectionMatrix(camJuego.combined);
		juego.batch.begin();
		jugador.draw(juego.batch);
		juego.batch.end();
		
		juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
		hud.escenario.draw();
		
	    if (FinJuego()) {
	        tiempoEspera -= delta; // Reduzca el tiempo de espera

	        if (tiempoEspera <= 0 && !cambioPantalla) {
	            // Cuando el tiempo de espera haya transcurrido y no hayamos cambiado de pantalla aún
	            juego.setScreen(new PantallaGameOver(juego));
	            cambioPantalla = true; // Evita que cambiemos de pantalla varias veces
	        }
	    }
	}
	
	public boolean FinJuego() {
		if(jugador.estadoActual == Sonic.Estado.MUERTO) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void resize(int width, int height) {
		vistaJuego.update(width, height);
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
		mapa.dispose();
		renderizar.dispose();
		mundo.dispose();
		b2dr.dispose();
		hud.dispose();
	}

}