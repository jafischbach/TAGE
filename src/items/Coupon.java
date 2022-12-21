package items;

import game.*;

public class Coupon extends Item {

	public Coupon() {
		super("coupon");
	}
	
	@Override
	public void look() {
		Game.print("Spinach World - 50% all kale products (non-organic).\nExpires: March 15, 1986");
	}
	
	@Override
	public void use() {
		Game.print("Look around. Does this place look like a Spinach World to you?");
	}
	
	@Override
	public void take(String command) {
		Game.print("You already have the coupon. And if you didn't, why would you want to take it?");
	}
	
}
