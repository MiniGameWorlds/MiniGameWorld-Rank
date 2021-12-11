package com.worldbiomusic.minigameworldrank.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworldrank.data.MiniGameRank;
import com.worldbiomusic.minigameworldrank.data.RankData;
import com.worldbiomusic.minigameworldrank.manager.MiniGameRankManager;
import com.worldbiomusic.minigameworldrank.observer.MiniGameRankObserver;

/**
 * MiniGameRank API <br>
 * [IMPORTANT] This plugin always works with latest MiniGameWorld API version
 * {@link com.worldbiomusic.minigameworld#MiniGameWorld}
 *
 */
public class MiniGameWorldRank {
	private static MiniGameWorldRank instance = new MiniGameWorldRank();
	private MiniGameRankManager rankManager;

	/**
	 * Gets MiniGameRank API instance
	 * 
	 * @return MiniGameWorldRank instance
	 */
	public static MiniGameWorldRank create() {
		return instance;
	}

	private MiniGameWorldRank() {
		this.rankManager = null;
	}

	/**
	 * Don't use this <br>
	 * Only called once when plugin enabled
	 * 
	 * @param rankManager
	 */
	public void setMiniGameManager(MiniGameRankManager rankManager) {
		if (this.rankManager == null) {
			this.rankManager = rankManager;
		}
	}

	/**
	 * Returns RankData clone (copied data)
	 * 
	 * @param accessor MiniGame
	 * @return RankData List
	 */
	public List<RankData> getRankDataList(MiniGameAccessor accessor) {
		MiniGameRank rank = this.rankManager.getMiniGameRank(accessor);
		List<RankData> rankDataListClone = new ArrayList<>();

		for (RankData rankData : rank.getRankData()) {
			rankDataListClone.add((RankData) rankData.clone());
		}

		return rankDataListClone;
	}

	/**
	 * Saves all rank data to configs
	 */
	public void saveAllData() {
		this.rankManager.saveAllData();
	}

	/**
	 * Sync a player's name in all rank data configs
	 * 
	 * @param p Player to sync name
	 */
	public void syncPlayerDataName(Player p) {
		this.rankManager.syncPlayerDataName(p);
	}

	/**
	 * Registers a rank observer
	 * 
	 * @param obs Observer to register
	 * @see {@link MiniGameRankObserver}
	 */
	public void registerRankObserver(MiniGameRankObserver obs) {
		this.rankManager.registerObserver(obs);
	}

	/**
	 * Unregisters a rank observer
	 * 
	 * @param obs Observer to unregister
	 * @see {@link MiniGameRankObserver}
	 */
	public void unregisterRankObserver(MiniGameRankObserver obs) {
		this.rankManager.unregisterObserver(obs);
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
