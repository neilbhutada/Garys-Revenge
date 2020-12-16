// --== CS400 File Header Information ==--
// Name: Neil Bhutada
// Email: nbhutada@wisc.edu
// Team: MG
// Role: Front end developer 1
// TA: Harit 
// Lecturer: Florian
// Notes to Grader: Used Autoformat as adviced per TA. The size of scene is 600 x 600. 

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FrontEnd extends Application {
	Data data = new Data("Players.csv"); // creating object to load Data made by Data Wrangler
	HBox attacks = new HBox(); // Will contain all important commands
	HBox users = new HBox(); // Will contain all players
	boolean instantLoad;
	// Buttons in user HBox
	Button Prannav = new Button("Prannav");
	Button Ryan = new Button("Ryan");
	Button Najuf = new Button("Najuf");
	Button Cam = new Button("Cam");
	Button Neil = new Button("Neil");
	Button Bailey = new Button("Bailey");

	// Buttons in attack HBox
	Button flee = new Button("Flee");
	Button magic = new Button("Magic Attack");
	Button melee = new Button("Melee Attack");
	Button healer = new Button("Heal");

	WebView wb = new WebView(); // Will be used for view CSS, HTML, and JavaScript files
	WebEngine engine = wb.getEngine();

	// Main layout element
	VBox root = new VBox();

	@Override
	/**
	 * This method is the abstract class of Application This method is responsible
	 * for setting elements on the GUI.
	 */
	public void start(Stage stage) throws Exception {

		data.loadHeroes();// Loading data from CSV file

		// adding users
		users.getChildren().addAll(Prannav);
		users.getChildren().addAll(Ryan);
		users.getChildren().addAll(Najuf);
		users.getChildren().addAll(Bailey);
		users.getChildren().addAll(Cam);
		users.getChildren().addAll(Neil);

		// adding attacks
		attacks.getChildren().addAll(melee);
		attacks.getChildren().addAll(magic);
		attacks.getChildren().addAll(healer);
		attacks.getChildren().addAll(flee);

		// adding everything to main VBox
		root.getChildren().addAll(users);
		root.getChildren().addAll(wb);
		root.getChildren().addAll(attacks);

		users.setAlignment(Pos.TOP_CENTER); // Setting position of user buttons to top center
		attacks.setMargin(flee, new Insets(0, 0, 0, 300)); // Setting position of flee button to
															// rightmost corner

		Scene sc = new Scene(root, 600, 600);
		stage.setScene(sc);
		stage.show(); // Will show the frame
		engine.load(this.getClass().getResource("game.html").toString()); // Load HTML file

		// Activating attack buttons for each user
		Prannav.setOnAction((event) -> {
			instantLoad = true;
			loadAttacks("Prannav", "Image_3.png");
		});
		Ryan.setOnAction((event) -> {
			instantLoad = true;

			loadAttacks("Ryan", "Image_1.png");

		});
		Neil.setOnAction((event) -> {
			instantLoad = true;
			loadAttacks("Neil", "Image_3_x.png");

		});
		Najuf.setOnAction((event) -> {
			instantLoad = true;
			loadAttacks("Najuf", "Image_2.png");

		});

		Bailey.setOnAction((event) -> {
			instantLoad = true;
			loadAttacks("Bailey", "Image_4.png");
		});
		Cam.setOnAction((event) -> {
			instantLoad = true;
			loadAttacks("Cam", "Image_5.png");

		});

		flee.setOnAction((event) -> {
			System.exit(0);
		});
	}

	/**
	 * This method loads the player in the front end from the back end.
	 * 
	 * @param name
	 * @param imagePath
	 * @return player
	 */

	private Entity loadPlayer(String name, String imagePath) {
		Entity player = data.players.get(name);

		// Javascript code to set image and name of player in game.HTML
		engine.executeScript("updateImage("+"\""+imagePath+"\""+","+"\""+name+"\""+")");

		updateUserMeter(player.getHealthPercent());
		return player;
	}

	/**
	 * This functions loads all the attacks for each button
	 * 
	 * @param name
	 * @param imagePath
	 */

	private void loadAttacks(String name, String imagePath) {
		Entity player = loadPlayer(name, imagePath); // Retrieve player
		Entity boss = data.players.get("Gary"); // Retrieve Villian's detail
		meleeHealth(player, boss); // Setting up meleeAttack
		heal(player); // setting up Heal
		magicAttack(player, boss); // Setting up magic attack

	}

	/**
	 * Javacript code to change the value of meter tag of the Boss
	 * 
	 * @param meter
	 */

	private void updateBossMeter(float meter) {
		
		engine.executeScript("updateBossMeter("+meter+")");

	}

	/**
	 * Javascript code to change the value of the meter tag of the player
	 * 
	 * @param meter
	 */

	private void updateUserMeter(float meter) {
		
		if (instantLoad) {
			engine.executeScript("updateUserMeter("+meter+");");
			instantLoad = false;
		}
		engine.executeScript("updateUserMeterDelay("+meter+");");

	}

	/**
	 * Code to call Melee attack from the Backend
	 * 
	 * @param player
	 * @param boss
	 */
	private void meleeHealth(Entity player, Entity boss) {
		melee.setOnAction((event1) -> {

			player.meleeAttack(boss);
			updateBossMeter(boss.getHealthPercent());
			if (!boss.isAlive())
				engine.executeScript("won()");
			garyAttacks(player, boss);

		});

	}

	/**
	 * Code to call heal function from the backend
	 * 
	 * @param player
	 */

	private void heal(Entity player) {
		healer.setOnAction((event2) -> {
			try {
				player.heal(player);
				instantLoad = true;
				updateUserMeter(player.getHealthPercent());
			} catch (Exception e) { // User-defined exception in back-end
				instantLoad = true;
				updateHeader("NO HEAL LEFT");
			}
		});
	}

	/**
	 * Code to call magic Attack from backend
	 * 
	 * @param player
	 * @param boss
	 */
	private void magicAttack(Entity player, Entity boss) {
		magic.setOnAction((event) -> {
			try {
				player.magicAttack(boss);
				updateBossMeter(boss.getHealthPercent());
				garyAttacks(player, boss);
			} catch (Exception e) { // User-defined exception in backend
				instantLoad = true;
				updateHeader("NO MAGIC");

			}
			if (!boss.isAlive())
				engine.executeScript("won()");

		});
	}

	/**
	 * Javascript code to edit value of header when opponent attacks setTimeout
	 * function in Javascript is used for setting delay
	 * 
	 * @param text
	 */
	private void updateHeader(String text) {
		if (instantLoad) {
			engine.executeScript("updateHeader("+"\""+text+"\""+")");
			instantLoad = false;
		} else {
			engine.executeScript("updateHeaderDelay("+"\""+text+"\""+")");
		}
	}

	/**
	 * Call algorithm for Gary's attack in the backend and according set Frontend
	 * 
	 * @param player
	 * @param boss
	 */
	private void garyAttacks(Entity player, Entity boss) {
		try {

			String nextMove = boss.bossNextMove(player);
			if (nextMove.matches(".*heal")) { //Used Regex for matching the string, .* to take care of additional spaces or special 
											  //magic names
				instantLoad = true;
				updateBossMeter(boss.getHealthPercent());
				updateHeader("GARY'S HEALING");

			} else if (nextMove.matches(".*magic")) { //Used Regex for matching the string

				updateUserMeter(player.getHealthPercent());
				updateHeader("GARY MAGIC'ED");
			} else {
				updateUserMeter(player.getHealthPercent());
				updateHeader("GARY MELEE'ED");
			}
			if (!player.isAlive())
				engine.executeScript("lost()");

		} catch (Exception e) {// User-defined exception in the Back-end
			engine.executeScript("won()");
		}
	}

	public static void main(String args[]) throws NumberFormatException, Exception {

		launch();

	}
}
