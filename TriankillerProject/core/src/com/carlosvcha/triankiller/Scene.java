package com.carlosvcha.triankiller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

public class Scene extends ApplicationAdapter {
    
    static ArrayList<Bullet> bullets;
    ArrayList<Wall> walls;
    ArrayList<Square> squares;
    
    private final boolean DEBUG = true;
    
    SpriteBatch batch;
    Player player;
    InputHandler inputHandler;
    
    // Box2D variables
    public static World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    public static final float PIXELS_TO_METERS = 100f;
    Body bodyEdgeScreen;
    
    @Override
    public void create () {
        world = new World(new Vector2(0, 0),true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());       
        setFieldLimits();
        
        player = new Player();
        bullets = new ArrayList<Bullet>();
        walls = new ArrayList<Wall>();
        squares = new ArrayList<Square>();
        
        inputHandler = new InputHandler(player);
        InputHandler inputProcessor = new InputHandler(player);
        Gdx.input.setInputProcessor(inputProcessor);

        batch = new SpriteBatch();

    }
    
    public void setFieldLimits(){
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        groundDef.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(groundDef);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();
    }

    @Override
    public void render () {
        camera.update();
        world.step(1f/60f, 6, 2);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        update();
        
        batch.setProjectionMatrix(camera.combined);
        
        if(DEBUG){
            debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
        }
        
        batch.begin();
        player.render(batch);
        for(int i=0; i<bullets.size(); i++){
            bullets.get(i).render(batch);
        }
        for(int i=0; i<squares.size(); i++){
            squares.get(i).render(batch);
        }
        batch.end();
        
        if(DEBUG){
            debugRenderer.render(world, debugMatrix);
        }
    }
    
    private void update(){
        player.update();
        for(int i=0; i<bullets.size(); i++){
            bullets.get(i).update();
            if(bullets.get(i).outOfField()){
                bullets.get(i).dispose();
                Scene.world.destroyBody(bullets.get(i).getBody());
                bullets.remove(i);
            }
        }
        bullets.trimToSize();
        
        for(int i=0; i<squares.size(); i++){
            squares.get(i).update();
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            genSquares();
        }
        
        if(DEBUG){
            System.out.println("FPS:"+Gdx.graphics.getFramesPerSecond()+"\tBullets:" + bullets.size());
        }   
    }

    private void genSquares(){
        Square sq = new Square(new Vector2(0,0), 50, 180, 200);
        squares.add(sq);
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        player.dispose();
        for(int i=0; i<bullets.size(); i++){
            bullets.get(i).dispose();
        }
        for(int i=0; i<walls.size(); i++){
            walls.get(i).dispose();
        }
        for(int i=0; i<squares.size(); i++){
            squares.get(i).dispose();
        }
        bullets.clear();
        walls.clear();
        squares.clear();
    }
}
