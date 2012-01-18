package com.khangames.androidgames.mrnom;

import com.khangames.framework.Screen;
import com.khangames.framework.com.khangames.framework.impl.AndroidGame;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/17/12
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MrNomGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
