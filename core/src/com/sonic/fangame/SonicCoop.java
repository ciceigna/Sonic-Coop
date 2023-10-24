//package com.sonic.fangame;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//import Pantallas.PantallaSega;
////import Pantallas.PantallaJuego;
//import personajes.Sonic;
//
//public class SonicCoop extends Game {
//    private Music backgroundMusic;
//    public SpriteBatch batch;
//    private OrthographicCamera camera;
//    private TiledMap map;
//    private TiledMapRenderer mapRenderer;
//    private float tiempo = 0f;
//    Sonic sonic;
//    
//    // Variables de control
//    boolean camina = false;
//    boolean miraDerecha = true; // Dirección inicial del personaje
//    Animation<AtlasRegion> caminarAnimacion;
//    TextureRegion cuadroQuieto;
//	TextureRegion cuadroQuietoInvertido;
//	Viewport viewport;
//
//    public static float anchoMundo = 800;
//    public static float altoMundo = 600;
//
//
//	@Override
//    public void create() {
////		batch = new SpriteBatch();
//		setScreen(new PantallaSega(this));
//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();
//        
//        viewport = new FitViewport(anchoMundo, altoMundo, new OrthographicCamera()); // Inicializamos el viewport
//        camera = (OrthographicCamera) viewport.getCamera(); // Obtenemos la cámara del viewport
//        
//        TextureAtlas caminaAtlas = new TextureAtlas(Gdx.files.internal("texturaSonic.atlas"));
//        caminarAnimacion = new Animation<>(0.1f, caminaAtlas.findRegion("basicMotion1"));
//        Texture texturaQuieta = new Texture(Gdx.files.internal("spritesSonic/quieto.png"));
//        cuadroQuieto = new TextureRegion(texturaQuieta);
//        cuadroQuietoInvertido = new TextureRegion(texturaQuieta);
//        
//        cuadroQuietoInvertido.flip(true, false);
//        
//        // Cargar la música de fondo
//        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("exoplanetaFloreado.mp3"));
//        
//        // Configurar opciones de la música de fondo
//        backgroundMusic.setLooping(true); // Reproducir en bucle
//        backgroundMusic.setVolume(0.1f); // Ajustar el volumen (0.0 a 1.0)
//        
//        // Reproducir la música de fondo
//        backgroundMusic.play();
//        
//        
//        camera.setToOrtho(false, w, h);
//        camera.update();
//        
//        // Cargar el mapa desde el archivo TMX
//        TmxMapLoader mapLoader = new TmxMapLoader();
//        map = mapLoader.load("prueba.tmx");
//        
//        // Configurar el renderizador del mapa
//        mapRenderer = new OrthogonalTiledMapRenderer(map);
//        
//        batch = new SpriteBatch(); // Inicializamos el SpriteBatch
//        
//        sonic = new Sonic(16, 753); // Crear instancia de Sonic en posición (16, 736) (ajústalo según tu diseño de nivel)
//    }
//
//
//    @Override
//    public void render() {
//    	super.render();
//    	
//    	 tiempo += Gdx.graphics.getDeltaTime();
//    	
//        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
//            camina = true;
//            miraDerecha = Gdx.input.isKeyPressed(Input.Keys.D); // Dirección del movimiento (derecha o izquierda)
//            float sonicSpeed = 3f; // Velocidad de movimiento de Sonic
//            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                sonic.x -= sonicSpeed; // Mover a la izquierda
//            }
//            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                sonic.x += sonicSpeed; // Mover a la derecha
//            }
//        } else {
//            camina = false;
//        }
//        
//        camera.position.set(sonic.x + sonic.getWidth() / 3, sonic.y + sonic.getHeight() / 3, 0);
//        camera.update();
//        
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        
//        mapRenderer.setView(camera);
//        mapRenderer.render();
//
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        
//        if (camina) {
//            // Dibujar la animación invertida cuando el personaje está caminando a la izquierda
//            if (!miraDerecha) {
//                batch.draw(sonic.animacionInvertida.getKeyFrame(tiempo, true), sonic.x, sonic.y);
//            } else {
//                // Dibujar la animación normal cuando el personaje está caminando a la derecha
//                batch.draw(sonic.animacion.getKeyFrame(tiempo, true), sonic.x, sonic.y);
//            }
//        } else {
//            // Dibujar el frame de "estar parado" cuando no se está caminando
//            if (miraDerecha) {
//                batch.draw(cuadroQuieto, sonic.x, sonic.y);
//            } else if (!miraDerecha){
//            	batch.draw(cuadroQuietoInvertido, sonic.x, sonic.y);
//            }else{
//                batch.draw(cuadroQuieto, sonic.x + sonic.getWidth(), sonic.y, -sonic.getWidth(), sonic.getHeight());
//            }
//        }
//
//        batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height); // Actualizamos el viewport al cambiar el tamaño de la ventana
//        camera.position.set(sonic.x + sonic.getWidth() / 3, sonic.y + sonic.getHeight() / 3, 0);
//    }
//    
//    @Override
//    public void dispose() {
//        System.out.println("Dispose");
//        
//        if (backgroundMusic != null) {
//            backgroundMusic.stop();
//            backgroundMusic.dispose();
//        }
//        map.dispose();
//        batch.dispose();
//    }
//}
