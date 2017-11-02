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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author carlos
 */
public class Player {
    
    public static final int MOVE_UP = 0;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_LEFT = 3;
    
    private float vel = 7f;
    
    private final Texture tex;
    private final Sprite sp;
    boolean movingUp, movingDown, movingRight, movingLeft, shooting, shooting2;
    Weapon weapon;
    
    private Body body;
    
    public Player(){
        tex = new Texture("playertex.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sp = new Sprite(tex);
        sp.setPosition(0, 0);
        sp.setScale(0.5f);
        
        movingUp = false;
        movingDown = false;
        movingRight = false;
        movingLeft = false;
        shooting = false;
        shooting2 = false;
        
        weapon = new Weapon(Weapon.REGULAR);
        
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sp.getX() + sp.getWidth()/2) / Scene.PIXELS_TO_METERS,
                (sp.getY() + sp.getHeight()/2) / Scene.PIXELS_TO_METERS);

        body = Scene.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sp.getWidth()/2 / Scene.PIXELS_TO_METERS, sp.getHeight()/2 / Scene.PIXELS_TO_METERS);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }
    
    public void update(){
        body.setLinearVelocity(0, 0);
        if(movingUp){move(Player.MOVE_UP);}
        if(movingDown){move(Player.MOVE_DOWN);}
        if(movingLeft){move(Player.MOVE_LEFT);}
        if(movingRight){move(Player.MOVE_RIGHT);}
        if(shooting){shoot();}
        if(shooting2){secondaryAttack();}
        
        updateRotation();
        sp.setPosition((body.getPosition().x * Scene.PIXELS_TO_METERS) - sp.getWidth()/2 ,
                (body.getPosition().y * Scene.PIXELS_TO_METERS) - sp.getHeight()/2 );
        sp.setRotation((float)Math.toDegrees(body.getAngle()));
    }
    
    public void updateRotation(){
        float px = Gdx.input.getX()/Scene.PIXELS_TO_METERS - Gdx.graphics.getWidth()/2/Scene.PIXELS_TO_METERS;
        float py = (Gdx.graphics.getHeight()-Gdx.input.getY())/Scene.PIXELS_TO_METERS - Gdx.graphics.getHeight()/2/Scene.PIXELS_TO_METERS;
        float angle = (float)( 3*Math.PI/2 + Math.atan2(py - body.getPosition().y, px - body.getPosition().x));
        body.setTransform(body.getPosition(), angle);
    }
    
    public void move(int direction){
        switch(direction){
            case MOVE_UP:
                body.setLinearVelocity(body.getLinearVelocity().x, vel);
                break;
            case MOVE_DOWN:
                body.setLinearVelocity(body.getLinearVelocity().x, -vel);
                break;
            case MOVE_RIGHT:
                body.setLinearVelocity(vel, body.getLinearVelocity().y);
                break;
            case MOVE_LEFT:
                body.setLinearVelocity(-vel, body.getLinearVelocity().y);
                break;              
        }
        
    }
    
    public void shoot(){
        weapon.shoot(body);
        
    }
    
    public void secondaryAttack(){
        // TODO
    }
    
    public void nextWeapon(){
        // TODO
    }
    
    public void prevWeapon(){
        // TODO
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){
        tex.dispose();
    }
    
    public String txtPosition(){
        return body.getPosition().x + "," + body.getPosition().y;
    }
    
}
