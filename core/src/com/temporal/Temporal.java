package com.temporal;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Temporal implements ApplicationListener
{
    Texture playerTex;
    //Sound dropSound;
    //Music rainMusic;
    SpriteBatch batch;
    OrthographicCamera camera;
    Rectangle player;
    Array<Rectangle> raindrops;
    long lastDropTime;

    @Override
    public void create()
    {
        // load the images for the droplet and the player, 64x64 pixels each
        playerTex = new Texture(Gdx.files.internal("player.png"));

        // load the drop sound effect and the rain background "music"
        /*
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        */
        /*
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();
        */
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        batch = new SpriteBatch();

        // create a Rectangle to logically represent the player
        /*
        player = new Rectangle();
        player.x = 800 / 2 - 64 / 2; // center the player horizontally
        player.y = 20; // bottom left corner of the player is 20 pixels above the bottom screen edge
        player.width = 64;
        player.height = 64;
        */
        
        player = new Rectangle();
        player.x = 800 / 2 - 64 / 2;
        player.y = 480 / 2;
        player.width = 64;
        player.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        //spawnRaindrop();
    }
    /*
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    */

    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the player and
        // all drops
        batch.begin();
        batch.draw(playerTex, player.x, player.y);
        /*
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        */
        batch.end();

        // process user input (mouse input)
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            player.x = touchPos.x - 64 / 2;
        }
        
        float dt = Gdx.graphics.getDeltaTime();
        float velocity = 200 * dt;
        
        if (Gdx.input.isKeyPressed(Keys.LEFT))
        {
            player.x -= velocity;
        }
        
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            player.x += velocity;
        }
        
        if (Gdx.input.isKeyPressed(Keys.UP))
        {
            player.y += velocity;
        }
        
        if (Gdx.input.isKeyPressed(Keys.DOWN))
        {
            player.y -= velocity;
        }

        // make sure the player stays within the screen bounds
        if(player.x < 0) player.x = 0;
        if(player.x > 800 - 64) player.x = 800 - 64;

        /*
        // check if we need to create a new raindrop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        */

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the player. In the later case we play back
        // a sound effect as well.
        /*
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();
            if(raindrop.overlaps(player)) {
                //dropSound.play();
                iter.remove();
            }
        }
        */
    }

    @Override
    public void dispose() {
        // dispose of all the native resources3
        playerTex.dispose();
        //dropImage.dispose();
        //playerImage.dispose();
        //dropSound.dispose();
        //rainMusic.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
