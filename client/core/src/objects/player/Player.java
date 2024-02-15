package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constants.*;

public class Player extends GameEntity {
    private final float speed;

    private int jumpCounter;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = PLAYER_SPEED;
        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        handleInput();
    }

    private void handleInput() {
        velX = 0;
        // Moving right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        // Moving left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }

        // Jumping
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && jumpCounter < JUMP_COUNT) {
            float force = body.getMass() * JUMP_FORCE;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getWorldCenter(), true);
            jumpCounter++;
        }

        // reset jump counter
        if (body.getLinearVelocity().y == 0) {
            jumpCounter = 0;
        }

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    public Vector2 getPosition() {
        return body.getPosition().scl(PPM);
    }

    public boolean isOutOfBounds(Vector2 center) {
        return Math.abs(center.x - x) > BOUNDS || Math.abs(center.y - y) > BOUNDS;
    }
}
