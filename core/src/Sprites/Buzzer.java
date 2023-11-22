package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.sonic.fangame.SonicProject;

import Escenas.Hud;
import Pantallas.PantallaJuego;
import Pantallas.PantallaJuego.TipoTextura;

public class Buzzer extends Enemigo {

	private float estadoTiempo;
	private Animation<TextureRegion> animacionVuela;
	private Animation<TextureRegion> animacionExplosion;
	private Array<TextureRegion> cuadros;
	private boolean muereBuzzer = false;
	private boolean muerto = false;
	
	public Buzzer(PantallaJuego pantalla, float x, float y) {
	    super(pantalla, x, y);

	    cuadros = new Array<TextureRegion>();
	    String regionName;

	    for (int i = 1; i <= 5; i++) {
	        regionName = "buzzerMuere" + i;
	        cuadros.add(pantalla.getTextura(TipoTextura.ENEMIGOS).findRegion(regionName));
	    }
	    animacionExplosion = new Animation<TextureRegion>(0.25f, cuadros);
	    cuadros.clear();
	    
	    for (int i = 1; i <= 3; i++) {
	        regionName = "buzzer" + i;
	        cuadros.add(pantalla.getTextura(TipoTextura.ENEMIGOS).findRegion(regionName));
	    }
	    animacionVuela = new Animation<TextureRegion>(0.25f, cuadros);
	    cuadros.clear();

	    setBounds(getX(), getY(), 48 / SonicProject.PPM, 26 / SonicProject.PPM);
	    setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	    estadoTiempo = 0;
	}
	
	public void update(float dt) {
		estadoTiempo += dt;
		if (muereBuzzer && !muerto) {
        	muerto = true;
			setRegion(animacionExplosion.getKeyFrame(estadoTiempo, true));
	   	    Hud.addPuntaje(100); 
		} else if (!muerto){
			setRegion(animacionVuela.getKeyFrame(estadoTiempo, true));
	    }
	}

	public void muerte() {
		muereBuzzer = true; 
	}

}
