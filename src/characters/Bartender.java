package characters;

import game.*;
import items.*;

public class Bartender extends NPC {

	private int convo;
	private boolean isPolite;
	private boolean gaveBeer;
	private boolean notMet;
	
	public Bartender(String name) {
		super(name);
		convo = 0;
		isPolite = true;
		gaveBeer = false;
		notMet = true;
	}
	
	private void convo1() {
		say("Hello, sir. I was taking a nap just now, but I woke up when some imbecile"
				+ " decided to start demolishing something on the second floor. Was"
				+ " that you, perhaps?");
		String[] options = new String[2];
		options[0] = "Yes.";
		options[1] = "Nope. Wasn't me.";
		getResponse(options);
	}
	
	private void response1(int choice) {
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
		getResponse(options);
	}
	
	private void response2(int choice) {
		switch(choice) {
		case 1:
			if (isPolite)
				say("Sorry, sir. The bar is not very well stocked I'm afraid.");
			else
				say("Of course, sir. I have a bottle of rum back here, but I keep"
						+ " the coke on ice in the restroom right over there. In"
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
		getResponse(options);
	}
	
	private void response3(int choice) {
		switch(choice) {
		case 1:
			say("Then feel free to visit your local convenience store. Pick up some"
					+ " slim jims while you're at it. I hear the sushi is lovely as well."
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
			Player.addItem(new Cookie());
		}
	}
	
	private void convo4() {
		say("Ah! Excellent! That's exactly what I need. Actually, it looks just like"
				+ " one my coworker Rupert used to have. Um...so, where did you find this?");
		String[] options = new String[2];
		options[0] = "On a dead guy I found stuffed in a closet upstairs.";
		options[1] = "Oh that? I had that in my pocket all along. Yeah, it was a gift from"
				+ " my...uh...cousin Marty. He gave it to me for my 21st birthday. What a"
				+ " guy that Marty always was! Is. Still is. He's not dead or anything. Haha!"
				+ " Who said anything about a dead guy? Um. Yeah.";
		convo = 4;
		getResponse(options);
	}
	
	private void response4(int choice) {
		switch(choice) {
		case 1:
			say("Well. I see. A dead guy. Stuffed in a closet. Upstairs. How did you get into"
					+ " the room? You know, it doesn't matter. There are other rooms with"
					+ " other closets. Some are even empty.");
			Game.print("The bartender reaches beneath the bar and retrieves a shotgun. He"
					+ " points it at your chest and pulls the trigger. And just like that,"
					+ " the hotel has another permanent guest.");
			Game.endGame();
			break;
		case 2:
			say("Hm. Well, in any case. Thank you for the corkscrew. This will make my life"
					+ " so much easier. Trying to pry corks out of bottles with my teeth"
					+ " wasn't going well. As promised, I will tell you about the employee"
					+ " entrance. Or exit in your case. It's in the basement. Clearly marked."
					+ " You can reach the basement through the door marked \"Employees Only\"."
					+ " Here's the key. Come visit again!");
			Game.print("The bartender hands you a large key.");
			Player.addItem(new LargeKey());
		}
	}
	
	private void convo5() {
		say("And you're back. Lovely.");
		String[] options = new String[2];
		options[0] = "So, hey, do you know where I can get some gas for this chainsaw that"
				+ " I most definitely did not just steal from the basement?";
		options[1] = "Does anyone else work here, or is it just you?";
		convo = 5;
		getResponse(options);
	}
	
	private void response5(int choice) {
		switch(choice) {
		case 1:
			say("I'm sure it's perfectly acceptible for you to be carrying around a"
					+ " chainsaw. I have no concerns about that at all. And, yes, I"
					+ " do know where you can find gasoline. If it will hasten your"
					+ " departure, I'll happily help you.");
			Game.print("The bartender hands you a utility key.");
			Player.addItem(new UtilityKey());
			break;
		case 2:
			say("No. We have a full staff. I'm just the only one brave enough to deal"
					+ " with you. You're very unpleasant. Everyone knows this.");
		}
	}
	
	private void giveBeer() {
		if (gaveBeer) {
			Game.print("The bartender glares at you. His eye starts twitching again.");
			say("No.");
			Game.print("He reaches beneath the bar, retrieves a shotgun, and proceeds to end your miserable life,"
					+ " cackling with glee.");
			Game.endGame();
		} else {
			say("Beer? You're giving me...beer? Beer!? BEER!?");
			Game.print("As the bartended grips the can of cool Dr. Hops beer, his hand starts to shake. His eye starts to"
					+ " twitch. His lips start to spasm. Drool dribbles over his chin. What the hell is up with this guy?");
			say("You. You...you...beer-chugging, heathen, barbarian, troglodite...how...why...NO! I will not tolerate your"
					+ " vile presense here any longer. You will leave! You will leave NOW!");
			Game.print("More quickly than you thought possible, the bartender leaps across the bar and grabs your ear."
					+ " He painfully hauls you out of the bar, down a hall, and down a flight of steps to the basement.");
			say("There. Out you go. Out!");
			Game.print("The bartender points to a door marked \"Exit\" and then stalks away.");
			Game.setCurrentRoom("HOTEL_BASEMENT");
			Game.getRoom("HOTEL_EMPLOYEES_ONLY").setLocked(false);
			Player.removeItem("beer");
			gaveBeer = true;
		}
	}
	
	public void talk() {
		if (notMet) {
			notMet = false;
			Game.addFlag("met bartender");
		}
		convo++;
		switch(convo) {
		case 1: convo1(); break;
		case 2: convo2(); break;
		case 3: convo3(); break;
		default:
			if (Player.has("chainsaw") && !Player.has("gas can")) {
				convo5();
			} else if (gaveBeer)
				say("And you're still here. Wonderful.");
			else
				say("Please, sir. I do have other customers to tend to.");
		}
	}
	
	public void response(int choice) {
		switch(convo) {
		case 1: response1(choice); break;
		case 2: response2(choice); break;
		case 3: response3(choice); break;
		case 4: response4(choice); break;
		case 5: response5(choice); break;
		}
	}
	
	public void look() {
		if (notMet) {
			notMet = false;
			Game.addFlag("met bartender");
		}
		String d = "The bartender is a tall, thin man. His bored expression suggests"
				+ " that he'd rather be anywhere else and doing anything else. He doesn't"
				+ " look surprised or thrilled by your presence in this otherwise deserted hotel.";
		Game.print(d);
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
		} else if (itemName.equals("corkscrew")) {
			convo4();
		} else if (itemName.equals("beer")) {
			giveBeer();
		} else if (itemName.equals("cash")) {
			say("Wow! Well, that's quite generous of you. Quite generous. Thank you, sir."
					+ " Thank you! Only once have I received a tip more generous, but that"
					+ " was another time. A quieter time.");
			Game.print("The bartender stares off into space. Just as the moment starts to"
					+ " get awkward...");
			say("Oh? You're still here? Um. Well. Thanks for the tip. Now off you go.");
			Player.removeItem("cash");
		} else if (itemName.equals("bronze room key") || itemName.equals("silver room key")) {
			say("I'm the bartender, sir. This is why I stand behind the bar. You see? If you"
					+ " want to check out, please bring your key to the receptionist. I'm"
					+ " sure he'll be back from his lunch break any day now.");
		} else if (itemName.equals("utility key")) {
			say("And you want to give this back to me because...?");
		} else if (itemName.equals("large key")) {
			say("That's the key to the basement. You can keep it.");
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
		} else if (weaponName.equals("sledge hammer") || weaponName.equals("crowbar")) {
			Game.print("You swing the "+weaponName+" and pulverize the bartender into a gory heap"
					+ " of bone, blood, and organs. I hope you're proud of yourself. As the"
					+ " bartender was your only means of escaping the hotel, you're now trapped"
					+ " here for the rest of your miserable life. Too bad you skipped those"
					+ " anger management classes.");
			Game.endGame();
		} else if (weaponName.equals("newspaper")){
			Game.print("You repeatedly smack the bartender with your folded newspaper. He just"
					+ " looks at you, bemused, and then snatches the newspaper away from you.");
			Player.removeItem("newspaper");
		} else if (weaponName.equals("chainsaw")) {
			if (Player.has("gas can")) {
				Game.print("With sadistic glee, you start the chainsaw and proceed to carve"
						+ " the defenseless bartender into fun size bartender bits. What a"
						+ " gory mess you've made. Had fun?");
				Room r = Game.getCurrentRoom();
				r.removeNPC("bartender");
				r.setDesc("HOTEL_BAR_C");
			} else {
				Game.print("While you'd love to put a permanent end to the bartender, your"
						+ " weapon of choice is lacking in power.");
			}
		} else {
			Game.print("You can't do that.");
		}
	}
	
}
