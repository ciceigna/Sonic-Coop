package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class ObjetoInteractivo {

	//protected World mundo;
	protected TiledMap mapa;
	protected TiledMapTile tile;
	protected Rectangle bordes;
//	protected Body cuerpo;
//	protected Fixture fixture;
	
	public ObjetoInteractivo(/*World mundo,*/ TiledMap mapa, Rectangle bordes) {
		//this.mundo = mundo;
		this.mapa = mapa;
		this.bordes = bordes;
		
//		BodyDef cdef = new BodyDef();
//		FixtureDef fdef = new FixtureDef();
//		PolygonShape forma = new PolygonShape();
//		
//		//anillos
//		cdef.type = BodyDef.BodyType.StaticBody;
//		cdef.position.set(bordes.getX() + bordes.getWidth() / 2 / SonicProject.PPM, bordes.getY() + bordes.getHeight() / 2 / SonicProject.PPM);
//			
//		cuerpo = mundo.createBody(cdef);
//				
//		forma.setAsBox(bordes.getWidth() / 2  / SonicProject.PPM, bordes.getHeight() / 2  / SonicProject.PPM);
//		fdef.shape = forma;
//		fixture = cuerpo.createFixture(fdef);
	}
	
	public abstract void esTocado();
//	public void setCategoryFilter(short filtroBit) {
//		Filter filtro = new Filter();
//		filtro.categoryBits = filtroBit;
//		fixture.setFilterData(filtro);
//	}
	
}
