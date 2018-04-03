package core;

import core.util.Vector2;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * A class which encapsulate various information about the current state of the game
 * Collect the data from the Launcher to pass them to the GameEngine
 *
 * @author matthias
 *
 */

// TODO make inputs more general (class playerInput ?)
// also consider several players

public class GameInformation {

	/**
	 * The time in seconds it took to complete the last frame
	 */
	public float deltaTime;
	
	/**
	 * The last input of the player
	 */
	public Vector2 playerInput = Vector2.ZERO();


	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			switch(event.getCode()) {
			case LEFT:
				playerInput = Vector2.LEFT();
				break;
			case RIGHT:
				playerInput = Vector2.RIGHT();
				break;
			case UP:
				playerInput = Vector2.UP();
				break;
			case DOWN:
				playerInput = Vector2.DOWN();
				break;
			case SPACE:

				break;
			default:
				playerInput = Vector2.ZERO();
				break;
			}
		}
	};



	@Override
	public String toString() {
		return "Info [playerInput=" + playerInput + ", eventHandler=" + eventHandler + "]";
	}
	
}
