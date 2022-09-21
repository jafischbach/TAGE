package characters;

import game.Game;
import game.Player;

public class Bartender extends NPC {

	public Bartender(String name) {
		super(name);
		String d = "The bartender is a tall, thin man. His bored expression suggests"
				+ " that he'd rather be anywhere else and doing anything else. He doesn't"
				+ " look surprided or thrilled by your presence in this otherwise deserted hotel.";
		setDesc(d);
	}
	
	public void talk() {
		Game.print(getName()+": I was taking a nap. Then I heard some idiot smashing something"
				+ " upstairs. Was that you? Please refrain from demolishing any more of the"
				+ " hotel.");
	}
	
	public void give(String itemName) {
		if (itemName.equals("bottle") && Player.has("bottle")) {
			Game.print(getName()+": What are you doing with that? Just help yourself while"
					+ " the bartender is off taking a nap. Is that how it is? It's empty!"
					+ " Did you drink it all? <mumbles something inaudible but probably not"
					+ " flattering>");
			Player.removeItem("bottle");
		}
	}
	
	public void attack(String weaponName) {
		if (weaponName.equals("bottle") && Player.has("bottle")) {
			Game.print("You whack the bartender over the head with the glass bottle. The bottle"
					+ " doesn't break, but neither does the bartender's head. All you've managed"
					+ " to do is upset the bartender. He reaches beneath the bar and brings out"
					+ " a shotgun, which he uses to blow a hole through your chest.");
			Game.endGame();
		} else if (weaponName.equals("sledge hammer")) {
			Game.print("You swing the sledge hammer and pulverize the bartended into a gory heap"
					+ " of bone, blood, and organs. I hope you're proud of yourself. As the"
					+ " bartended was your only means of excaping the hotel, you're now trapped"
					+ " here for the rest of your miserable life. Too bad you skipped those"
					+ " anger management classes.");
			Game.endGame();
		} else if (weaponName.equals("newspaper") && Player.has("newspaper")){
			Game.print("You repeatedly smack the bartender with your folded newspaper. He just"
					+ " looks at you, bemused, and then snatches the newspaper away from you.");
			Player.removeItem("newspaper");
		} else {
			Game.print("You can't do that.");
		}
	}
	
}
