/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author carlos
 */
public class Wall {
       
    Vector2 position;
    Texture tx;
    Sprite sp;
    
    public Wall(int type, Vector2 position){
        tx = new Texture("walltex.png");
        sp = new Sprite(tx);
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        tx.dispose();
    }
    
}
