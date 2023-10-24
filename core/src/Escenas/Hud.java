 package Escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sonic.fangame.SonicProject;

public class Hud implements Disposable{
	public Stage escenario;
	private Viewport viewport;
	
	private float cuentaTiempo;
	private Integer puntaje;

	Label etiquetaTiempo;
	Label etiquetaTiempepopo;
	Label etiquetaPuntaje;
	Label etiquetaPunputapajepe;
	Label etiquetaAnillos;
	Label etiquetaApanipillospo;
	
	public Hud(SpriteBatch sb) {
		cuentaTiempo = 0;
		puntaje = 0;
		
		viewport = new FitViewport(SonicProject.V_ANCHO,SonicProject.V_ALTO,new OrthographicCamera());
		escenario = new Stage(viewport, sb);
		
		Table mesa = new Table();
		mesa.top();
		mesa.left();
		mesa.setFillParent(true);
		

		etiquetaTiempepopo = new Label(String.format("%06f", cuentaTiempo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaPunputapajepe = new Label(String.format("%06d", puntaje), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		etiquetaPuntaje = new Label("  PUNTAJE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		etiquetaTiempo = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		etiquetaAnillos = new Label(" ANILLOS", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
		
		mesa.add(etiquetaPuntaje);
		mesa.add(etiquetaPunputapajepe);
		mesa.row();
		mesa.add(etiquetaTiempo);
		mesa.add(etiquetaTiempepopo);
		mesa.row();
		mesa.add(etiquetaAnillos);
		
		escenario.addActor(mesa);
	}

	@Override
	public void dispose() {
		escenario.dispose();
		
	}
}
