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
        this.setName("DwarfCraft Level Requirement");
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

        System.out.print("Player: " + player.getDisplayName());
        System.out.print("Testing Reqs...");

        if (pm == null) {
            System.out.print("PM not found");
            return false;
        }

        DwarfCraft dwarfCraft = getDwarfCraft(pm);
        if (dwarfCraft == null) {
            System.out.print("DC Not Found");

            server.getConsoleSender().sendMessage("[DCLevelReq] DwarfCraft was not found. Failing all DCLevel Requirements...");
            return false;
        }

        DCPlayer dcPlayer = new DCPlayer(dwarfCraft, player);
        int skillID = Integer.parseInt((String) map.get("SkillID"));
        int reqLevel = Integer.parseInt((String)map.get("ReqLevel"));

        System.out.print("Race: " + dcPlayer.getRace());

        if (skillID != 0 && reqLevel != 0) {
            System.out.print("SkillID: " + skillID);
            int skillLevel = dcPlayer.getSkillLevel(skillID);

            System.out.print("SkillLevel: " + skillLevel);
            System.out.print("ReqLevel: " + reqLevel);

            if (skillLevel >= reqLevel) {
                System.out.print("Test Passed");

                return true;
            }
        }


        System.out.print("Test Failed");
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
