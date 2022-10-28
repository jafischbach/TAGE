package characters;

import game.*;
import items.Corkscrew;

public class Wolverine extends NPC {

	public Wolverine() {
		super("wolverine");
	}

	public void look() {
		if (Game.hasFlag("wolverine sleeping"))
			Game.print("The wolverine is sleeping peacefully. Little bastard"
					+ " looks kinda cute now that he's not trying to kill you," + " doesn't he?");
		else
			Game.print("It's small, furry, and totally pissed off.");
	}

	public void attack(String weapon) {
		if (Game.hasFlag("wolverine sleeping"))
			Game.print("The wolverine is sleeping. Leave the poor thing alone.");
		else {
			if (weapon.equalsIgnoreCase("sledge hammer")) {
				Game.print("It turns out that an unwieldly sledge hammer is not"
						+ " the ideal weapon choice when doing battle with a quick and"
						+ " highly aggressive animal. As you attempt to heft the heavy"
						+ " sledge hammer, the wolverine lunges suddenly and tears"
						+ " out your throat. The fiesty animal proceeds to feast" + " on your corpse.");
				Game.endGame();
			} else if (weapon.equalsIgnoreCase("chainsaw")) {
				Game.print("Sadly for you, the wolverine is not dumb enough to just"
						+ " stand there and wait for you to start up the chainsaw."
						+ " Instead, it lunges suddenly and tears out your throat."
						+ " The fiesty animal proceeds to feast on your corpse.");
				Game.endGame();
			} else if (weapon.equalsIgnoreCase("bottle")) {
				Game.print("The wolverine easily dodges your attempt to smite it" + " with the bottle.");
			} else if (weapon.equalsIgnoreCase("cookie")) {
				Game.print("You wave the cookie at the wolverine. The animal is confused"
						+ " at first but then becomes mesmerized as you wave the cookie"
						+ " back and forth in front of its nose. The chocolatey scent"
						+ " calms the animal. Instead of viciously attacking you, it just"
						+ " reaches out and snatches the cookie. Retreating to a corner"
						+ " of the room, the animal settles down for a nap.");
				Game.addFlag("wolverine sleeping");
				Game.getCurrentRoom().setDesc("HOTEL_ROOM_202_B");
				Game.printRoom();
				Player.removeItem("cookie");
				if (!Player.has("corkscrew")) {
					Game.print("Strangely, it appears that the wolverine was guarding a"
							+ " corkscrew. You could contemplate the reason for this, but"
							+ " decide not to. Instead, you just bend down and pick up the" + " corkscrew.");
					Player.addItem(new Corkscrew());
				}
			} else if (weapon.equalsIgnoreCase("crowbar")) {
				hitWolverine();
			} else
				super.attack(weapon);
		}
	}

	private void hitPlayer() {
		int harm = roll(60);
		if (Player.reduceHealth(harm)) {
			Game.print("The wolverine manages to evade your crowbar long"
					+ " enough to bite and scratch you. It's quite painful.");
			Game.print("You: " + Player.getHealth()+"/100");
		} else {
			Game.print("Evading your crowbar, the wolverine lunges and tears"
					+ " out your throat. The fiesty animal proceeds to feast" + " on your corpse.");
			Game.endGame();
		}
	}

	private void hitWolverine() {
		int toss = roll(2);
		if (toss == 0) {
			int harm = roll(100);
			if (reduceHealth(harm)) {
				Game.print("Nice! You whack the wolverine with your crowbar, but"
						+ " not quite hard enough to render it senseless.");
				Game.print("Wolverine: " + getHealth() + "/100");
				hitPlayer();
			} else {
				Game.print("The wolverine attempts to dodge your attack, but you smite"
						+ " the furry bastard with all your strength and render the "
						+ " little beast senseless. It rolls onto the floor and troubles" + " you no more.");
				Game.addFlag("wolverine sleeping");
				Game.getCurrentRoom().setDesc("HOTEL_ROOM_202_B");
				Game.printRoom();
			}
		} else {
			Game.print("You swing your crowbar at the wolverine, but the nimble beast" + " dances out of your reach.");
			hitPlayer();
		}
	}

}
