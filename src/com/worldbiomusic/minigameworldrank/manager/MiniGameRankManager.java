package com.worldbiomusic.minigameworldrank.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworld.api.MiniGameWorld;
import com.worldbiomusic.minigameworld.observer.MiniGameEventNotifier.MiniGameEvent;
import com.worldbiomusic.minigameworld.observer.MiniGameObserver;
import com.worldbiomusic.minigameworld.util.Utils;
import com.worldbiomusic.minigameworldrank.data.MiniGameRank;

public class MiniGameRankManager implements MiniGameObserver {
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
		this.mw.registerMiniGameObserver(this);
	}

	public void saveAllData() {
		this.yamlManager.saveAllData();
	}

	@Override
	public void update(MiniGameAccessor minigame, MiniGameEvent event) {
		// save rank data
		if (event == MiniGameEvent.FINISH) {
			this.rankList.forEach(rank -> {
				if (rank.getMinigame().getClassName().equals(minigame.getClassName())) {
					rank.saveRank();
				}
			});
		}
		// load exist minigame rank data (if not exist, create new config)
		else if (event == MiniGameEvent.REGISTRATION) {
			MiniGameRank rank = new MiniGameRank(minigame);

			this.rankList.add(rank);
			this.yamlManager.registerMember(rank);
		} else if (event == MiniGameEvent.UNREGISTRATION) {
			MiniGameRank rank = null;
			for (MiniGameRank r : this.rankList) {
				if (r.getMinigame().equals(minigame)) {
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

	public void removeNotExistMiniGameRankConfig() {
		List<String> allMiniGameString = new ArrayList<>();
		this.mw.getMiniGameList().forEach(m -> allMiniGameString.add(m.getClassName()));

		File rankDataFolder = new File(this.plugin.getDataFolder(), "data");

		for (File minigameFile : rankDataFolder.listFiles()) {
			String flieName = Files.getNameWithoutExtension(minigameFile.getName());
			if (!allMiniGameString.contains(flieName)) {
				Utils.debug("Delete mingame rank config: " + flieName);

				// remove file
				minigameFile.delete();
				// add removed list
				allMiniGameString.add(minigameFile.getName());
			}
		}

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
