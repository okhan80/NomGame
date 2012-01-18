package com.khangames.androidgames.mrnom;

import com.khangames.framework.Game;
import com.khangames.framework.Graphics;
import com.khangames.framework.Input;
import com.khangames.framework.Screen;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/17/12
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelpScreen extends Screen {
    public HelpScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416) {
                    game.setScreen(new HelpScreen2(game));
                    if(Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.help1, 64, 100);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
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
