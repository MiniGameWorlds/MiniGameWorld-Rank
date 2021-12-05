package com.worldbiomusic.minigameworldrank.api;

import java.util.List;

import org.bukkit.entity.Player;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworldrank.data.MiniGameRank;
import com.worldbiomusic.minigameworldrank.manager.MiniGameRankManager;

public class MiniGameWorldRank {
	private static MiniGameWorldRank instance = new MiniGameWorldRank();
	private MiniGameRankManager rankManager;

	public static MiniGameWorldRank create() {
		return instance;
	}

	private MiniGameWorldRank() {
		this.rankManager = null;
	}

	/**
	 * Only called once
	 * 
	 * @param rankManager
	 */
	public void setMiniGameManager(MiniGameRankManager rankManager) {
		if (this.rankManager == null) {
			this.rankManager = rankManager;
		}
	}

	public List<MiniGameRank> getMiniGameRankList() {
		return this.rankManager.getMiniGameRankList();
	}

	public MiniGameRank getMiniGameRank(MiniGameAccessor accessor) {
		return this.rankManager.getMiniGameRank(accessor);
	}

	public void saveAllData() {
		this.rankManager.saveAllData();
	}

	public void syncPlayerDataName(Player p) {
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
//
//
//
//
//
//
//
//
