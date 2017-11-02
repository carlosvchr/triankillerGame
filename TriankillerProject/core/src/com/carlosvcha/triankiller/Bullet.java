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
    
    private final Texture tex;
    private final Sprite sp;
    private final float vel = 1000;
    private final float velx, vely;
    
    private Body body;
    
    public Bullet(Vector2 position, float angle){
        tex = new Texture("bullet01tex.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sp = new Sprite(tex);
        sp.setScale(0.3f);
        sp.setPosition(position.x, position.y);
        velx = (float)(vel * Math.cos(angle))/Scene.PIXELS_TO_METERS;
        vely = (float)(vel * Math.sin(angle))/Scene.PIXELS_TO_METERS;
        
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
    }
    
    public void update(){
        body.setLinearVelocity(velx, vely);
        sp.setPosition(body.getPosition().x * Scene.PIXELS_TO_METERS, body.getPosition().y * Scene.PIXELS_TO_METERS);
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        tex.dispose();
    }
    
    public Body getBody(){
        return this.body;
    }
    
    public boolean outOfField(){
        return (sp.getX() < -(Gdx.graphics.getHeight()/2 + sp.getWidth())) ||
               (sp.getY() < -(Gdx.graphics.getHeight()/2 + sp.getHeight())) ||
               (sp.getX() > Gdx.graphics.getWidth()/2) ||
               (sp.getY() > Gdx.graphics.getHeight()/2);
    }
}
