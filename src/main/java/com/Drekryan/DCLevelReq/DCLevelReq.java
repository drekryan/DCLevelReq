package com.Drekryan.DCLevelReq;

import com.Jessy1237.DwarfCraft.DCPlayer;
import com.Jessy1237.DwarfCraft.DwarfCraft;
import me.blackvein.quests.CustomRequirement;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Map;

public class DCLevelReq extends CustomRequirement {

    public DCLevelReq() {
        this.setName("DCLevelReq");
        this.setAuthor("Drekryan");
        this.addData("SkillID");
        this.addData("ReqLevel");
        this.addDescription("SkillID", "The DwarfCraft Skill ID of the skill you want to require.");
        this.addDescription("ReqLevel", "The required level in that skill they must be.");
    }

    @Override
    public boolean testRequirement(Player player, Map<String, Object> map) {
        Server server = player.getServer();
        PluginManager pm = server.getPluginManager();

        if (pm == null) {
            return false;
        }

        DwarfCraft dwarfCraft = getDwarfCraft(pm);
        if (dwarfCraft == null) {
            server.getConsoleSender().sendMessage("[DCLevelReq] DwarfCraft was not found. Failing all DCLevel Requirements...");
            return false;
        }

        DCPlayer dcPlayer = dwarfCraft.getDataManager().find(player);
        int skillID = Integer.parseInt((String) map.get("SkillID"));
        int reqLevel = Integer.parseInt((String)map.get("ReqLevel"));

        if (skillID != 0 && reqLevel != 0) {
            int skillLevel = dcPlayer.getSkillLevel(skillID);

            if (skillLevel >= reqLevel) {
                return true;
            }
        }

        return false;
    }

    private DwarfCraft getDwarfCraft(PluginManager pm) {
        Plugin plugin = pm.getPlugin("DwarfCraft");

        //DwarfCraft may not be loaded
        if (plugin == null || !plugin.isEnabled() || !(plugin instanceof DwarfCraft)) {
            return null;
        }

        return (DwarfCraft) plugin;
    }

}
