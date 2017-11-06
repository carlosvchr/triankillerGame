/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author carlos
 */
public class CollisionManager implements ContactListener{

    @Override
    public void beginContact(Contact ct) {
        Object objA = ct.getFixtureA().getBody().getUserData();
        Object objB = ct.getFixtureB().getBody().getUserData();
        if(objA instanceof Bullet && objB instanceof Square){
            bulletSquareCol((Bullet)objA, (Square)objB);
        }
        else if(objA instanceof Square && objB instanceof Bullet){
            bulletSquareCol((Bullet)objB, (Square)objA);
        }
        else{
            if(objA instanceof Bullet && !(objB instanceof Player)){
                Bullet b = (Bullet)objA;
                b.dispose();
                Scene.bullets.remove(b);
            }
            if(objB instanceof Bullet && !(objA instanceof Player)){
                Bullet b = (Bullet)objB;
                b.dispose();
                Scene.bullets.remove(b);
            }
        }
    }

    @Override
    public void endContact(Contact cntct) {
        
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        
    }
    
    private void bulletSquareCol(Bullet b, Square s){
        b.dispose();
        Scene.bullets.remove(b);
        s.splitSquare();
        Scene.squares.remove(s);
    }
    
}
