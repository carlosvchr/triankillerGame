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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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
    private Body body;
    private int level;
    
    public Square(Vector2 pos, float vel, float direction, float rotVel, int level){
        tex = new Texture("square01tex.png");
        sp = new Sprite(tex);
        //sp.setScale(level/10);
        this.level = level;
        
        this.rotVel = rotVel;
        this.vel = vel;
        this.dir = direction;
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sp.getX() + sp.getWidth()/2) / Scene.PIXELS_TO_METERS,
                (sp.getY() + sp.getHeight()/2) / Scene.PIXELS_TO_METERS);

        bodyDef.bullet = true;
        body = Scene.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sp.getWidth()*sp.getScaleX()/2/Scene.PIXELS_TO_METERS, sp.getScaleY()*sp.getHeight()/2/Scene.PIXELS_TO_METERS);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);
        shape.dispose();
        
        body.applyForceToCenter(new Vector2((float)Math.random()*80-40, (float)Math.random()*60-30), true);
        body.applyAngularImpulse((float)Math.random()*0.1f, true);
        
        body.setUserData(this);
    }
    
    public void update(){
        sp.setPosition((body.getPosition().x * Scene.PIXELS_TO_METERS) - sp.getWidth()/2 ,
                (body.getPosition().y * Scene.PIXELS_TO_METERS) - sp.getHeight()/2 );
        sp.setRotation((float)Math.toDegrees(body.getAngle()));
        
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        tex.dispose();
        Scene.world.destroyBody(body);
    }
    
    public void splitSquare(){
        /*this.dispose();
        Scene.squares.remove(this);
        float velocity = (float) Math.sqrt(Math.pow(body.getLinearVelocity().x,2)+Math.pow(body.getLinearVelocity().y, 2));
        Square sq = new Square(body.getPosition(), velocity, 90, body.getAngularVelocity(), level);
        Square sq2 = new Square(body.getPosition(), velocity, 180, body.getAngularVelocity(), level);
        Scene.squares.add(sq);
        Scene.squares.add(sq2);
        Scene.squares.trimToSize();*/
    }
    
    
}
