package com.khangames.androidgames.mrnom;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/18/12
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class World { 
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;
    
    public Snake snake;
    public Stain stain;
    public boolean gameOver = false;
    public int score = 0;
    
    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;

    public World() {
        snake = new Snake();
        placeStain();
    }

    /**
     * Used to randomly place the stain on the field while avoiding locations that contain snake parts
     * This will utilize a 2D array of booleans that flag whether a location is occupied by parts of a snake
     */
    private void placeStain() {
        //  Clearing the cell array
        for(int x = 0; x < WORLD_WIDTH; x++) {
            for(int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }
        
        //  Setting the fields that contain snake parts to true
        int len = snake.parts.size();
        for(int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }
        
        //  Scan array for free cell starting at random position
        //  Once found, create Stain with random type
        int stainX = random.nextInt(WORLD_WIDTH);
        int stainY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if(fields[stainX][stainY] == false) {
                break;
            }
            stainX += 1;
            if(stainX >= WORLD_WIDTH) {
                stainX = 0;
                stainY += 1;
                if(stainY >= WORLD_HEIGHT) {
                    stainY = 0;
                }
            }
        }
        stain = new Stain(stainX, stainY, random.nextInt(3));
    }

    /**
     * Updating the world and all the objects in it based upon delta time
     * Method will be called each frame in the game screen
     * @param deltaTime - normalize time between all types of devices
     */
    public void update(float deltaTime) {

        if(gameOver) {
            return;
        }
        
        tickTime += deltaTime;

        //  Use up as many ticks that have been accumulated
        //  Also known as a fixed-time-step simulation
        while(tickTime > tick) {
            tickTime -= tick;
            snake.advance();

            //  Check to see if player has bitten themselves
            if(snake.checkBitten()) {
                gameOver = true;
                return;
            }

            //  Check to see if the head is in same cell as the stain
            SnakePart head = snake.parts.get(0);
            if(head.x == stain.x && head.y == stain.y) {
                score += SCORE_INCREMENT;
                snake.eat();

                //  If snake takes up as many spots as in the world, then end the game
                //  Otherwise generate new stain
                if(snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                } else {
                    placeStain();
                }

                //  If 10 stains have been eaten, then increase the speed at which the snake moves
                if(score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}
