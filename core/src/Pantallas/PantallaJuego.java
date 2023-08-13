package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sonic.fangame.SonicCoop;

import Escenas.Hud;

public class PantallaJuego implements Screen {

	private SonicCoop game;
	private Hud hud;
	
	//BOX 2D
	private World mundo;
	private Box2DDebugRenderer b2dr;
	
	//CONSTRUCTOR
	public PantallaJuego(SonicCoop game) {
		this.game = game;
//		gamecam = new OrthographicCamera();
//		gamePort = new FitViewport();
		hud = new Hud(game.batch);
		
		mundo = new World(new Vector2(0,0), true);
		b2dr = new Box2DDebugRenderer();
		
//		BodyDef bdef = new BodyDef();
//		PolygonShape forma = new PolygonShape();
//		FixtureDef fdef = new FixtureDef();
//		Body cuerpo; 
		
//		for(MapObject object : map.getLayers().get().getObjects().getByType(RectangleMapObject.class)) {
			
//		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
        hud.escenario.draw();
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
