package characters;

import game.*;

public class Cop extends NPC {

	public Cop() {
		super("cop");
	}
	
	public void look() {
		Game.print("The cop looks intimidating in his sharp, blue uniform. His badge glistens"
				+ " in the sun. He squints at you, waiting for you to make the first move. You're"
				+ " not getting past this guy.");
	}
	
	public void attack(String weapon) {
		Game.print("You brought a " + weapon + " to a gun fight. You're not going to win that one.");
	}
	
	public void talk() {
		say("Well, well, well. Who do we have here? Chainsaw. Crowbar. Slegde hammer. Looks like"
				+ " a vandal to me. A professional vandal. Those are some serious tools. Master"
				+ " of the trade, eh? You been doing a little unconventional work on this here"
				+ " fine hotel? I see what you've done to the front door. A stately entrance"
				+ " that was. Passed through many times myself. No more though. You saw to that,"
				+ " didn't you? (No pun intended, I suppose.) In any case, I'm gonna have to"
				+ " arrest your delinquint vandal ass. You're looking at hard time. Hard time.");
		String[] option = new String[2];
		option[0] = "I had to! I was trapped! There was no other way out. Look! This freakin'"
				+ " hotel doesn't even have windows. What kind of hotel doesn't have windows?"
				+ " And I'm telling you, there's something not right with that bartender...";
		if (Game.player.has("cash"))
			option[1] = "Well, Mr. Law Enforcement Officer, sir. Deputy Steve, is it? I just"
					+ " so happen to have this pile of cash. I don't suppose that might alter"
					+ " the situation here, would it?";
		else
			option[1] = "Nuts.";
		getResponse(option);
	}
	
	public void response(int choice) {
		switch(choice) {
		case 1:
			say("The bartender is my little brother Fitzroy. I'll not hear a cross word about"
					+ " him. As for the rest, tell it to the judge.");
			Game.print("You are arrested. You are convicted. You never see the light of day again."
					+ " (Vandalism laws are pretty tough in these parts.)");
			Game.endGame();
			break;
		case 2:
			if (Game.player.has("cash")) {
				Game.print("The cop eagerly accepts the proferred bribe. Well, there goes your"
						+ " beach house fund.");
				Game.print("Congratulations! You escaped the hotel and avoided doing hard time"
						+ " in the state pen. What a day, am I right?");
				Game.endGame();
			} else {
				say("I'd read you your rights, but you look like someone who's heard them many"
						+ " times before.");
				Game.print("You are arrested. You are convicted. You never see the light of day again."
						+ " (Vandalism laws are pretty tough in these parts.)");
				Game.endGame();
			}
		}
	}
	
	public void give(String item) {
		if (item.equals("cash")) {
			Game.print("The cop eagerly accepts the proferred bribe. Well, there goes your"
					+ " beach house fund.");
			Game.print("Congratulations! You escaped the hotel and avoided doing hard time"
					+ " in the state pen. What a day, am I right?");
			Game.endGame();
		} else
			super.give(item);
	}
	
}
