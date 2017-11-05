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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 *
 * @author carlos
 */
public class Bullet {
    
    private final Sprite sp;
    private final float vel;
    private final float velx, vely;
    
    private Body body;
    
    public Bullet(Vector2 position, float angle, float bulletVel){      
        sp = new Sprite(Scene.assetLoader.bullettex);
        sp.setScale(0.3f);
        this.vel = bulletVel;
        
        velx = (float)(vel * Math.cos(angle));
        vely = (float)(vel * Math.sin(angle));
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sp.getX() + sp.getWidth()*sp.getScaleX()/2) / Scene.PIXELS_TO_METERS,
                (sp.getY() + sp.getHeight()*sp.getScaleY()/2) / Scene.PIXELS_TO_METERS);
        bodyDef.bullet = true;
        
        body = Scene.world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sp.getScaleX()*sp.getWidth()/2/Scene.PIXELS_TO_METERS);
              
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.isSensor = false;

        body.createFixture(fixtureDef);
        shape.dispose();
        
        
        body.setTransform(position, body.getAngle());
        sp.setPosition((body.getPosition().x * Scene.PIXELS_TO_METERS) - sp.getWidth()/2 ,
                (body.getPosition().y * Scene.PIXELS_TO_METERS) - sp.getHeight()/2 );
        
        body.applyForceToCenter(velx, vely, true);
        body.setUserData(this);
    }
    
    public void update(){
        sp.setPosition((body.getPosition().x * Scene.PIXELS_TO_METERS) - sp.getWidth()/2 ,
                        (body.getPosition().y * Scene.PIXELS_TO_METERS) - sp.getHeight()/2 );    
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        boolean itExists = false;
        for(int i=0; i<Scene.scheduledForRemoval.size(); i++){
            if(Scene.scheduledForRemoval.get(i).equals(body)){
                itExists = true;
            }
        }
        if(!itExists){Scene.scheduledForRemoval.add(body);}
    }
    
    public Body getBody(){
        return this.body;
    }
    
    public boolean outOfField(){
        return false;
    }
}
