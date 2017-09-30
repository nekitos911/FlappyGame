package ru.mygame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mygame.Constants;

import java.util.Random;
import java.util.Vector;

public class Tube {
    private Texture topTube,bottomTube;
    private Vector2 posTopTube,posBotTube;
    private Rectangle boundsTop,boundsBot;
    private Random rand;

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x,rand.nextInt(Constants.TUBE_OFFSET) + Constants.TUBE_GAP + Constants.LOWEST_OPENING);
        posBotTube = new Vector2(x,posTopTube.y - Constants.TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x,posTopTube.y,topTube.getWidth(),topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x,posBotTube.y,bottomTube.getWidth(),bottomTube.getHeight());
    }

    public void reposition(float x) {
        posTopTube.set(x,rand.nextInt(Constants.TUBE_OFFSET) + Constants.TUBE_GAP + Constants.LOWEST_OPENING);
        posBotTube.set(x,posTopTube.y - Constants.TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x,posTopTube.y);
        boundsBot.setPosition(posBotTube.x,posBotTube.y);
    }

    public boolean colision(Rectangle bird) {
        return bird.overlaps(boundsTop) || bird.overlaps(boundsBot);
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
