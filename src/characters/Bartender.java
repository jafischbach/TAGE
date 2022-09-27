package characters;

import game.*;
import items.*;

public class Bartender extends NPC {

	private int convo;
	private boolean isPolite;
	
	public Bartender(String name) {
		super(name);
		convo = 0;
		isPolite = true;
		String d = "The bartender is a tall, thin man. His bored expression suggests"
				+ " that he'd rather be anywhere else and doing anything else. He doesn't"
				+ " look surprided or thrilled by your presence in this otherwise deserted hotel.";
		setDesc(d);
	}
	
	private void convo1() {
		say("Hello, sir. I was taking a nap just now, but I woke up when some imbecile"
				+ " decided to start demolishing something on the second floor. Was"
				+ " that you, perhaps?");
		String[] options = new String[2];
		options[0] = "Yes.";
		options[1] = "Nope. Wasn't me.";
		int choice = getConvoOption(options);
		if (choice == 1)
			say("Please refrain from destroying any more of the hotel. It's probably"
					+ " best if you give me that sledge hammer lest you succumb to"
					+ " temptation later.");
		else {
			isPolite = false;
			say("Hm. You had nothing to do with the noise upstairs. And you just happen"
					+ " to be carrying around a sledge hammer. I think you are a lying"
					+ " bastard, sir. A filthy, lying bastard.");
		}
	}
	
	private void convo2() {
		if(isPolite)
			say("Yes, sir. What can I do for you?");
		else
			say("What do you want, you filthy, lying bastard?");
		String[] options = new String[3];
		options[0] = "Can I get a rum and coke?";
		options[1] = "Got any beer?";
		options[2] = "You wouldn't believe the day I've had...";
		int choice = getConvoOption(options);
		switch(choice) {
		case 1:
			if (isPolite)
				say("Sorry, sir. The bar is not very well stocked I'm afraid.");
			else
				say("Of course, sir. I have a bottle of rum back here, but I keep"
						+ " the coke on ice in the rest room right over there. In"
						+ " the toilet. Go fetch me a can, won't you?");
			break;
		case 2:
			say("Beer? Beer!? This is a classy hotel, sir. We don't serve...beer.");
			break;
		case 3:
			say("I'm not that kind of bartender. I serve drinks. I'm not paid to"
					+ " listen to you whine about your lousy day.");
		}
	}
	
	private void convo3() {
		say("You're still here. Wonderful. I wouldn't want the opportunity to return"
				+ " to my nap. Thank you for keeping me occupied. Was there something"
				+ " you needed?");
		String[] options = new String[3];
		options[0] = "Beer. I want beer.";
		options[1] = "Got any cheese?";
		options[2] = "So, the front door is locked. Do you have the key?";
		int choice = getConvoOption(options);
		switch(choice) {
		case 1:
			say("Then feel free to visit your local convenience store. Pick up some"
					+ " slim jims while your at it. I hear the sushi is lovely as well."
					+ " Here's a coupon. Enjoy.");
			Player.addItem(new Coupon());
			break;
		case 2:
			say("Sure. Swiss? Gouda? Ricotta? Brie? Oh, wait. This is all we have left. Enjoy.");
			Player.addItem(new Cheese());
			break;
		case 3:
			say("The front door is locked? That's odd. Sadly, I don't have a key. Please"
					+ " resist the urge to try that sledge hammer. The door is quite sturdy."
					+ " There is an employee entrance, though. I could tell you about it..."
					+ "if you brought me a corkscrew. Opening wine bottles has been a"
					+ " challenge lately. In the meantime, have a cookie.");
		}
	}
	
	public void talk() {
		convo++;
		switch(convo) {
		case 1: convo1(); break;
		case 2: convo2(); break;
		case 3: convo3(); break;
		default:
			say("Please, sir. I do have other customers to tend to.");
		}
	}
	
	public void give(String itemName) {
		if (itemName.equals("bottle")) {
			say("What are you doing with that? Just help yourself while"
					+ " the bartender is off taking a nap. Is that how it is? It's empty!"
					+ " Did you drink it all? <mumbles something inaudible but probably not"
					+ " flattering>");
			Player.removeItem("bottle");
		} else if (itemName.equals("sledge hammer")) {
			say("Actually, I'd rather not hold onto that. When the owner sees whatever you"
					+ " did upstairs, he might blame the one with the sledge hammer. Better"
					+ " that's you than me.");
		} else 
			super.give(itemName);
	}
	
	public void attack(String weaponName) {
		if (weaponName.equals("bottle")) {
			Game.print("You whack the bartender over the head with the glass bottle. The bottle"
					+ " doesn't break, but neither does the bartender's head. All you've managed"
					+ " to do is upset the bartender. He reaches beneath the bar and brings out"
					+ " a shotgun, which he uses to blow a hole through your chest.");
			Game.endGame();
		} else if (weaponName.equals("sledge hammer")) {
			Game.print("You swing the sledge hammer and pulverize the bartended into a gory heap"
					+ " of bone, blood, and organs. I hope you're proud of yourself. As the"
					+ " bartender was your only means of escaping the hotel, you're now trapped"
					+ " here for the rest of your miserable life. Too bad you skipped those"
					+ " anger management classes.");
			Game.endGame();
		} else if (weaponName.equals("newspaper")){
			Game.print("You repeatedly smack the bartender with your folded newspaper. He just"
					+ " looks at you, bemused, and then snatches the newspaper away from you.");
			Player.removeItem("newspaper");
		} else {
			Game.print("You can't do that.");
		}
	}
	
}
