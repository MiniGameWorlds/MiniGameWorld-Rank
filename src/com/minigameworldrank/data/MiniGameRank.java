package com.minigameworldrank.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.wbm.plugin.util.data.yaml.YamlManager;
import com.wbm.plugin.util.data.yaml.YamlMember;
import com.minigameworld.api.MiniGameAccessor;

public class MiniGameRank implements YamlMember {

	private MiniGameAccessor templateGame;
	private List<RankData> rankData;

	public MiniGameRank(MiniGameAccessor minigame) {
		this.templateGame = minigame;
		this.rankData = new ArrayList<>();
	}

	public List<RankData> getRankData() {
		return this.rankData;
	}

	public RankData getPlayersRankData(List<Player> players) {
		for (RankData rankData : this.rankData) {
			// if players team
			if (rankData.isSamePlayers(players)) {
				return rankData;
			}
		}
		return null;
	}

	public void saveRank(MiniGameAccessor newGame) {
		// check with exist rank
		checkWithExistRank(newGame);

		// Renew ranks
		sortRankOrders();
	}

	/**
	 * 1. check same players exist, if exist: process, if not exist: add new rank
	 * data <br>
	 * 2-1. if this score < exist score: do nothing <br>
	 * 2-2. if this score < exist score: process <br>
	 * 3. remove exist rank data and add new rank data<br>
	 */
	private void checkWithExistRank(MiniGameAccessor newGame) {
		List<? extends com.minigameworld.frames.helpers.MiniGameRank> gameRanks = newGame.rank();

		OUT: for (com.minigameworld.frames.helpers.MiniGameRank newRank : gameRanks) {

			for (RankData oldRank : this.rankData) {
				// if rank with the same players exist
				if (oldRank.isSamePlayers(newRank.getPlayers())) {
					// if new rank score is bigger than old rank score
					if (newRank.getScore() > oldRank.getScore()) {
						this.rankData.remove(oldRank);
						this.rankData.add(new RankData(newRank));
					}
					continue OUT;
				}
			}

			// if rank with the same players not exist
			this.rankData.add(new RankData(newRank));
		}
	}

	public void sortRankOrders() {
		// sort by score
		Collections.sort(this.rankData);

		// renew ranks order
		for (int i = 0; i < this.rankData.size(); i++) {
			this.rankData.get(i).setRank(i + 1);
		}
	}

	public MiniGameAccessor getMinigame() {
		return this.templateGame;
	}

	@Override
	public String getFileName() {
		return "data" + File.separator + templateGame.className() + ".yml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setData(YamlManager yamlManager, FileConfiguration config) {
		if (config.isSet("data")) {
//			this.data = YamlHelper.ObjectToMap(config.getConfigurationSection("data"));
			this.rankData = (List<RankData>) config.getList("data");
		}

		config.set("data", this.rankData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() == obj.getClass()) {
			return this.templateGame.isSameTemplate(((MiniGameRank) obj).templateGame);
		}
		return false;
	}

}
