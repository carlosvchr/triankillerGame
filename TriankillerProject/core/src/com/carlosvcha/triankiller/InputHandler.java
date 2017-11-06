/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author carlos
 */
public class InputHandler implements InputProcessor{
    
    Player player;
    
    // Variables para joystick de orientacion con sensibilidad regulable
    Vector2 fixedPos;
    int radiusJoystick;
    
    public InputHandler(Player player){
        this.player = player;
        fixedPos = new Vector2(0,0);
        radiusJoystick = 30;
    }
    

    @Override
    public boolean keyDown(int i) {
        if(i == Input.Keys.W){player.movingUp = true;}
        
        if(i == Input.Keys.S){ player.movingDown = true;}
        
        if(i == Input.Keys.D){player.movingRight = true;}
        
        if(i == Input.Keys.A){player.movingLeft = true;}
        
        if(i == Input.Keys.UP){player.turnUp = true;}
        
        if(i == Input.Keys.DOWN){player.turnDown = true;}
        
        if(i == Input.Keys.RIGHT){player.turnRight = true;}
        
        if(i == Input.Keys.LEFT){player.turnLeft = true;}
        
        if(i == Input.Keys.SPACE){player.shooting = true;}
        
        if(i == Input.Keys.Q){player.prevWeapon();}
        
        if(i == Input.Keys.E){player.nextWeapon();}
        
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        if(i == Input.Keys.W){player.movingUp = false;}
        
        if(i == Input.Keys.S){ player.movingDown = false;}
        
        if(i == Input.Keys.D){player.movingRight = false;}
        
        if(i == Input.Keys.A){player.movingLeft = false;}
        
        if(i == Input.Keys.UP){player.turnUp = false;}
        
        if(i == Input.Keys.DOWN){player.turnDown = false;}
        
        if(i == Input.Keys.RIGHT){player.turnRight = false;}
        
        if(i == Input.Keys.LEFT){player.turnLeft = false;}
        
        if(i == Input.Keys.SPACE){player.shooting = false;}
        
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){player.shooting2 = true;}
        
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){player.shooting = true;}

        System.out.println(player.shooting);
        
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        
        if(!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){player.shooting2 = false;}
        
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){player.shooting = false;}

        return true;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        mouseMoved(i, i1);
        return true;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        Vector2 newPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        
        if(newPos.dst(fixedPos) > radiusJoystick){ 
            float angle = (float) Math.atan2(newPos.y - fixedPos.y, fixedPos.x - newPos.x);
            player.updateRotation((float) (Math.PI/2 + angle));
            float ax = (float) (Math.cos(angle) * radiusJoystick);
            float ay = (float) (Math.sin(angle) * radiusJoystick);
            
            fixedPos.x = newPos.x + ax;
            fixedPos.y = newPos.y - ay;
            
        }    
        
        return true;
    }

    @Override
    public boolean scrolled(int i) {
        
        if(i == 1){
            player.nextWeapon();
        }
        
        if(i == -1){
            player.prevWeapon();
        }
        
        return true;
    }
    
}
