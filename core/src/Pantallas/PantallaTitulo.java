package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.fangame.SonicProject;

public class PantallaTitulo implements Screen {
    private final SonicProject juego;
    private Stage stage;
    private Image imgTitulo;
    private Viewport viewport;
    private Texture imagenTitulo;
    private float tiempoTranscurrido;
    private static final float DURACION_MOSTRAR_IMAGEN = 1.5f; // Duración en segundos
    private static final float DURACION_FADE_OUT = 1.5f; // Duración del efecto de fade-out en segundos
    private boolean iniciadoFadeOut;

    public PantallaTitulo(SonicProject juego) {
        this.juego = juego;
        imagenTitulo = new Texture("logo.png"); // Reemplaza "imagenTitulo.png" con la ruta de tu imagen de título
        tiempoTranscurrido = 0;
        iniciadoFadeOut = false;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        viewport = stage.getViewport();
        imgTitulo = new Image(imagenTitulo);
        imgTitulo.setFillParent(true); // Hace que la imagen ocupe todo el espacio del viewport
        stage.addActor(imgTitulo);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        tiempoTranscurrido += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Mostrar la imagen durante la duración especificada
        if (tiempoTranscurrido <= DURACION_MOSTRAR_IMAGEN) {
            stage.act(); // Actualiza el escenario
            stage.draw(); // Dibuja el escenario
        } else {
            // Realizar el efecto de desvanecimiento
            if (!iniciadoFadeOut) {
                iniciadoFadeOut = true;
                // Realizar acciones iniciales de inicio de desvanecimiento (puedes reproducir una animación aquí)
            }

            // Calcula el valor alpha para el efecto de desvanecimiento
            float alpha = 1 - Math.min(1.0f, (tiempoTranscurrido - DURACION_MOSTRAR_IMAGEN) / DURACION_FADE_OUT);

            // Aplica el valor alpha a la imagen
            imgTitulo.getColor().a = alpha;

            stage.act(); // Actualiza el escenario
            stage.draw(); // Dibuja el escenario

            // Cuando finaliza el efecto de desvanecimiento, cambia a la pantalla de juego
            if (alpha <= 0) {
                juego.setScreen(new PantallaMenu(juego));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el viewport cuando se redimensiona la ventana
        viewport.update(width, height, true);
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
        imagenTitulo.dispose();
        stage.dispose();
    }
}

