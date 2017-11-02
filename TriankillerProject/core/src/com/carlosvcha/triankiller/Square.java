/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author carlos
 */
public class Square {
    
    private final Texture tex;
    private final Sprite sp;
    private float rotVel;
    private float vel;
    private float dir;
    
    public Square(Vector2 pos, float vel, float direction, float rotVel){
        tex = new Texture("square01tex.png");
        sp = new Sprite(tex);
        sp.setPosition(pos.x, pos.y);
        this.rotVel = rotVel;
        this.vel = vel;
        this.dir = direction;
    }
    
    public void update(){
        sp.setPosition(sp.getX() + vel*(float)Math.cos(Math.toRadians(dir))*Gdx.graphics.getDeltaTime(),
                sp.getY() + vel*(float)Math.sin(Math.toRadians(dir))*Gdx.graphics.getDeltaTime());
        sp.rotate(rotVel*Gdx.graphics.getDeltaTime());
        
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        tex.dispose();
    }
    
}
