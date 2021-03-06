package core.game.interaction.item.brawling_gloves;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.player.Player;

import core.game.node.item.Item;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Objects;

/**
 * Manages brawling gloves for a player
 * @author ceik
 */
public class BrawlingGlovesManager {

    final Player player;
    public HashMap<Integer, Integer> GloveCharges = new HashMap<Integer,Integer>();
    public void registerGlove(int id) {
        try {
            registerGlove(id, Objects.requireNonNull(BrawlingGloves.forId(id)).getCharges());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void registerGlove(int id, int charges) {GloveCharges.putIfAbsent(id,charges);}

    public BrawlingGlovesManager(Player player){this.player = player;}

    public boolean updateCharges(int glove, int charges){
        if(GloveCharges.get(glove) != null){
            if(GloveCharges.get(glove) - charges <= 0) {
                GloveCharges.remove(glove);
                player.getEquipment().remove(new Item(glove));
                player.getPacketDispatch().sendMessage("<col=ff0000>You use the last charge of your " + ItemDefinition.forId(glove).getName() + " and they vanish.</col>");
                return false;
            }
            int currentCharges = GloveCharges.get(glove);
            GloveCharges.replace(glove,currentCharges - charges);
            player.debug("Glove charges: " + (currentCharges - 1));
            if((currentCharges - 1) % 50 == 0) {
                player.getPacketDispatch().sendMessage("<col=1fbd0d>Your " + ItemDefinition.forId(glove).getName() + " have " + GloveCharges.get(glove) + " charges left.</col>");
            }
        }
        return true;
    }

    public double getExperienceBonus(){
        double bonus;
        int level = player.getSkullManager().getLevel();
        if(level > 0){
            bonus = 3.0;
        }else{
            bonus = 0.5;
        }
        return bonus;
    }
}
