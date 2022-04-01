package core.game.content.dialogue;

import core.game.node.entity.npc.NPC;
import core.plugin.Initializable;
import core.game.node.entity.player.Player;

/**
 * Represents the lumbridge swamp lizard dialogue.
 * @author 'Vexia
 * @version 1.0
 */
@Initializable
public final class LumbridgeSwampWizard extends DialoguePlugin {

	/**
	 * Constructs a new {@code LumbridgeSwampWizard} {@code Object}.
	 */
	public LumbridgeSwampWizard() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code LumbridgeSwampWizard} {@code Object}.
	 * @param player the player.
	 */
	public LumbridgeSwampWizard(Player player) {
		super(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.HALF_GUILTY, "Why are all of you standing around here?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.HALF_GUILTY, "Hahaha you dare talk to a mighty wizard such as", "myself? I bet you can't even cast windstrike yet", "amateur!");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.HALF_GUILTY, "...You're an idiot.");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new LumbridgeSwampWizard(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 652 };
	}

}
