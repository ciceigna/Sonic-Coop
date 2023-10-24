//package Pantallas;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.sonic.fangame.SonicCoop;
//
//public class PantallaSega implements Screen {
//    private SonicCoop game;
//    private Texture fondoPresentacion;
//    private SpriteBatch batch;
//    private static final float DURACION_PRESENTACION = 10f; // Duración en segundos
//    private float tiempoTranscurrido = 0;
//    
//    public PantallaSega(SonicCoop game) {
//        this.game = game;
//    }
//
//    @Override
//    public void show() {
//        fondoPresentacion = new Texture("pantalla_sega.png"); // Carga la imagen de fondo de la presentación
//        batch = new SpriteBatch();
//        if (fondoPresentacion != null) {
//            Gdx.app.log("PantallaPresentacion", "Textura cargada con éxito.");
//        } else {
//            Gdx.app.error("PantallaPresentacion", "Error al cargar la textura.");
//        }
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        
//        tiempoTranscurrido += delta;
//        batch.begin();
//        batch.draw(fondoPresentacion, 0, 0); // Renderiza la textura en la esquina superior izquierda
//        batch.end();
//
//
//        
//        if (tiempoTranscurrido > DURACION_PRESENTACION) {
//            game.setScreen(new PantallaJuego(game));
//        }
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        fondoPresentacion.dispose();
//        batch.dispose();
//    }
//}
