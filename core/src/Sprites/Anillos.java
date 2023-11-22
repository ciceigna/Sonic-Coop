package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import Escenas.Hud;

public class Anillos extends ObjetoInteractivo {
	
	public Anillos(/*World mundo,*/TiledMap mapa, Rectangle bordes) {
		super(/*mundo,*/ mapa, bordes);	
//		fixture.setUserData(this);
//		setCategoryFilter(SonicProject.BIT_ANILLO);
	}

	@Override
	public void esTocado() {
		Gdx.app.log("Anillo", "Colision");
		Hud.addPuntaje(100);
		Hud.addAnillo(1);
	}
	
}
