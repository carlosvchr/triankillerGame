/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author carlos
 */
public class Weapon {
    
    public static final int REGULAR = 0;
    private long cooldownTime, cooldownCounter;
    private float lastShoot;
    
    
    public Weapon(int weapon){
        
        lastShoot = 10000;
        cooldownCounter = 0;
        
        switch(weapon){
            case REGULAR:
                cooldownTime = 100;
        }
    }
    
    private boolean isReady(){
        return cooldownTime < (TimeUtils.millis() - cooldownCounter);
    }
    
    public void shoot(Body player){
        if(isReady()){
            Bullet b = new Bullet(player.getPosition(), 90 + player.getTransform().getRotation());
            Scene.bullets.add(b);
            cooldownCounter = TimeUtils.millis();
        }
    }
    
}
