package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sonic.fangame.SonicProject;
import com.badlogic.gdx.utils.Array;
import Pantallas.PantallaJuego;


public class Sonic extends Sprite {
	public enum Estado{CAYENDO,SALTANDO,PARADO,CORRIENDO, VELOCIDAD_MAXIMA, MUERTO}
	public Estado estadoActual;
	public Estado estadoPrevio;
	//public World mundo;
//	public Body b2cuerpo;
	
	private TextureRegion sonicQuieto;
	private TextureRegion sonicMuerto;
	private Animation<TextureRegion> sonicCorre;
	private Animation<TextureRegion> sonicSalta;
	private Animation<TextureRegion> sonicVelMax;
	
	private boolean murioSonic = false;
	
	private float estadoTiempo;
	
	public Sonic(/*World mundo, */PantallaJuego pantalla) {
	    super(pantalla.getAtlas().findRegion("basicMotion1")); // Inicializa con la primera región de "basicMotion"
	    //this.mundo = mundo;
	    estadoActual = Estado.PARADO;
	    estadoPrevio = Estado.PARADO;
	    estadoTiempo = 0;
	    String regionName;
	    
	    Array<TextureRegion> frames = new Array<TextureRegion>();

	    // Carga las regiones etiquetadas como "basicMotion" desde el atlas
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

//	    defineSonic();
	    sonicQuieto = new TextureRegion(getTexture(), 1410, 2, 27, 59);
	    sonicMuerto = new TextureRegion(getTexture(), 768, 2, 34, 59);
	    setBounds(200 / SonicProject.PPM,775 / SonicProject.PPM, getWidth() / SonicProject.PPM, getHeight() / SonicProject.PPM);
	    setRegion(sonicQuieto);
	}

//	public void golpe() {
//		if(tieneAnillos) {
//			tieneAnillos=false;
//			
//		}
//		else {
//			Filter filtro = new Filter();
//			filtro.maskBits = SonicProject.BIT_VACIO;
//
//			SonicProject.admin.get("audio/sonidos/s_muerte.wav", Sound.class).play();
//			for(Fixture fixture : b2cuerpo.getFixtureList()) {
//				fixture.setFilterData(filtro);
//			}
//			b2cuerpo.applyLinearImpulse(new Vector2(0, 3f), b2cuerpo.getWorldCenter(), true);
//			murioSonic = true;
////		}
//	}
	
	public void update(float dt) {
//		setPosition(b2cuerpo.getPosition().x - getWidth() / 2, b2cuerpo.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
		
	}
	
	public TextureRegion getFrame(float dt) {
	    //estadoActual = getEstado();

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
	            break;
	        case VELOCIDAD_MAXIMA:
	            region = sonicVelMax.getKeyFrame(estadoTiempo, true);
	            break;
	        case CAYENDO:
	        case PARADO:
	        default:
	            region = sonicQuieto;
	            break;
	    }

//	    if (b2cuerpo.getLinearVelocity().x < 0) {
//	        if (!region.isFlipX()) {
//	            region.flip(true, false);
//	        }
//	    } else if (b2cuerpo.getLinearVelocity().x > 0) {
//	        if (region.isFlipX()) {
//	            region.flip(true, false);
//	        }
//	    }

	    estadoTiempo = estadoActual == estadoPrevio ? estadoTiempo + dt : 0;
	    estadoPrevio = estadoActual;
	    return region;
	}

	
//	public Estado getEstado() {
//		if(murioSonic)
//			return Estado.MUERTO;
//		else if(b2cuerpo.getLinearVelocity().y > 0 || (b2cuerpo.getLinearVelocity().y < 0 && estadoPrevio == Estado.SALTANDO)) 
//			return Estado.SALTANDO;
//		else if(b2cuerpo.getLinearVelocity().y < 0)
//			return Estado.CAYENDO;
//		else if(b2cuerpo.getLinearVelocity().x != 0)
//			return Estado.CORRIENDO;
//		else 
//			return Estado.PARADO;
//	}
	
	
}
