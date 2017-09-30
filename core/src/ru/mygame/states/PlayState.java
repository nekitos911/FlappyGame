package ru.mygame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ru.mygame.Constants;
import ru.mygame.sprites.Bird;
import ru.mygame.sprites.Tube;

import javax.swing.plaf.TableUI;

public class PlayState extends State {
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1,groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,150);
        cam.setToOrtho(false, Constants.WIDTH / 2,Constants.HEIGHT / 2);
        bg = new Texture("bg1.png");
        ground = new Texture("spr_earth.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2,Constants.GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(),Constants.GROUND_Y_OFFSET);

        tubes = new Array<Tube>();

        for (int i = 0; i < Constants.TUBE_COUNT ; i++) {
            tubes.add(new Tube((i + 1) * (Constants.TUBE_SPACING + Constants.TUBE_WIDTH )));

        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPos().x + 100;

        for (int i = 0;i < tubes.size;i++) {
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (Constants.TUBE_WIDTH + Constants.TUBE_SPACING) * Constants.TUBE_COUNT);
            }
            if(tube.colision(bird.getBounds()))
                gsm.set(new PlayState(gsm));
        }
        if(bird.getPos().y <= ground.getHeight() + Constants.GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));
        cam.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(bg,cam.position.x - cam.viewportWidth / 2,0);
        batch.draw(bird.getTexture(),bird.getPos().x,bird.getPos().y);
        for (Tube tube : tubes) {
            batch.draw(tube.getTopTube(),tube.getPosTopTube().x,tube.getPosTopTube().y);
            batch.draw(tube.getBottomTube(),tube.getPosBotTube().x,tube.getPosBotTube().y);
        }
        batch.draw(ground,groundPos1.x,groundPos1.y);
        batch.draw(ground,groundPos2.x,groundPos2.y);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }

    private void updateGround() {
        if(cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2,0);
        if(cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2,0);
    }
}
