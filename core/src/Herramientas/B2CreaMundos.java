package Herramientas;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.fangame.SonicProject;

import Sprites.Anillos;

public class B2CreaMundos {

	public B2CreaMundos(World mundo, TiledMap mapa) {
		BodyDef cdef = new BodyDef();
		PolygonShape forma = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body cuerpo;
		
		//colinas
		for(MapObject object : mapa.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
		
			cdef.type = BodyDef.BodyType.StaticBody;
			cdef.position.set(rect.getX() + rect.getWidth() / 2 / SonicProject.PPM, rect.getY() + rect.getHeight() / 2 / SonicProject.PPM);
		
			cuerpo = mundo.createBody(cdef);
			
			forma.setAsBox(rect.getWidth() / 2  / SonicProject.PPM, rect.getHeight() / 2  / SonicProject.PPM);
			fdef.shape = forma;
			cuerpo.createFixture(fdef);
		}
		
		//piso
		for(MapObject object : mapa.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
		
			cdef.type = BodyDef.BodyType.StaticBody;
			cdef.position.set(rect.getX() + rect.getWidth() / 2 / SonicProject.PPM, rect.getY() + rect.getHeight() / 2 / SonicProject.PPM);
		
			cuerpo = mundo.createBody(cdef);
			
			forma.setAsBox(rect.getWidth() / 2 / SonicProject.PPM, rect.getHeight() / 2 / SonicProject.PPM);
			fdef.shape = forma;
			cuerpo.createFixture(fdef);
		}
		
		for(MapObject object : mapa.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new Anillos(mundo,mapa,rect);
		}
	}
}
