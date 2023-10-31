package Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sonic.fangame.SonicProject;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;	

public class PantallaMenu extends ScreenAdapter {
    private Stage stage;
    private Table table;
    private SonicProject juego;
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public PantallaMenu(final SonicProject juego) {
        this.juego = juego;
        stage = new Stage(new FitViewport(SonicProject.V_ANCHO, SonicProject.V_ALTO));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        final Game finalJuego = juego; // Declarar finalJuego como final
        
        TextButton jugarLocalButton = new TextButton("Jugar Local", skin);
        jugarLocalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        // Botón para ajustes (deberás implementar esta pantalla)
//        TextButton ajustesButton = new TextButton("Ajustes", skin);
//        ajustesButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Implementa la pantalla de ajustes aquí
//            }
//        });

        // Botón para conectar en red (PantallaJuego temporal)
        TextButton conectarButton = new TextButton("Conectar", skin);
        conectarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        // Botón para salir
        TextButton salirButton = new TextButton("Salir", skin);
        salirButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Cierra la aplicación
            }
        });

        // Agrega los botones a la tabla
        table.add(jugarLocalButton).pad(10f).row();
//        table.add(ajustesButton).pad(10f).row();
        table.add(conectarButton).pad(10f).row();
        table.add(salirButton).pad(10f).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibuja la etapa
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
