/*
 * GameWindow
 * @author 		Ted Lim
 * @date		January 22
 * @version
 * This class runs the client game and draws all graphics based on data received from the server; 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.sound.sampled.*;
import java.io.*;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 5446208496818554929L;

	private JFrame frame; // create jframe object
	protected static final int WIDTH = 1280; // constant for width
	protected static final int HEIGHT = 720; // constant for height


	public GameWindow() {
		frame = new JFrame("Cattle Smashers"); // initialize new frame
		frame.setSize(WIDTH, HEIGHT); // set size to width and height
		frame.setResizable(false); // set resizable to false
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation
		frame.setLocationRelativeTo(null); // open frame in middle of screen
		frame.getContentPane().add(new GamePanel()); // add game panel to frame
		frame.pack(); // pack frame
		frame.setVisible(true); // set visible to true
	}


	class GamePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable {

		private static final long serialVersionUID = -7553463863957154757L;

		private Socket socket; // declare socket object
		private BufferedReader input; // declare bufferedreader object for input from server
		private PrintWriter output; // declare printwriter object for output to server
		private int clientNum; // declare int variable to store the client number (corresponds to player number ie player1 player2..)

		private String inputMsg; // declare string to store string of data from server


		private String frameRate; // string to store the framerate
		private long deltaTime; // long variable to store the time passed
		private long lastTimeCheck; // long variable to store the time of last check
		private int frameCount; // int variable to store the frame count

		private boolean running; // boolean to keep track of wether game is running


		private int gameState = 0; //0 mainmenu; 1 instructions; 2 Options; 3 lobby; 4 game

		private MenuScreen menuScreen; //  MenuScreen object
		private InstructionsScreen instrScreen; //  InstructionsScreen object
		private OptionScreen optionsScreen; //  OptionScreen object
		private CharacterSelectionScreen characterSelectionScreen; // character selection screen
		private GameScreen gameScreen; // game screen object
		//private PlayerHud playerHud; // hud for health/mana
		private EndScreen endScreen; // end screen for game over
		private ArrayList<Player> playerList = new ArrayList<Player>(); // arraylist to store players
		private ArrayList<Minion> enemyList = new ArrayList<Minion>(); // arraylist to store enemies (parameterized as player for now)

		// boolean variables to keep track of key press for all movement and attack keys
		private boolean upKey;
		private boolean downKey;
		private boolean leftKey;
		private boolean rightKey;
		private boolean attKey;

		private boolean connected; // boolean for server connection
		private boolean win; // boolean for game over		
		private int score; // int for score

		//fonts
		private Font scoreFont = new Font("Sans Serif", Font.BOLD, 50);
		private Font fpsFont = new Font("Sans Serif", Font.BOLD, 15);

		public GamePanel() {
			setPreferredSize(new Dimension(GameWindow.WIDTH, GameWindow.HEIGHT)); // set prefered size
			setFocusable(true); // set focusable to true
			requestFocusInWindow(); // request focus
			addMouseListener(this); // add mouse listener
			addMouseMotionListener(this); // add mousemotionlistener
			addKeyListener(this); // add keylistener

			menuScreen = new MenuScreen(); // initialize menu screen
			optionsScreen = new OptionScreen(); // initialize option screen
			instrScreen = new InstructionsScreen(); // initialize instructions screen
			characterSelectionScreen = new CharacterSelectionScreen(); // initialize character selection screen
			gameScreen = new GameScreen(); // initialize game screen
			//playerHud = new PlayerHud(); // initialize player hud
			endScreen = new EndScreen();
			init(); // initialize game	
		}

		/**
		 * init 
		 * this method initializes any value that need to be set when running the game for the first time
		 */
		private void init() {
			gameState = 0;
			playerList.clear();
			enemyList.clear();
			win = false;
			score = 0;
			characterSelectionScreen.setReady(false);
			characterSelectionScreen.setSelected(5);
		}

		/**
		 * paintComponent
		 * @param Graphics g		used to draw
		 * @Override				
		 * this method updates the screen the user sees
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.clearRect(0, 0, WIDTH, HEIGHT); // clear screen
			if (gameState == 0) { // main menu
				menuScreen.draw(g); // draw main menu screen
			} else if (gameState == 1) { // instructions
				instrScreen.draw(g); // draw instruction screen
			} else if (gameState == 2) { // options
				optionsScreen.draw(g); // draw options screen
			} else if (gameState == 3) { // lobby
				// draw lobby
				characterSelectionScreen.draw(g, playerList);
			} else if (gameState == 4){ // game
				// draw map
				gameScreen.draw(g);
				// draw hud of all four players
				//playerHud.draw(g, playerList);


				// draw all players and enemies
				drawPlayerAndEnemy(g);
			} else { // end screen
				if (!win) { // if game over (lost)
					//draw lose screen
					endScreen.draw(g);
					// draw high score
					g.setColor(Color.CYAN);
					g.setFont(scoreFont);
					g.drawString("Waves Defeated: " + Integer.toString(score), 400, 300);
				} else { // if game over (win)
					//draw win screen
				}
			}

			// draw the framerate
			g.setColor(Color.BLACK);
			g.setFont(fpsFont);
			g.drawString(getFrameRate(), 10, 15); // invoke method to get framerate
			repaint(); //repaint the screen
		}

		/**
		 * drawPlayerAndEnemy
		 * @param g 		Graphics g object
		 * this method draws all players and enemies based on y coordinate (smaller y coordinate drawn first)
		 */
		private void drawPlayerAndEnemy(Graphics g) {
			Character[] temp = new Character[playerList.size()+enemyList.size()];

			//System.out.println("-----------------"); // test
			//System.out.println("ORIGINAL"); // test
			for (int i = 0; i < playerList.size(); i++) {
				temp[i] = playerList.get(i);
				//System.out.println(i); // test
				//System.out.println(playerList.get(i).getyPosition()); //test
			}
			for (int i = 0; i < enemyList.size(); i++){
				temp[i+(playerList.size())] = enemyList.get(i);
				//System.out.println(i); // test
				//System.out.println(enemyList.get(i).getyPosition()); //test
			}
			//System.out.println("-----------------"); // test
			boolean swapped = false; // variable to check if the two numbers were changed
			Character tempItem; // variable to store the temporary item

			for (int i = 0; i < temp.length; i++) {
				swapped = false; // change variable to false
				for (int j = 0; j < temp.length - 1- i; j++) { // go through all the elements of the number array - count
					if (temp[j].compareTo(temp[j+1]) > 0) { // if the number at j is greater than the number in front of it
						tempItem = temp[j]; // store the number at j to tempItem

						// swap the two numbers at index i and i+1
						temp[j] = temp [j+1];
						temp[j+1] = tempItem;
						swapped = true; // change variable to true
					}
				}
				if (swapped = false) {
					i = temp.length;
				}
			}
			//System.out.println("Temp Size" + temp.length); //test

			for (int i = 0; i < playerList.size(); i++) {
				playerList.get(i).drawHud(g, i);
			}

			for (int i = 0; i < temp.length; i++) {
				//System.out.println(temp[i].getyPosition()); //test
				if (temp[i] instanceof Minion) {
					((Minion)temp[i]).draw(g);
				} else {
					((Player)temp[i]).draw(g);
				}
			}
		}

		/**
		 * getFrameRate
		 * @return String frameRate		the framerate the game is currently running at
		 * this method calculates the framerate the game is running
		 */
		private String getFrameRate() {
			long currentTime = System.currentTimeMillis();
			deltaTime += currentTime - lastTimeCheck;
			lastTimeCheck = currentTime;
			frameCount++;
			if (deltaTime >= 1000) {
				frameRate = frameCount + "fps";
				frameCount = 0;
				deltaTime = 0;
			}
			return frameRate;
		}

		/**
		 * mouseClicked
		 * @Override
		 * this method checks for if mouse button 1 is clicked and if any of the game buttons are clicked
		 * if a button is clicked the game state is changed to draw new screen
		 */
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() ==MouseEvent.BUTTON1) {
				if (gameState == 0) { // if game state is in main menu
					playMusic();
					if (menuScreen.getPlayRect().contains(e.getX(), e.getY())) {
						//System.out.println("PLAY(3)"); // test
						connect(); // connect to server

						// for when server is not running to test the game 
						//gameState = 4; // test
						//start(); // test
					} else if (menuScreen.getInstructionsRect().contains(e.getX(), e.getY())) { // if instructions button is clicked
						//System.out.println("INST(1)"); // test
						gameState = 1; // change game state to 1 (instr)
					} else if (menuScreen.getOptionsRect().contains(e.getX(), e.getY())) { // if options button is clicked
						//System.out.println("OPT(2)"); // test
						gameState = 2;  // change game state to 2 (options)
					} else if (menuScreen.getQuitRect().contains(e.getX(), e.getY())) { // if quit button is clicked
						frame.dispose(); //close game
					}
				} else if (gameState == 1) { // if game state is in instructions

					if (instrScreen.getBackBox().contains(e.getX(), e.getY())){
						gameState = 0; // return to main menu (test)
					}
				} else if (gameState == 2) { // if game state is in options
					gameState = 0; // return to main menu
				} else if (gameState == 3) { // lobby
					for (int i = 0; i < 4; i++){
						if (characterSelectionScreen.isReady() == false) {
							if (characterSelectionScreen.getCharacterBox(i).contains(e.getX(), e.getY())){
								characterSelectionScreen.setSelected(i);
								if (i == 0) {
									outputToServer("MONK"); // invoke method to output to server the class chosen
									//System.out.println("M"); // test
								} else if (i == 1) {
									outputToServer("PALADIN"); // invoke method to output to server the class chosen
									//System.out.println("P"); // test
								} else if (i == 2) {
									outputToServer("WARRIOR"); // invoke method to output to server the class chosen
									//System.out.println("W"); // test
								} else if (i == 3) {
									outputToServer("SAMURAI"); // invoke method to output to server the class chosen
									//System.out.println("S"); // test
								}
							}
						}
					}
					if (characterSelectionScreen.getSelected() != -1 && characterSelectionScreen.isReady() == false){
						if (characterSelectionScreen.getReadyBox().contains(e.getX(), e.getY())){
							characterSelectionScreen.setReady(true);
							outputToServer("READY"); // invoke method to output to server that player is ready
						}
					}
				} else if (gameState == 4) { // game

				} else { //end screen
					if (endScreen.getConfirmRect().contains(e.getX(), e.getY())) {
						init();
						JOptionPane.showMessageDialog(null, "Restart Server to Play Again", null, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {}

		/**
		 * mouseMoved
		 * @Override
		 * this method checks for mouse movement to draw the icon to show that the user is hovering over a button
		 */
		public void mouseMoved(MouseEvent e) {
			if (gameState == 0) { // if game state is in main menu
				if (menuScreen.getPlayRect().contains(e.getX(), e.getY())) {
					//System.out.println("PLAY"); // test
					menuScreen.setPlayHovered(true);
					menuScreen.setInstructionsHovered(false);
					menuScreen.setOptionsHovered(false);
					menuScreen.setQuitHovered(false);
				} else if (menuScreen.getInstructionsRect().contains(e.getX(), e.getY())) {
					//System.out.println("INSTRUCTIONS"); // test
					menuScreen.setPlayHovered(false);
					menuScreen.setInstructionsHovered(true);
					menuScreen.setOptionsHovered(false);
					menuScreen.setQuitHovered(false);
				} else if (menuScreen.getOptionsRect().contains(e.getX(), e.getY())) {
					//System.out.println("OPTIONS"); // test
					menuScreen.setPlayHovered(false);
					menuScreen.setInstructionsHovered(false);
					menuScreen.setOptionsHovered(true);
					menuScreen.setQuitHovered(false);
				} else if (menuScreen.getQuitRect().contains(e.getX(), e.getY())) {
					//System.out.println("QUIT"); // test
					menuScreen.setPlayHovered(false);
					menuScreen.setInstructionsHovered(false);
					menuScreen.setOptionsHovered(false);
					menuScreen.setQuitHovered(true);
				} else {
					menuScreen.setPlayHovered(false);
					menuScreen.setInstructionsHovered(false);
					menuScreen.setOptionsHovered(false);
					menuScreen.setQuitHovered(false);
				}
			}
		}

		/**
		 * keyPressed
		 * @Override
		 * this method checks for key presses and changes the boolean variables on key press
		 */
		public void keyPressed(KeyEvent e) {
			if (gameState == 4) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					//System.out.print("UP"); //test
					//if (upKey == false) {
					//	outputToServer("W true "); // output to server that W key has been pressed
					upKey = true;
					//}
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					//System.out.print("DOWN"); //test
					//if (downKey == false) {
					//	outputToServer("S true "); // output to server that S key has been pressed
					downKey = true;
					//}
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					//System.out.print("LEFT"); //test
					//if (leftKey == false) {
					//	outputToServer("A true "); // output to server that A key has been pressed
					leftKey = true;
					//}
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					//System.out.print("RIGHT"); //test
					//if (rightKey == false) {
					//	outputToServer("D true "); // output to server that D key has been pressed
					rightKey = true;
					//}
				}
				if (e.getKeyCode() == KeyEvent.VK_J) {
					//System.out.print("ATTACK"); //test
					//if (attKey == false) {
					//	outputToServer("J true "); // output to server that J key has been pressed
					attKey = true;
					playSound();
					//}
				}
			}
		}

		/**
		 * keyReleased
		 * @Override
		 * this method checks for key releases and changes the boolean variables on key release
		 */
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				//if (upKey == true) {
				//	outputToServer("W false "); // output to server that W key has been released
				upKey = false;
				//}
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				//if (downKey == true) {
				//	outputToServer("S false "); // output to server that S key has been released
				downKey = false;
				//}
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				//if (leftKey == true) {
				//	outputToServer("A false "); // output to server that A key has been released
				leftKey = false;
				//}
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				//if (rightKey == true) {
				//	outputToServer("D false "); // output to server that D key has been released
				rightKey = false;
				//}
			}
			if (e.getKeyCode() == KeyEvent.VK_J) {
				//if (attKey == true) {
				//	outputToServer("J false "); // output to server that J key has been released
				attKey = false;
				//}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {}

		/**
		 * start
		 * this method creates a thread for the game and starts the thread
		 */
		public void start() {
			Thread gameThread = new Thread(this);
			gameThread.start();
			running = true;
		}

		/**
		 * run
		 * this method is game loop for input from server
		 */
		public void run()
		{		    
			int target = 60;
			double timePerTick = 1000000000 / target;
			double delta = 0;
			long now;
			long lastTime = System.nanoTime();

			while (running) {
				//System.out.println("running"); // test
				now = System.nanoTime();
				delta += (now - lastTime) / timePerTick;
				lastTime = now;

				if (connected) {
					inputFromServer();
				}

				if (delta >= 1) {
					if (connected && gameState == 4) {
						outputToServer(null);
					}
					delta--;
				}
			} //end of running loop
		} //end of run method


		/**
		 * connect
		 * this method attempts to connect to the game server. Jdialog is opened to ask for server ip which is used to connect
		 */
		private void connect() {
			String ipAddress = JOptionPane.showInputDialog("Enter Server IP"); // ask for server ip
			//System.out.println("Connecting to server...");
			if (ipAddress != null) {
				try {
					socket = new Socket(ipAddress, 5000);
					InputStreamReader stream = new InputStreamReader(socket.getInputStream());
					input = new BufferedReader(stream);
					output = new PrintWriter(socket.getOutputStream());
					//System.out.println("Connected to server.");
					//gameState = 4; // test (no server)
				} catch (IOException e) {
					//System.out.println("Failed to connect to server.");
					e.printStackTrace();
					gameState = 0; // change game state to 0 (main menu)
					JOptionPane.showMessageDialog(null, "Failed to connect to server. Please try again later.", null, JOptionPane.ERROR_MESSAGE);
				}

				try {
					//if (input.ready()) {
					String msg = input.readLine();
					//System.out.println("From server:" + msg);
					clientNum = Integer.parseInt(msg);
					characterSelectionScreen.setPlayer(clientNum);
					//System.out.println("playernum:"+clientNum);
					//}
				} catch (IOException e) {
					//System.out.println("\nFailed to receive message from server");
					e.printStackTrace();
				}

				start();
				connected = true;
				gameState = 3;  // change game state to 3 (lobby)
				//System.out.println(connected);
			}
		}

		/**
		 * outputToServer
		 * this method outputs all client data that needs to be sent to server
		 */
		private void outputToServer(String msg) {
			if (msg == null) {
				String outputMsg = String.valueOf(upKey) + " " + String.valueOf(leftKey) + " " + String.valueOf(downKey) + " " + 
						String.valueOf(rightKey) + " " + String.valueOf(attKey) + " ";
				//System.out.println("To Server: " + outputMsg);
				output.println(outputMsg);
				output.flush();
			} else {
				//System.out.println("To server: " + msg);
				output.println(msg); // output to server
				output.flush();
			}
		}

		/**
		 * inputFromServer
		 * this method attempts to get input from the server
		 */
		private void inputFromServer() {
			try {
				if (input.ready()) {
					// get data from server
					inputMsg = input.readLine();
					System.out.println("from server: " + inputMsg); // test
					decodeInput();
				}
			} catch(IOException e) {
				//System.out.println("\nFailed to receive message from server");
				e.printStackTrace();
			}
		}

		/**
		 * decodeInput
		 * this method decodes the string of data that is received from the server
		 */
		private void decodeInput() {
			int totalPlayers;
			char classType;

			if (gameState == 3) { // if in lobby
				//System.out.println("from server:" + inputMsg); // test
				if (inputMsg.equals("GAME STARTED")) {
					gameState = 4; // change game state to 4 (game);
				} else { // if a player has connected or disconnected or class chosen
					//System.out.println("original: " + inputMsg); // test
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
					totalPlayers = Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" "))); // get total players from string 
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);



					//////////////////////////////// PLAYERS //////////////////////////////////
					for (int i = 0; i < playerList.size(); i++) {
						//System.out.println("Loop#1:" + i); // test
						classType = inputMsg.charAt(0); // get class type from string
						//System.out.println(classType); // test
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ") + 1);

						// if the current player object at index class does not match the corresponding letter
						// (ie object at i is Warrior class but the string input is 'M' for Monk class
						if ((playerList.get(i) instanceof Warrior && classType != 'W') || (playerList.get(i) instanceof Monk && classType != 'M') ||
								(playerList.get(i) instanceof Samurai && classType != 'S') ||(playerList.get(i) instanceof Paladin && classType != 'P'
								|| playerList.get(i) instanceof DummyClass && classType !=' ')) {
							playerList.remove(i); // remove the player object
							Player p; // declare new player object

							if (classType == 'W') {
								p = new Warrior(); //initialize as Warrior
							} else if (classType == 'M') {
								p = new Monk(); //initialize as Monk
							} else if (classType == 'S') {
								p = new Samurai(); //initialize as Samurai
							} else  if (classType == 'P') {
								p = new Paladin(); //initialize as Paladin
							} else {
								p = new DummyClass();
							}

							if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) { // get boolean of 'ready' from string
								p.setReady(true); // set player object ready value to true
							} else {
								p.setReady(false); // set player object ready value to false
							}
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);

							playerList.add(i, p); // add new player object to same index
							//playerList.remove(i+1); // remove the player object
						} else {
							if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) { // get boolean of 'ready' from string
								playerList.get(i).setReady(true); // set player object ready value to true
							} else {
								playerList.get(i).setReady(false); // set player object ready value to false
							}
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						}
					}
					//System.out.println("TotalPlayers:" + totalPlayers); // test
					//System.out.println("Loop#2 expected iterations:" + (totalPlayers-playerList.size())); //test
					for (int i = 0; i < (totalPlayers-playerList.size()); i++) {
						//System.out.println("Loop#2:" + i); // test
						Player p;
						classType = inputMsg.charAt(0); // get class type from string
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ") + 1);

						if (classType == 'W') {
							p = new Warrior(); //initialize as Warrior
						} else if (classType == 'M') {
							p = new Monk(); //initialize as Monk
						} else if (classType == 'S') {
							p = new Samurai(); //initialize as Samurai
						} else if (classType == 'P'){
							p = new Paladin(); //initialize as Paladin
						}else {
							p = new DummyClass();
						}

						if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) { // get boolean of 'ready' from string
							p.setReady(true); // set player object ready value to true
						} else {
							p.setReady(false); // set player object ready value to false
						}
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);

						playerList.add(p);
					}
				}
			} else { // if in game
				if (inputMsg.indexOf("GAMEOVER") >= 0) {
					score = Integer.parseInt(inputMsg.substring(inputMsg.lastIndexOf(" ")+1));
					win = false;
					gameState = 5; // change game state to end screen
				} else {
					int totalEnemies;

					//System.out.println("original: " + inputMsg); // test
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
					totalPlayers = Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" "))); // get total players from string
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);


					//////////////////////////////// PLAYERS //////////////////////////////////
					// System.out.println("before loop: " + code); // test
					for (int i = 0; i < totalPlayers; i++) {
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ") + 1);
						// System.out.println("iteration" + i + ": " + code ); // test
						playerList.get(i).setxPosition(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); // set xpos
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						// System.out.println("iteration" + i + ": " + code ); // test
						playerList.get(i).setyPosition(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); // set ypos
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						playerList.get(i).setHealth(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); // set health
						playerList.get(i).getHud().setHealthCurrent(playerList.get(i).getHealth());
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						playerList.get(i).setMana(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); // set mana
						playerList.get(i).getHud().setManaCurrent(playerList.get(i).getMana());
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
							playerList.get(i).setMoving(true); // set moving true
						} else {
							playerList.get(i).setMoving(false); // set moving false
						}
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
							playerList.get(i).setFacingForward(true); // set facing forward true
						} else {
							playerList.get(i).setFacingForward(false); // set facing forward false
						}
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
							playerList.get(i).setAttacking(true); // set attacking true
						} else {
							playerList.get(i).setAttacking(false); // set attacking false
						}
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
					}


					//////////////////////////////// ENEMIES //////////////////////////////////
					//System.out.println("original: " + inputMsg); // test
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
					totalEnemies = Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")));
					inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
					// System.out.println("before loop: " + code); // test
					//System.out.println(totalEnemies + " " + enemyList.size());  // test
					if (totalEnemies > enemyList.size()) { // if input from server states more enemies than what client has
						int counter = totalEnemies - enemyList.size();
						// loop n number of times where n is the difference of the server total enemies
						// and client total enemies
						for (int i = 0; i < counter; i++) {
							Minion e = new Minion(); // new minion object
							enemyList.add(e); // add to enemyList
						}
					}
					//System.out.println(totalEnemies + " " + enemyList.size()); // test

					for (int i = 0; i < enemyList.size(); i++) {
						// System.out.println("iteration" + i + ": " + code ); // test
						enemyList.get(i).setxPosition(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); //set xpos
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						// System.out.println("iteration" + i + ": " + code ); // test
						enemyList.get(i).setyPosition(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); //set ypos
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						enemyList.get(i).setHealth(Integer.parseInt(inputMsg.substring(0, inputMsg.indexOf(" ")))); //set health
						inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						if (enemyList.get(i).getHealth() > 0) { // if the enemy's health is greater than 0
							if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
								enemyList.get(i).setMoving(true); //set moving true
							} else {
								enemyList.get(i).setMoving(false); //set moving false
							}
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);

							if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
								enemyList.get(i).setFacingForward(true); //set facing forward true
							} else {
								enemyList.get(i).setFacingForward(false); //set facing forward false
							}
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
							if (inputMsg.substring(0, inputMsg.indexOf(" ")).equals("true")) {
								enemyList.get(i).setAttacking(true); //set attacking true
							} else {
								enemyList.get(i).setAttacking(false); //set attacking false
							}
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
						} else { // if supposed to be dead
							enemyList.remove(i); // remove the enemy at index i
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
							inputMsg = inputMsg.substring(inputMsg.indexOf(" ")+1);
							i--; // decrement i
						}
					}
				}
			}
		}

		public void playSound(){
			try {
				File yourFile = new File("SwordSwing.wav");
				AudioInputStream stream;
				AudioFormat format;
				DataLine.Info info;
				Clip clip;

				stream = AudioSystem.getAudioInputStream(yourFile);
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(stream);

				/*FloatControl gainControl = 
		       (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		       gainControl.setValue(-0.0f);
				 */
				clip.start();
			}
			catch (Exception e) {

			}
		}

		public void playMusic(){
			try {
				File yourFile = new File("EpicMusic.wav");
				AudioInputStream stream;
				AudioFormat format;
				DataLine.Info info;
				Clip clip;

				stream = AudioSystem.getAudioInputStream(yourFile);
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(stream);

				FloatControl gainControl = 
						(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-20.0f);

				clip.start();
			}
			catch (Exception e) {
			}
		}

	} // end of gamepanel class
} // end of game window class
