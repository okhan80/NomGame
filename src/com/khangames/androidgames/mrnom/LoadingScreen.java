package com.khangames.androidgames.mrnom;

import com.khangames.framework.Game;
import com.khangames.framework.Graphics;
import com.khangames.framework.Screen;


/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/17/12
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        
        Assets.background = g.newPixmap("graphics/background.png", Graphics.PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("graphics/logo.png", Graphics.PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("graphics/mainmenu.png", Graphics.PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("graphics/buttons.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("graphics/help1.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("graphics/help2.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("graphics/help3.png", Graphics.PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("graphics/numbers.png", Graphics.PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("graphics/ready.png", Graphics.PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("graphics/pausemenu.png", Graphics.PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("graphics/gameover.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("graphics/headdown.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("graphics/headleft.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("graphics/headright.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headUp = g.newPixmap("graphics/headup.png", Graphics.PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("graphics/ready.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("graphics/stain1.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("graphics/stain2.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("graphics/stain3.png", Graphics.PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("graphics/tail.png", Graphics.PixmapFormat.ARGB4444);

        Assets.bitten = game.getAudio().newSound("soundeffects/bitten.ogg");
        Assets.click = game.getAudio().newSound("soundeffects/click.ogg");
        Assets.eat = game.getAudio().newSound("soundeffects/eat.ogg");
        
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
        
    }

    @Override
    public void present(float deltaTime) {
        //To change body of implemented methods use File | Settings | File Templates.
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
