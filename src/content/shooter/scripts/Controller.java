package content.shooter.scripts;

import content.Layer;
import content.MonoBehaviour;
import content.Tag;
import content.platformer.Bullet;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Annex;
import core.util.MutableFloat;
import core.util.Vector2;

/**
 * The controller script which manages the movement of the GameObject
 * 
 * @author Raph
 *
 */
public class Controller extends MonoBehaviour {

	/**
	 * The default move speed of the GameObject  
	 */
	public static float defaultMoveSpeed = 500f;

	/**
	 * The speed of the GameObject when dashing
	 */
	public static float dashSpeed = 3000f;
	
	/**
	 * The time required to reach the target velocity when starting with a null velocity
	 * (set 0 for no inertia)
	 */
	public static float accelerationTime = 0f;

	public static float fireCooldown = 0.5f;		// the time between two shots
	private float timeBeforeShoot = 0f;


	Vector2 velocity = Vector2.ZERO();
	MutableFloat velocityXSmoothing = new MutableFloat(0f);		// Used for the smoothing of the horizontal velocity
	MutableFloat velocityYSmoothing = new MutableFloat(0f);		// Used for the smoothing of the vertical velocity

	boolean isDashing;



	/* Constructor */
	public Controller() {}



	@Override
	public void start() {
		
	}

	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {

		if (!isDashing && playerInput.spacePressed) {
			isDashing = true;
		}
		calculateVelocity(deltaTime, playerInput.directionalInput);


		if (playerInput.aPressed && timeBeforeShoot <= 0) {
			shootBullet(playerInput.directionalInput, deltaTime);
			timeBeforeShoot = fireCooldown;
		}
		timeBeforeShoot -= deltaTime;


		// Moving
		support.position.translate(velocity.multiply(deltaTime));
	}


	/**
	 * Calculate the velocity of the player for this frame according to its input.
	 */
	void calculateVelocity(float deltaTime, Vector2 directionalInput) {
		float targetVelocityX = (isDashing ? dashSpeed : defaultMoveSpeed) * directionalInput.x;
		float targetVelocityY = (isDashing ? dashSpeed : defaultMoveSpeed) * directionalInput.y;
		velocity.x = Annex.SmoothDamp(velocity.x, targetVelocityX, velocityXSmoothing, accelerationTime, deltaTime);
		velocity.y = Annex.SmoothDamp(velocity.y, targetVelocityY, velocityYSmoothing, accelerationTime, deltaTime);
	}

	void shootBullet(Vector2 directionalInput, float deltaTime) {
		support.gameEngine.allGameObjects.add(new Bullet(support.position.add(directionalInput.multiply(64f)), 
				Layer.DEFAULT, 
				Tag.SOLID, 
				directionalInput, 
				200f * deltaTime, 
				support.gameEngine));
	}


}
