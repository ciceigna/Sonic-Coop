package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Sonic extends Jugadores {

    public float x;
    public float y;
    public Animation<AtlasRegion> animacion;
    public float tiempo;
	public Animation<AtlasRegion> animacionInvertida;

    public Sonic(float x, float y) {
        this.x = x;
        this.y = y;

        // Cargar el archivo .atlas para obtener las regiones de textura
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("texturaSonic.atlas"));
        
        //CAMINAR DERECHA
        // Cargar las regiones de textura desde el archivo .atlas
        Array<TextureAtlas.AtlasRegion> regiones = new Array<>();
        for (int i = 1; i <= 8; i++) {
            regiones.add(atlas.findRegion("basicMotion" + i));
        }
        animacion = new Animation<>(0.1f, regiones);
        tiempo = 0f;
        
        //CAMINAR IZQUIERDA
        // Crear una nueva Array para almacenar las regiones de textura invertidas
        Array<TextureAtlas.AtlasRegion> regionesInvertidas = new Array<>();

        // Duplicar y voltear horizontalmente cada región original y agregarla a la nueva Array
        for (TextureAtlas.AtlasRegion region : regiones) {
            TextureAtlas.AtlasRegion regionInvertida = new TextureAtlas.AtlasRegion(region);
            regionInvertida.flip(true, false); // Voltear horizontalmente
            regionesInvertidas.add(regionInvertida);
        }

        // Combinar las regiones originales y las invertidas para formar una nueva Array
        Array<TextureAtlas.AtlasRegion> todasLasRegiones = new Array<>();
        todasLasRegiones.addAll(regiones);
        todasLasRegiones.addAll(regionesInvertidas);

        // Crear la nueva animación usando todas las regiones
        animacionInvertida = new Animation<>(0.1f, regionesInvertidas);
    }

    public void dibujar(SpriteBatch batch, boolean miraDerecha) {
        tiempo += Gdx.graphics.getDeltaTime(); // Tiempo pasado desde el último render
        TextureRegion cuadroActual = animacion.getKeyFrame(tiempo, true);

        if (miraDerecha) {
            batch.draw(cuadroActual, x, y);
        } else {
            batch.draw(cuadroActual, x + getWidth(), y, -getWidth(), getHeight());
        }
    }
}
