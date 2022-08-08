package com.minigameworldrank.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;
import com.minigameworld.api.MiniGameAccessor;
import com.minigameworld.api.MiniGameWorld;
import com.minigameworld.api.observer.MiniGameObserver;
import com.minigameworld.api.observer.MiniGameTimingNotifier.Timing;
import com.minigameworld.events.minigame.MiniGameFinishEvent;
import com.minigameworld.util.Utils;
import com.minigameworldrank.data.MiniGameRank;
import com.minigameworldrank.data.PlayerData;
import com.minigameworldrank.data.RankData;
import com.minigameworldrank.util.Setting;
import com.wbm.plugin.util.data.yaml.YamlManager;

import net.md_5.bungee.api.ChatColor;

public class MiniGameRankManager implements MiniGameObserver, Listener {

	private JavaPlugin plugin;
	private MiniGameWorld mw;
	private YamlManager yamlManager;
	private List<MiniGameRank> rankList;

	public MiniGameRankManager(JavaPlugin plugin, YamlManager yamlManager) {
		/*
		 * 1. Load rank data
		 * 2. Check misseed minigames and create new rank data config
		 * 3. Delete removed minigame rank data config
		 */

		this.plugin = plugin;
		this.rankList = new ArrayList<>();

		this.yamlManager = yamlManager;

		// LATEST
		this.mw = MiniGameWorld.create(MiniGameWorld.API_VERSION);
		this.mw.registerObserver(this);
	}

	public void saveAllData() {
		this.yamlManager.saveAllData();
	}

	/**
	 * Save rank when finish
	 * 
	 * @param e Event when a minigame has finished
	 */
	@EventHandler
	public void onMiniGameFinish(MiniGameFinishEvent e) {
		MiniGameAccessor minigame = e.getMiniGame();
		saveRank(minigame);
	}

	@Override
	public void update(MiniGameAccessor minigame, Timing event) {
		// load exist minigame rank data (if not exist, create new config)
		if (event == Timing.REGISTRATION) {
			MiniGameRank rank = new MiniGameRank(minigame);

			this.rankList.add(rank);
			this.yamlManager.registerMember(rank);

			// sort rank orders
			rank.sortRankOrders();
		} else if (event == Timing.UNREGISTRATION) {
			MiniGameRank rank = null;
			for (MiniGameRank r : this.rankList) {
				if (r.getMinigame().isSameTemplate(minigame)) {
					rank = r;
				}
			}

			// save data and unregister
			if (rank != null) {
				this.yamlManager.save(rank);
				this.yamlManager.unregisterMember(rank);
				this.rankList.remove(rank);
			}
		}
	}

	private void saveRank(MiniGameAccessor minigame) {
		MiniGameRank minigameRank = null;

		// save rank
		for (MiniGameRank rank : this.rankList) {
			if (rank.getMinigame().isSameTemplate(minigame)) {
				minigameRank = rank;
				rank.saveRank(minigame);
			}
		}

		// print rank
		for (com.minigameworld.frames.helpers.MiniGameRank team : minigame.getRank()) {
			RankData rankData = minigameRank.getPlayersRankData(team.getPlayers());
			printSurroundRanks(minigameRank, rankData, team.getPlayers());
		}
	}

	private void printSurroundRanks(MiniGameRank minigameRank, RankData teamRankData, List<Player> teamPlayers) {

		int teamRank = teamRankData.getRank();
		List<RankData> surroundRankData = new ArrayList<>();
		for (int i = teamRank - Setting.SURROUNDED_UP_RANK_COUNT; i < teamRank + Setting.SURROUNDED_DOWN_RANK_COUNT
				+ 1; i++) {
			int listIndex = i - 1;
			if (0 <= listIndex && listIndex < minigameRank.getRankData().size()) {
				surroundRankData.add(minigameRank.getRankData().get(listIndex));
			}
		}

		teamPlayers.forEach(p -> {
			p.sendMessage(ChatColor.BOLD + "\n[Rank]");
			surroundRankData.forEach(r -> p.sendMessage(r.toString()));
		});

	}

	public void removeNotExistMiniGameRankConfig() {
		List<String> allMiniGameString = new ArrayList<>();
		this.mw.getTemplateGames().forEach(m -> allMiniGameString.add(m.getClassName()));

		File rankDataFolder = new File(this.plugin.getDataFolder(), "data");

		for (File minigameFile : rankDataFolder.listFiles()) {
			String flieName = Files.getNameWithoutExtension(minigameFile.getName());
			if (!allMiniGameString.contains(flieName)) {
				Utils.warning("Delete mingame rank config: " + flieName);

				// remove file
				minigameFile.delete();
				// add removed list
				allMiniGameString.add(minigameFile.getName());
			}
		}
	}

	/**
	 * Sync player's name in all data config
	 * 
	 * @param p Player to sync name
	 */
	public void syncPlayerDataName(Player p) {
		for (MiniGameRank rank : this.rankList) {
			List<RankData> rankDataList = rank.getRankData();
			for (RankData rankData : rankDataList) {
				for (PlayerData pData : rankData.getPlayers()) {
					// if player's name changed
					if (p.getUniqueId().equals(pData.getUUID()) && !p.getName().equals(pData.getName())) {
						pData.setName(p.getName());
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<MiniGameRank> getMiniGameRankList() {
		return (ArrayList<MiniGameRank>) ((ArrayList<MiniGameRank>) this.rankList).clone();
	}

	public MiniGameRank getMiniGameRank(MiniGameAccessor accessor) {
		for (MiniGameRank rank : this.rankList) {
			if (rank.getMinigame().isSameTemplate(accessor)) {
				return rank;
			}
		}
		return null;
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
