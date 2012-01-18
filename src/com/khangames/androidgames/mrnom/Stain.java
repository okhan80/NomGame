package com.khangames.androidgames.mrnom;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/18/12
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Stain {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    
    public int x, y;
    public int type;

    public Stain(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

    }
    
}
