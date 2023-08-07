package com.sonic.fangame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import personajes.Sonic;

public class Principal extends Game {
    private Music backgroundMusic;
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapRenderer mapRenderer;
    private float tiempo = 0f;
    Sonic sonic;
    
    // Variables de control
    boolean camina = false;
    boolean miraDerecha = true; // Dirección inicial del personaje
    Animation<AtlasRegion> caminarAnimacion;
    TextureRegion cuadroQuieto;
	TextureRegion cuadroQuietoInvertido;


    @Override
    public void create() {
        System.out.println("Create");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
        float worldWidth = 800;
        float worldHeight = 600;
        
        TextureAtlas walkAtlas = new TextureAtlas(Gdx.files.internal("texturaSonic.atlas"));
        caminarAnimacion = new Animation<>(0.1f, walkAtlas.findRegion("basicMotion1"));
        caminarAnimacion = new Animation<>(0.1f, walkAtlas.findRegion("basicMotion2"));
        caminarAnimacion = new Animation<>(0.1f, walkAtlas.findRegion("basicMotion3"));
        Texture texturaQuieta = new Texture(Gdx.files.internal("spritesSonic/quieto.png"));
        cuadroQuieto = new TextureRegion(texturaQuieta);
        cuadroQuietoInvertido = new TextureRegion(texturaQuieta);
        
        cuadroQuietoInvertido.flip(true, false);
        
        // Cargar la música de fondo
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("exoplanetaFloreado.mp3"));
        
        // Configurar opciones de la música de fondo
        backgroundMusic.setLooping(true); // Reproducir en bucle
        backgroundMusic.setVolume(0.3f); // Ajustar el volumen (0.0 a 1.0)
        
        // Reproducir la música de fondo
        backgroundMusic.play();
        
        world = new World(new Vector2(0, -9.81f), true);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        
        // Cargar el mapa desde el archivo TMX
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("prueba.tmx");
        
        // Configurar el renderizador del mapa
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        
        batch = new SpriteBatch(); // Inicializamos el SpriteBatch
        
        sonic = new Sonic(16, 736); // Crear instancia de Sonic en posición (16, 736) (ajústalo según tu diseño de nivel)
    }

    @Override
    public void render() {
    	
    	 tiempo += Gdx.graphics.getDeltaTime();
    	
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            camina = true;
            miraDerecha = Gdx.input.isKeyPressed(Input.Keys.D); // Dirección del movimiento (derecha o izquierda)
            float sonicSpeed = 2f; // Velocidad de movimiento de Sonic
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                sonic.x -= sonicSpeed; // Mover a la izquierda
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                sonic.x += sonicSpeed; // Mover a la derecha
            }
        } else {
            camina = false;
        }
        
        camera.position.set(sonic.x + sonic.getWidth() / 3, sonic.y + sonic.getHeight() / 3, 0);
        camera.update();
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        if (camina) {
            // Dibujar la animación invertida cuando el personaje está caminando a la izquierda
            if (!miraDerecha) {
                batch.draw(sonic.animacionInvertida.getKeyFrame(tiempo, true), sonic.x, sonic.y);
            } else {
                // Dibujar la animación normal cuando el personaje está caminando a la derecha
                batch.draw(sonic.animacion.getKeyFrame(tiempo, true), sonic.x, sonic.y);
            }
        } else {
            // Dibujar el frame de "estar parado" cuando no se está caminando
            if (miraDerecha) {
                batch.draw(cuadroQuieto, sonic.x, sonic.y);
            } else if (!miraDerecha){
            	batch.draw(cuadroQuietoInvertido, sonic.x, sonic.y);
            }else{
                batch.draw(cuadroQuieto, sonic.x + sonic.getWidth(), sonic.y, -sonic.getWidth(), sonic.getHeight());
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        System.out.println("Dispose");
        
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        map.dispose();
        batch.dispose();
    }
}