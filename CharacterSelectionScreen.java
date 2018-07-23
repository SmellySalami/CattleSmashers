import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class CharacterSelectionScreen {
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;

	private ImageIcon characterSelection;

	private ImageIcon monkLabel;
	private ImageIcon paladinLabel;
	private ImageIcon warriorLabel;
	private ImageIcon samuraiLabel;

	private ImageIcon selectionGrey;

	//private ImageIcon checkIcon;
	private CheckIcon cIcon = new CheckIcon();

	private Rectangle[] characterBox = new Rectangle[4];
	private Rectangle readyBox;
	private int selected;
	private int player;
	private int labelX, labelY;
	private boolean ready;

	CharacterSelectionScreen(){

		characterSelection = new ImageIcon ("resource/CharacterSelection.png");

		selectionGrey = new ImageIcon("resource/SelectionGrey.png");
		monkLabel = new ImageIcon("resource/monkLabel.png");
		paladinLabel = new ImageIcon("resource/paladinLabel.png");
		warriorLabel = new ImageIcon("resource/warriorLabel.png");
		samuraiLabel = new ImageIcon("resource/samuraiLabel.png");
		//checkIcon = new ImageIcon("resource/checkIcon.png");

		for (int i = 0; i < 4; i++){
			characterBox[i] = new Rectangle(280, 280);
		}

		readyBox = new Rectangle (80, 40);
		selected = -1;

		characterBox[0].setLocation(20, 20);
		characterBox[1].setLocation(340, 20);
		characterBox[2].setLocation(660, 20);
		characterBox[3].setLocation(980, 20);

		readyBox.setLocation(1200, 680);
	}

	public void draw(Graphics g, ArrayList<Player> PlayerList){
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(characterSelection.getImage(), 0, 0, null);

		if (player == 0){
			labelX = 20; 
		}else if (player == 1){
			labelX = 340;
		}else if (player == 2){
			labelX = 660;
		}else if (player == 3){
			labelX = 980;
		}	

		if (selected == 0){

			g.drawImage(monkLabel.getImage(), labelX, 620, null);

			g.drawImage(selectionGrey.getImage(), 20, 20, null);

		}else if (selected == 1){

			g.drawImage(paladinLabel.getImage(), labelX, 620, null);

			g.drawImage(selectionGrey.getImage(), 340, 20, null);

		}else if (selected == 2){

			g.drawImage(warriorLabel.getImage(), labelX, 620, null);

			g.drawImage(selectionGrey.getImage(), 660, 20, null);

		}else if (selected == 3){

			g.drawImage(samuraiLabel.getImage(), labelX, 620, null);

			g.drawImage(selectionGrey.getImage(), 980, 20, null);
		}

		if (ready == true){
			cIcon.draw(g, 1190, 693);
			//g.drawImage(checkIcon.getImage(), 1175, 693, null);
		}

		for (int i = 0; i < PlayerList.size(); i++){
			if (player != i){
				if (PlayerList.get(i) instanceof Monk){
					g.drawImage(monkLabel.getImage(), i*320 + 20, 620, null);
				}else if (PlayerList.get(i) instanceof Paladin){
					g.drawImage(paladinLabel.getImage(), i*320 + 20, 620, null);
				}else if (PlayerList.get(i) instanceof Warrior){
					g.drawImage(warriorLabel.getImage(), i*320 + 20, 620, null);
				}else if (PlayerList.get(i) instanceof Samurai){
					g.drawImage(samuraiLabel.getImage(), i*320 + 20, 620, null);
				}
			}
		}


	}


	public Rectangle getCharacterBox(int i) {
		return characterBox[i];
	}

	public void setCharacterBox(Rectangle[] characterBox) {
		this.characterBox = characterBox;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public Rectangle getReadyBox() {
		return readyBox;
	}

	public void setReadyBox(Rectangle readyBox) {
		this.readyBox = readyBox;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}



}