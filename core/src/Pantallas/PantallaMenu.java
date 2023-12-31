package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.reactive.sonic.red.HiloCliente;
import utiles.Global;
import utiles.Utiles;

import com.sonic.fangame.SonicProject;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;	

public class PantallaMenu extends ScreenAdapter {
    private Stage stage;
    private Table table;
    private HiloCliente hc;
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public PantallaMenu(final SonicProject juego) {
        stage = new Stage(new FitViewport(SonicProject.V_ANCHO, SonicProject.V_ALTO));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        
        SonicProject.admin.get("audio/musica/gameOver.mp3", Music.class).stop();
        SonicProject.admin.get("audio/musica/menu.mp3", Music.class).play();

        // Botón para conectar en red
        final TextButton botonConectar = new TextButton("Conectarse", skin);
        botonConectar.addListener(new ClickListener() {
            private boolean botonPresionado = false;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!botonPresionado) {
                    juego.setScreen(new PantallaJuego(juego));
                    botonConectar.setDisabled(true);
                    botonConectar.setText("Conectando...");
                    botonPresionado = true;
                }
            }
        });

        // Botón para ajustes
        TextButton botonAjustes = new TextButton("Ajustes", skin);
        botonAjustes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	juego.setScreen(new PantallaAjustes(juego));
            }
        });

        // Botón para salir
        TextButton botonSalir = new TextButton("Salir", skin);
        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Cierra la aplicación
            }
        });

        // Agrega los botones a la tabla
        table.add(botonConectar).pad(10f).row();
        table.add(botonAjustes).pad(10f).row();
        table.add(botonSalir).pad(10f).row();

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
        skin.dispose();
    }
}
