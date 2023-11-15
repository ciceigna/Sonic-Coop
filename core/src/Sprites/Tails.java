package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sonic.fangame.SonicProject;
import com.badlogic.gdx.utils.Array;
import Pantallas.PantallaJuego;


public class Tails extends Sprite {
	public enum Estado{CAYENDO,SALTANDO,PARADO,CORRIENDO, VELOCIDAD_MAXIMA, MUERTO}
	public Estado estadoActual;
	public Estado estadoPrevio;
	//public World mundo;
//	public Body b2cuerpo;
	
	private Animation<TextureRegion> TailsQuieto;
	private TextureRegion TailsMuerto;
	private Animation<TextureRegion> TailsCorre;
	private Animation<TextureRegion> TailsSalta;
	private Animation<TextureRegion> TailsVelMax;
	
	private float estadoTiempo;
	
	public Tails(/*World mundo,*/ PantallaJuego pantalla) {
	    super(pantalla.getAtlasAlt().findRegion("basicMotion1")); // Inicializa con la primera región de "basicMotion"
	    //this.mundo = mundo;
	    estadoActual = Estado.PARADO;
	    estadoPrevio = Estado.PARADO;
	    estadoTiempo = 0;
	    String regionName;
	    
	    Array<TextureRegion> frames = new Array<TextureRegion>();

	    // Carga las regiones etiquetadas como "basicMotion" desde el atlas
	    for (int i = 1; i <= 8; i++) {
	        regionName = "basicMotion" + i;
	        frames.add(pantalla.getAtlasAlt().findRegion(regionName));
	    }
	    
	    Array<TextureAtlas.AtlasRegion> regionesSalto = new Array<>();
	    for (int i = 1; i <= 3; i++) {
	    	regionName = "bola" + i;
	        regionesSalto.add(pantalla.getAtlasAlt().findRegion(regionName));
	    }
	    TailsSalta = new Animation<TextureRegion>(0.08f, regionesSalto);

	    // Crea la animación
	    TailsCorre = new Animation<TextureRegion>(0.1f, frames);
	    frames.clear();

	    TailsQuieto = new Animation<TextureRegion>(0.1f, frames);
	    frames.clear();
	    
	 // Carga al tails quieto
	    for (int i = 1; i <= 4; i++) {
	        regionName = "quieto" + i;
	        frames.add(pantalla.getAtlasAlt().findRegion(regionName));
	    }
	    TailsQuieto = new Animation<TextureRegion>(0.16f, frames); // Configura la animación
	    frames.clear(); // No elimines las regiones de "frames"

//	    defineTails();
	    TailsMuerto = new TextureRegion(getTexture(), 1541, 17, 29, 32);
	    setBounds(64 / SonicProject.PPM,775 / SonicProject.PPM, getWidth() / SonicProject.PPM, getHeight() / SonicProject.PPM);
	    setRegion(TailsQuieto.getKeyFrame(estadoTiempo, true));

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
//			b2cuerpo.applyLinearImpulse(new Vector2(0, 4f), b2cuerpo.getWorldCenter(), true);
//			murioTails = true;
//			}
//	}
	
	public void update(float dt) {
		setRegion(getFrame(dt));
		
	}
	
	public TextureRegion getFrame(float dt) {
//	    estadoActual = getEstado();

	    TextureRegion region;
	    switch (estadoActual) {
		    case MUERTO:
		    	region = TailsMuerto;
		    	break;
	        case SALTANDO:
	            region = TailsSalta.getKeyFrame(estadoTiempo, true);
	            break;
	        case CORRIENDO:
	            region = TailsCorre.getKeyFrame(estadoTiempo, true);
	            break;
	        case VELOCIDAD_MAXIMA:
	            region = TailsVelMax.getKeyFrame(estadoTiempo, true);
	            break;
	        case CAYENDO:
	        case PARADO:
	        default:
	            region = TailsQuieto.getKeyFrame(estadoTiempo, true);
	            break;
	    }

	    if (dt > 0) {
	        estadoTiempo = estadoActual == estadoPrevio ? estadoTiempo + dt : 0;
	    }

	    estadoPrevio = estadoActual;
	    return region;
	}

	
//	public Estado getEstado() {
//		if(murioTails)
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
