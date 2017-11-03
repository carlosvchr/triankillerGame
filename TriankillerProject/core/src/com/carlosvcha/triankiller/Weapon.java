/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author carlos
 */
public class Weapon {
    
    public static final int REGULAR = 0;
    public static final int FAST = 1;
    public static final int SLOW = 2;
    
    private long cooldownTime, cooldownCounter;
    private float bulletVel;
    
    public Weapon(int weapon){
        
        cooldownCounter = 0;
        
        switch(weapon){
            case REGULAR:
                cooldownTime = 200;
                bulletVel = 0.15f;
                break;
            case FAST:
                cooldownTime = 50;
                bulletVel = 0.3f;
                break;
            case SLOW:
                cooldownTime = 500;
                bulletVel = 0.05f;
                break;
        }
        
    }
    
    private boolean isReady(){
        return cooldownTime < (TimeUtils.millis() - cooldownCounter);
    }
    
    public void shoot(Vector2 position, float angle){
        if(isReady()){
            Bullet b = new Bullet(position, angle, bulletVel);
            Scene.bullets.add(b);
            cooldownCounter = TimeUtils.millis();
        }
    }
    
}
