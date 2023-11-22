package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reactive.sonic.red.HiloCliente;
import com.sonic.fangame.SonicProject;

import Escenas.FondoParallax;
import Escenas.Hud;
import Sprites.Buzzer;
import Sprites.Sonic;
import Sprites.Tails;
import utiles.Global;

public class PantallaJuego implements Screen {
	//base
	private SonicProject juego;
	public OrthographicCamera camJuego;
	private Viewport vistaJuego; 
	
	private TextureAtlas sonic;
	private TextureAtlas tails;
	private TextureAtlas enemigos;
	private TextureAtlas objetos;
	
	public Sonic jugador;
	public Tails jugadorAlt;
	public Buzzer buzzer;
	
	float limMapa1 = 0.25f;
    float limMapa2 = 23.5f;
    private HiloCliente hc;
    Texture esperaTextura;
    Image esperaImagen;
    Stage stage;
    Table table;
    
	//hud
	private Hud hud;
	
	//mapa tiled
	private TmxMapLoader cargaMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderizar;
	private FondoParallax fondo;
	
	//box2d obj. y colisiones
	private World mundo;
	private Box2DDebugRenderer b2dr;
	
	private float tiempoEspera = 0.85f;
	private boolean cambioPantalla = false;
	
	public PantallaJuego(SonicProject juego) {
		
        this.juego = juego;
        
        esperaTextura = new Texture("esperaImagen.png");
        esperaImagen = new Image(esperaTextura);
        stage = new Stage(new FitViewport(SonicProject.V_ANCHO, SonicProject.V_ALTO));
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
        
		sonic = new TextureAtlas("texturaSonic.atlas");
		tails = new TextureAtlas("texturaTails.atlas");
		enemigos = new TextureAtlas("texturaEnemigos.atlas");
		objetos = new TextureAtlas("objetos.atlas");
		
		camJuego = new OrthographicCamera();
		vistaJuego = new FitViewport(SonicProject.V_ANCHO / SonicProject.PPM,SonicProject.V_ALTO / SonicProject.PPM,camJuego);
		
		//hud
		hud = new Hud(juego.batch);
		
		//tiled
		cargaMapa = new TmxMapLoader();
		mapa = cargaMapa.load("prueba.tmx");
		renderizar = new OrthogonalTiledMapRenderer(mapa, 1 / SonicProject.PPM);
		fondo = new FondoParallax(camJuego); 
		
		camJuego.position.set(vistaJuego.getWorldWidth() / 2, vistaJuego.getWorldHeight() / 2, 0);
		
		jugador = new Sonic(this);
		jugadorAlt = new Tails(this);
		buzzer = new Buzzer(this, 1000 / SonicProject.PPM, 790 / SonicProject.PPM );
		hc = new HiloCliente(this);
		hc.start();
		
        SonicProject.admin.get("audio/musica/menu.mp3", Music.class).stop();
        SonicProject.admin.get("audio/musica/gameOver.mp3", Music.class).stop();
	}
	
	public TextureAtlas getTextura(TipoTextura tipo) {
	    switch (tipo) {
	        case SONIC:
	            return sonic;
	        case TAILS:
	            return tails;
	        case ENEMIGOS:
	            return enemigos;
	        case OBJETOS:
	            return objetos;
	        default:
	            return null;
	    }
	}
	
	public enum TipoTextura {
	    SONIC,
	    TAILS,
	    ENEMIGOS,
	    OBJETOS
	}
	
	@Override
	public void show() {
	}
	
	public void handleInput() {
		if(jugador.estadoActual != Sonic.Estado.MUERTO && jugadorAlt.estadoActual != Tails.Estado.MUERTO) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				hc.enviarMensaje("teclaArriba");
				SonicProject.admin.get("audio/sonidos/s_salto.wav", Sound.class).play();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				hc.enviarMensaje("teclaDer");
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				hc.enviarMensaje("teclaIzq");
			}
		}
	}
	
	public void update(float dt) {
	    handleInput();

	    jugador.update(dt);
	    jugadorAlt.update(dt);
	    buzzer.update(dt);
	    hud.update(dt);
	    
	    if (Global.esPrimerCliente) {
	        camJuego.position.set(jugador.getX(), jugador.getY(), 0);
	    } else{
	        camJuego.position.set(jugadorAlt.getX(), jugadorAlt.getY(), 0);
	    }
	    camJuego.update();
	    renderizar.setView(camJuego);
	}

	@Override
	public void render(float delta) {
		if(!Global.empieza) {
			table.clear(); // Limpia los actores anteriores de la tabla
	        table.add(esperaImagen).expand().center().row();
		}else {
			update(delta);
			
			SonicProject.admin.get("audio/musica/pantallaJuego.mp3", Music.class).play();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			fondo.render(0.80f, 75);
			renderizar.render();
			
			juego.batch.setProjectionMatrix(camJuego.combined);
			juego.batch.begin();
			jugador.draw(juego.batch);
			jugadorAlt.draw(juego.batch);
			buzzer.draw(juego.batch);
			juego.batch.end();
			
			juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
			hud.escenario.draw();
			
		    if (FinJuego()) {
		        tiempoEspera -= delta;
		        if (tiempoEspera <= 0 && !cambioPantalla) {
		            juego.setScreen(new PantallaGameOver(juego));
		            cambioPantalla = true;
		        }
		    }
		}
	}
	
	public boolean FinJuego() {
		if(jugador.estadoActual == Sonic.Estado.MUERTO || jugadorAlt.estadoActual == Tails.Estado.MUERTO) {
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