/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author carlos
 */
public class AssetLoader {
 
    public TextureRegion playertex, walltex, bullettex;
    public TextureRegion[] squaretex;
    public float textureScale;
    Texture mainTexture;
    
    public AssetLoader(){
        mainTexture = new Texture("sprites_tex.png");
        mainTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playertex = new TextureRegion(mainTexture, 0, 0, 64, 64);
        walltex = new TextureRegion(mainTexture, 65, 0, 64, 64);
        bullettex = new TextureRegion(mainTexture, 128, 32, 32, 32);
        squaretex = new TextureRegion[6];
        squaretex[5] = new TextureRegion(mainTexture, 160, 0, 200, 200);
        squaretex[4] = new TextureRegion(mainTexture, 360, 0, 150, 150);
        squaretex[3] = new TextureRegion(mainTexture, 510, 0, 110, 110);
        squaretex[2] = new TextureRegion(mainTexture, 620, 0, 80, 80);
        squaretex[1] = new TextureRegion(mainTexture, 700, 0, 55, 55);
        squaretex[0] = new TextureRegion(mainTexture, 755, 0, 35, 35);
    }
    
    public void dispose(){
        mainTexture.dispose();
    }
}
