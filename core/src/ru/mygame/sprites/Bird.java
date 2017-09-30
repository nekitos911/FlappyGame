package ru.mygame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import ru.mygame.Constants;

import java.util.Vector;

public class Bird {
    private Vector3 pos;
    private Vector3 vel;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;

    public Vector3 getPos() {
        return pos;
    }

    public Bird(int x,int y) {
        pos = new Vector3(x,y,0);
        vel = new Vector3(0,0,0);
        texture = new Texture("spr_b1_strip4.png");
        birdAnimation = new Animation(new TextureRegion(texture),Constants.BIRD_FRAMES_COUNT,0.5f);
        bounds = new Rectangle(x,y,texture.getWidth() / Constants.BIRD_FRAMES_COUNT,texture.getHeight() / Constants.BIRD_FRAMES_COUNT);
    }

    public void update(float dt) {
        birdAnimation.update(dt);
        if(pos.y > 0)
            vel.add(0, Constants.GRAVITY,0);
        vel.scl(dt);
        pos.add(Constants.MOVEMENT * dt,vel.y,0);
        if(pos.y < 0)
            pos.y = 0;
        vel.scl(1 / dt);
        bounds.setPosition(pos.x,pos.y);
    }

    public void jump() {
        vel.y = 300;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void dispose() {
        texture.dispose();
    }


}
