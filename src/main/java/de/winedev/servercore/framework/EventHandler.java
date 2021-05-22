package de.winedev.servercore.framework;

import de.winedev.servercore.ServerCore;
import de.winedev.servercore.events.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventHandler {

  static Plugin sc = ServerCore.pl;

  public static void init() {
    regEvents(new EVENT_BlockBrake());
    regEvents(new EVENT_BlockPlace());
    regEvents(new EVENT_PlayerMove());
    regEvents(new EVENT_PlayerJoin());
    regEvents(new EVENT_PlayerChat());
    regEvents(new EVENT_PlayerDamageByEntiy());
    regEvents(new EVENT_PlayerAttackEntiy());
    regEvents(new EVENT_PlayerInteractWithEntity());
    regEvents(new EVENT_PlayerInteract());
    //regEvents(new EVENT_ServerListPing());
  }

  private static void regEvents(Listener event) {
    sc.getServer().getPluginManager().registerEvents(event, sc);
    System.out.println("Das Event " + event.getClass().getSimpleName() + " wurde regestriert");
  }
}
