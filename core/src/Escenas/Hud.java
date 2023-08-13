package Escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.fangame.SonicCoop;

public class Hud {
	
	public Stage escenario;
	private Viewport viewport;
	
	private float cuentaTiempo;
	private Integer puntaje;
	
	Label etiquetaPuntaje;
	Label etiquetaTiempo;
	Label etiquetaEstrellas;
	
	public Hud(SpriteBatch sb) {
		cuentaTiempo = 0;
		puntaje = 0;
		
		viewport = new FitViewport(SonicCoop.anchoMundo,SonicCoop.altoMundo,new OrthographicCamera());
		escenario = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		etiquetaPuntaje = new Label(String.format("%06d", puntaje), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaTiempo = new Label(String.format("%06d", cuentaTiempo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaEstrellas = new Label(String.format("%02d", cuentaTiempo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		escenario.addActor(table);
	}
}
