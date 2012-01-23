package com.khangames.androidgames.mrnom;

import com.khangames.framework.FileIO;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/17/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    public static boolean soundEnabled = true;
    public static int[] highscores = new int[] { 100, 80, 50, 30, 10};
    
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".mrnom")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for(int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException ex) {
            //  use defaults
        } catch (NumberFormatException ex) {
            //  use defaults
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                
            }
        }
    }
    
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".mrnom")));
            out.write(Boolean.toString(soundEnabled));
            for(int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
            }
        } catch (IOException e) {
            
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                
            }
        }
    }
    
    public static void addScore(int score) {
        for(int i = 0; i < 5; i++) {
            if(highscores[i] < score) {
                for(int j = 4; j > i; j--) {
                    highscores[j] = highscores[j - 1];
                }
                highscores[i] = score;
                break;
            }
        }
    }
}
