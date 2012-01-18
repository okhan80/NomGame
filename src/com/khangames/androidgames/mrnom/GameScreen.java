package com.khangames.androidgames.mrnom;

import android.graphics.Color;
import com.khangames.framework.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/18/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen extends Screen { 
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
    
    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";
    
    public GameScreen(Game game) {
        super(game);
        world = new World();
        
    }
    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        switch (state) {
            case Ready:
                updateReady(touchEvents);
                break;
            case Running:
                updateRunning(touchEvents, deltaTime);
                break;
            case Paused:
                updatePaused(touchEvents);
                break;
            case GameOver:
                updateGameOver(touchEvents);
                break;
        }
    }

    private void updateGameOver(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y <= 264) {
                    if(Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    private void updatePaused(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        state = GameState.Running;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            
            //  Check to see if the pause button has been pressed
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    state = GameState.Paused;
                    return;
                }
            }
            
            //  Check to see if the contorller buttons have been pressed
            if(event.type == Input.TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    world.snake.turnLeft();
                }
                if(event.x > 256 && event.y > 416) {
                    world.snake.turnRight();
                }
            }
        }
        
        //  After touch events tell world to update itself
        world.update(deltaTime);
        
        if(world.gameOver) {
            if(Settings.soundEnabled) {
                Assets.bitten.play(1);
            }
            state = GameState.GameOver;
        }
        
        if(oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            if(Settings.soundEnabled) {
                Assets.eat.play(1);
            }
        }
    }

    private void updateReady(List<Input.TouchEvent> touchEvents) {
        if(touchEvents.size() > 0) {
            state = GameState.Running;
        }
    }

    /**
     * Will first draw the background then calls the respective drawing methods for the state
     * the player is in
     * @param deltaTime - used to normalize time between devices
     */
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        
        switch (state) {
            case Ready:
                drawReadyUI();
                break;
            case Running:
                drawRunningUI();
                break;
            case Paused:
                drawPausedUI();
                break;
            case GameOver:
                drawGameOverUI();
                break;
        }

        
        drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2, g.getHeight() - 42);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for(int i = 0; i < len; i++) {
            char character = line.charAt(i);
            
            if(character == ' ') {
                x += 20;
                continue;
            }
            
            int srcX = 0;
            int srcWidth = 0;
            if(character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
            
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;

        //  Determine Pixmap for rendering the stain
        Pixmap stainPixmap = null;
        switch (stain.type) {
            case Stain.TYPE_1:
                stainPixmap = Assets.stain1;
                break;
            case Stain.TYPE_2:
                stainPixmap = Assets.stain2;
                break;
            case Stain.TYPE_3:
                stainPixmap = Assets.stain3;
                break;
        }
        
        //  Draw and center it horizontally at screen position
        int x = stain.x * 32;
        int y = stain.y * 32;
        g.drawPixmap(stainPixmap, x, y);
        
        //  Render the tail parts for the snake and draw
        int len = snake.parts.size();
        for(int i = 1; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.tail, x, y);
        }
        
        //  Determine Pixmap for direction snake is facing
        Pixmap headPixmap = null;
        switch (snake.direction) {
            case Snake.UP:
                headPixmap = Assets.headUp;
                break;
            case Snake.LEFT:
                headPixmap = Assets.headLeft;
                break;
            case Snake.DOWN:
                headPixmap = Assets.headDown;
                break;
            case Snake.RIGHT:
                headPixmap = Assets.headRight;
                break;
        }
        
        //  Center and draw the head based upon screen position
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    @Override
    public void pause() {
        if(state == GameState.Running) {
            state = GameState.Paused;
        }

        if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
