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
    static ArrayList<Wall> walls;
    static ArrayList<Square> squares;
    
    static ArrayList<Body> scheduledForRemoval;
    static ArrayList<Square> scheduledSquaresForAddition;
    static AssetLoader assetLoader;
    
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
    Body bodyEdgeScreen, bodyEdgeScreen2, bodyEdgeScreen3, bodyEdgeScreen4;
    
    @Override
    public void create () {
        world = new World(new Vector2(0, 0),true);
        world.setContactListener(new CollisionManager());
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());      
        assetLoader = new AssetLoader();        
        
        setFieldLimits();
        
        player = new Player();
        bullets = new ArrayList<Bullet>();
        walls = new ArrayList<Wall>();
        squares = new ArrayList<Square>();
        
        inputHandler = new InputHandler(player);
        InputHandler inputProcessor = new InputHandler(player);
        Gdx.input.setInputProcessor(inputProcessor);

        batch = new SpriteBatch();

        scheduledForRemoval = new ArrayList<Body>();
        scheduledSquaresForAddition = new ArrayList<Square>();
    }
    
    public void setFieldLimits(){
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        groundDef.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(groundDef);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();
        
        BodyDef groundDef2 = new BodyDef();
        groundDef2.type = BodyDef.BodyType.StaticBody;
        float w2 = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        float h2 = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        groundDef2.position.set(0,0);
        FixtureDef fixtureDef22 = new FixtureDef();

        EdgeShape edgeShape2 = new EdgeShape();
        edgeShape2.set(-w2/2,h2/2,w2/2,h2/2);
        fixtureDef22.shape = edgeShape2;

        bodyEdgeScreen = world.createBody(groundDef2);
        bodyEdgeScreen.createFixture(fixtureDef22);
        edgeShape2.dispose();
        
        BodyDef groundDef3 = new BodyDef();
        groundDef3.type = BodyDef.BodyType.StaticBody;
        float w3 = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        float h3 = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        groundDef3.position.set(0,0);
        FixtureDef fixtureDef23 = new FixtureDef();

        EdgeShape edgeShape3 = new EdgeShape();
        edgeShape3.set(w3/2,-h3/2,w3/2,h3/2);
        fixtureDef23.shape = edgeShape2;

        bodyEdgeScreen = world.createBody(groundDef3);
        bodyEdgeScreen.createFixture(fixtureDef23);
        edgeShape3.dispose();
        
        BodyDef groundDef4 = new BodyDef();
        groundDef4.type = BodyDef.BodyType.StaticBody;
        float w4 = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        float h4 = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        groundDef4.position.set(0,0);
        FixtureDef fixtureDef24 = new FixtureDef();

        EdgeShape edgeShape4 = new EdgeShape();
        edgeShape4.set(-w4/2,-h4/2,-w4/2,h4/2);
        fixtureDef24.shape = edgeShape2;

        bodyEdgeScreen = world.createBody(groundDef4);
        bodyEdgeScreen.createFixture(fixtureDef24);
        edgeShape4.dispose();
    }

    @Override
    public void render () {
        camera.update();
        world.step(1f/60f, 6, 2);
        for(int i=0; i<scheduledForRemoval.size(); i++){
            world.destroyBody(scheduledForRemoval.get(i));
        }
        scheduledForRemoval.clear();
        
        for(int i=0; i<scheduledSquaresForAddition.size(); i++){
            scheduledSquaresForAddition.get(i).create();
        }
        scheduledSquaresForAddition.clear();

        Gdx.gl.glClearColor(0, 0, 0, 1);
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
            System.out.println("FPS:"+Gdx.graphics.getFramesPerSecond()+"\tBullets:" + bullets.size()+
                    "\tSquares:"+squares.size());
        }   
    }

    private void genSquares(){
        squares.add(new Square(new Vector2(0,0), 5, 180, 200, 5));
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
        assetLoader.dispose();
    }
}
