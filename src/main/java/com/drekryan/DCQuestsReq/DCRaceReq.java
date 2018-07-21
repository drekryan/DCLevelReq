package com.drekryan.DCQuestsReq.DCRaceReq;

import com.Jessy1237.DwarfCraft.DCPlayer;
import com.Jessy1237.DwarfCraft.DwarfCraft;

import me.blackvein.quests.CustomRequirement;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Map;

public class DCRaceReq extends CustomRequirement
{

    public DCRaceReq()
    {
        this.setName( "DCRaceReq" );
        this.setAuthor( "Drekryan" );
        this.addData( "ReqRace" );
        this.addDescription( "ReqRace", "The required DwarfCraft race the player must be." );
    }

    @Override public boolean testRequirement( Player player, Map<String, Object> map )
    {
        Server server = player.getServer();
        PluginManager pm = server.getPluginManager();

        if ( pm == null )
        {
            return false;
        }

        DwarfCraft dwarfCraft = getDwarfCraft( pm );
        if ( dwarfCraft == null )
        {
            server.getConsoleSender().sendMessage( "[DCQuestsReq] DwarfCraft was not found. Failing all DCRace Requirements..." );
            return false;
        }

        DCPlayer dcPlayer = dwarfCraft.getDataManager().find( player );
        String raceReq = ( String ) map.get( "ReqRace" );

        if ( !raceReq.equals( "" ) )
        {
            String race = dcPlayer.getRace();

            if ( !race.equals( "NULL" ) && race.toLowerCase().equals( raceReq.toLowerCase() ) )
            {
                return true;
            }
        }

        return false;
    }

    private DwarfCraft getDwarfCraft( PluginManager pm )
    {
        Plugin plugin = pm.getPlugin( "DwarfCraft" );

        //DwarfCraft may not be loaded
        if ( plugin == null || !plugin.isEnabled() || !( plugin instanceof DwarfCraft ) )
        {
            return null;
        }

        return ( DwarfCraft ) plugin;
    }

}