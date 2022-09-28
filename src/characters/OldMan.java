package characters;

import game.*;

public class OldMan extends NPC {

	public OldMan() {
		super("old man");
		String d = "The old man is sleeping peacefully.";
		setDesc(d);
	}
	
	public void talk() {
		Game.print("The old man doesn't talk in his sleep.");
	}
	
	public void attack(String weapon) {
		System.out.print("Really? You really want to attack the gentle old man"
				+ " with the "+weapon+"? Really? (y/n) ");
		char response = Character.toLowerCase(Game.input.nextLine().charAt(0));
		if (response == 'y') {
			if (weapon.equals("bottle") || weapon.equals("sledge hammer")
					|| weapon.equals("crowbar") || weapon.equals("chainsaw")) {
				Game.print("You begin your viscious, unprovoked attack on the gently"
						+ " sleeping old man. Sadly for you, it turns out that the"
						+ " old man is a master graduate of the School of the Mongoose."
						+ " He wakes instantly, grabs the "+weapon+" out of your hands"
						+ " and proceeds to end your life by puncturing your carotid"
						+ " artery with a fingernail. You die in gurgling agony.");
				Game.endGame();
			} else {
				Game.print("How desparate are you? You can't attach the old man with"
						+ " a "+weapon+"!");
			}
		} else {
			Game.print("Glad to hear you're not a total jackass.");
		}
	}
	
}