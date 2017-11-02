/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosvcha.triankiller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 *
 * @author carlos
 */
public class InputHandler implements InputProcessor{
    
    Player player;
    
    
    public InputHandler(Player player){
        this.player = player;
    }
    

    @Override
    public boolean keyDown(int i) {
        if(i == Input.Keys.UP || i == Input.Keys.W){player.movingUp = true;}
        
        if(i == Input.Keys.DOWN || i == Input.Keys.S){ player.movingDown = true;}
        
        if(i == Input.Keys.RIGHT || i == Input.Keys.D){player.movingRight = true;}
        
        if(i == Input.Keys.LEFT || i == Input.Keys.A){player.movingLeft = true;}
        
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        if(i == Input.Keys.UP || i == Input.Keys.W){player.movingUp = false;}
        
        if(i == Input.Keys.DOWN || i == Input.Keys.S){ player.movingDown = false;}
        
        if(i == Input.Keys.RIGHT || i == Input.Keys.D){player.movingRight = false;}
        
        if(i == Input.Keys.LEFT || i == Input.Keys.A){player.movingLeft = false;}
        
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
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        player.updateRotation();
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
