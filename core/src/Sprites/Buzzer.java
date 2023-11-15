package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.sonic.fangame.SonicProject;

import Pantallas.PantallaJuego;

public class Buzzer extends Enemigo {

	private float estadoTiempo;
	private Animation<TextureRegion> animacionVuela;
	private Array<TextureRegion> cuadros;
	
	public Buzzer(PantallaJuego pantalla, float x, float y) {
	    super(pantalla, x, y);
	    estadoTiempo = 0;
	    cuadros = new Array<TextureRegion>();
	    String regionName;

	    for (int i = 1; i <= 4; i++) {
	        regionName = "buzzer" + i;
	        cuadros.add(pantalla.getEnemigos().findRegion(regionName));
	    }

	    animacionVuela = new Animation<TextureRegion>(0.4f, cuadros);
	    cuadros.clear();

	    setBounds(getX(), getY(), getRegionWidth() / SonicProject.PPM, getRegionHeight() / SonicProject.PPM);
	    setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	}


	public void update(float dt) {
		setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	
	}

}
