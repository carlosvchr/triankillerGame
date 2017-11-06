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

    private final Sprite sp;
    boolean movingUp, movingDown, movingRight, movingLeft, shooting, shooting2,
            turnUp, turnDown, turnRight, turnLeft;
    private Weapon weapon[];
    private int maxWeapons;
    private int currentWeapon;   
    
    
    private Body body;
    
    public Player(){
        sp = new Sprite(Scene.assetLoader.playertex);
        sp.setPosition(0, 0);
        sp.setScale(0.5f);
        
        movingUp = false;
        movingDown = false;
        movingRight = false;
        movingLeft = false;
        shooting = false;
        shooting2 = false;
        turnUp = false;
        turnDown = false;
        turnLeft = false;
        turnRight = false;
        
        maxWeapons = 3;
        
        weapon = new Weapon[maxWeapons];
        weapon[0] = new Weapon(Weapon.REGULAR);
        weapon[1] = new Weapon(Weapon.FAST);
        weapon[2] = new Weapon(Weapon.SLOW);
        
        currentWeapon = 0;
        
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sp.getX() + sp.getWidth()/2) / Scene.PIXELS_TO_METERS,
                (sp.getY() + sp.getHeight()/2) / Scene.PIXELS_TO_METERS);

        bodyDef.bullet = true;
        body = Scene.world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        Vector2 vertices[] = new Vector2[3];
        vertices[0] = new Vector2(-sp.getWidth()*sp.getScaleX()/2/Scene.PIXELS_TO_METERS, -sp.getScaleY()*sp.getHeight()/2/Scene.PIXELS_TO_METERS);
        vertices[1] = new Vector2(0, sp.getScaleY()*sp.getHeight()/2/Scene.PIXELS_TO_METERS);
        vertices[2] = new Vector2(sp.getWidth()*sp.getScaleX()/2/Scene.PIXELS_TO_METERS, -sp.getScaleY()*sp.getHeight()/2/Scene.PIXELS_TO_METERS);
        shape.set(vertices);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1000f;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
        
        body.setUserData(this);
    }
    
    public void update(){
        body.setLinearVelocity(0, 0);
        if(movingUp){move(Player.MOVE_UP);}
        if(movingDown){move(Player.MOVE_DOWN);}
        if(movingLeft){move(Player.MOVE_LEFT);}
        if(movingRight){move(Player.MOVE_RIGHT);}
        if(shooting){shoot();}
        if(shooting2){secondaryAttack();}
        if(turnUp && turnRight){
            body.setTransform(body.getPosition(), (float) Math.toRadians(315));
        }else if(turnUp && turnLeft){
            body.setTransform(body.getPosition(), (float) Math.toRadians(45));
        }else if(turnUp){
            body.setTransform(body.getPosition(), (float) Math.toRadians(0));
        }else if(turnDown && turnRight){
            body.setTransform(body.getPosition(), (float) Math.toRadians(225));
        }else if(turnDown && turnLeft){
            body.setTransform(body.getPosition(), (float) Math.toRadians(135));
        }else if(turnDown){
            body.setTransform(body.getPosition(), (float) Math.toRadians(180));
        }else if(turnRight){
            body.setTransform(body.getPosition(), (float) Math.toRadians(270));
        }else if(turnLeft){
            body.setTransform(body.getPosition(), (float) Math.toRadians(90));
        }
        
        sp.setPosition((body.getPosition().x * Scene.PIXELS_TO_METERS) - sp.getWidth()/2 ,
                (body.getPosition().y * Scene.PIXELS_TO_METERS) - sp.getHeight()/2 );
        sp.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    
    public void updateRotation(float angle){
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
        float px = body.getPosition().x + sp.getWidth()/2*sp.getScaleX()/Scene.PIXELS_TO_METERS * (float)Math.cos(Math.PI/2+body.getAngle());
        float py = body.getPosition().y + sp.getHeight()/1.5f*sp.getScaleY()/Scene.PIXELS_TO_METERS * (float)Math.sin(Math.PI/2+body.getAngle());

        weapon[currentWeapon].shoot(new Vector2(px, py), (float)Math.PI/2 + body.getAngle());
        
    }
    
    public void secondaryAttack(){
        // TODO
    }
    
    public void nextWeapon(){
        currentWeapon += 1;
        if(currentWeapon >= maxWeapons){currentWeapon = 0;}
    }
    
    public void prevWeapon(){
        currentWeapon -= 1;
        if(currentWeapon < 0){currentWeapon = maxWeapons-1;}
    }
    
    public void render(SpriteBatch batcher){
        sp.draw(batcher);
    }
    
    public void dispose(){

    }
    
    public String txtPosition(){
        return body.getPosition().x + "," + body.getPosition().y;
    }
    
}
