package Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Filter;
import com.sonic.fangame.SonicProject;
import com.badlogic.gdx.utils.Array;
import Pantallas.PantallaJuego;


public class Sonic extends Sprite {
	public enum Estado{CAYENDO,SALTANDO,PARADO,CORRIENDO, CORRIENDO_IZQ, MUERTO}
	public Estado estadoActual;
	public Estado estadoPrevio;
	
	private TextureRegion sonicQuieto;
	private TextureRegion sonicMuerto;
	private Animation<TextureRegion> sonicCorre;
	private Animation<TextureRegion> sonicSalta;
	private Animation<TextureRegion> sonicVelMax;
	
	private boolean murioSonic = false;
	
	private float estadoTiempo;
	
	public Sonic(PantallaJuego pantalla) {
	    super(pantalla.getAtlas().findRegion("basicMotion1"));
	    estadoActual = Estado.PARADO;
	    estadoPrevio = Estado.PARADO;
	    estadoTiempo = 0;
	    String regionName;
	    
	    Array<TextureRegion> frames = new Array<TextureRegion>();

	    for (int i = 1; i <= 8; i++) {
	        regionName = "basicMotion" + i;
	        frames.add(pantalla.getAtlas().findRegion(regionName));
	    }

	    Array<TextureAtlas.AtlasRegion> regionesSalto = new Array<>();
	    for (int i = 1; i <= 4; i++) {
	    	regionName = "bola" + i;
	        regionesSalto.add(pantalla.getAtlas().findRegion(regionName));
	    }
	    sonicSalta = new Animation<TextureRegion>(0.08f, regionesSalto);

	    // Crea la animación
	    sonicCorre = new Animation<TextureRegion>(0.1f, frames);
	    frames.clear();

	    sonicQuieto = new TextureRegion(getTexture(), 1410, 2, 27, 59);
	    sonicMuerto = new TextureRegion(getTexture(), 768, 2, 34, 59);
	    setBounds(200 / SonicProject.PPM,775 / SonicProject.PPM, getWidth() / SonicProject.PPM, getHeight() / SonicProject.PPM);
	    setRegion(sonicQuieto);
	}

	public void golpe() {
		Filter filtro = new Filter();
		filtro.maskBits = SonicProject.BIT_VACIO;

		SonicProject.admin.get("audio/sonidos/s_muerte.wav", Sound.class).play();
		murioSonic = true;
}
	
	public void update(float dt) {
		setRegion(getFrame(dt));
		
	}
	
	public TextureRegion getFrame(float dt) {

	    TextureRegion region;
	    switch (estadoActual) {
		    case MUERTO:
		    	region = sonicMuerto;
		    	break;
	        case SALTANDO:
	            region = sonicSalta.getKeyFrame(estadoTiempo, true);
	            break;
	        case CORRIENDO:
	            region = sonicCorre.getKeyFrame(estadoTiempo, true);
    	        if (region.isFlipX()) {
    	            region.flip(true, false);
    	        }
	            break;
	        case CORRIENDO_IZQ:
	            region = sonicCorre.getKeyFrame(estadoTiempo, true);
		        if (!region.isFlipX()) {
		            region.flip(true, false);
		        }
	            break;
	        case CAYENDO:
	        case PARADO:
	        default:
	            region = sonicQuieto;
	            break;
	    }

	    if (dt > 0) {
	        estadoTiempo = estadoActual == estadoPrevio ? estadoTiempo + dt : 0;
	    }

	    estadoPrevio = estadoActual;
	    return region;
	}
	
}
