package com.minigameworldrank.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minigameworldrank.manager.MiniGameRankManager;

public class CommonListener implements Listener {
	private MiniGameRankManager rankManager;

	public CommonListener(MiniGameRankManager rankManager) {
		this.rankManager = rankManager;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		this.rankManager.syncPlayerDataName(p);
	}
}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
