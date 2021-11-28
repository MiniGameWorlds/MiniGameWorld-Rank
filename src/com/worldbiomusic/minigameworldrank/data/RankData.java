package com.worldbiomusic.minigameworldrank.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.worldbiomusic.minigameworld.minigameframes.helpers.MiniGameRankComparable;

public class RankData implements ConfigurationSerializable, Comparable<RankData> {
	private int rank;
	private int score;
	private List<PlayerData> players;

	public RankData(MiniGameRankComparable comp) {
		this.rank = 0;
		this.score = comp.getScore();
		this.players = new ArrayList<>();
		comp.getPlayers().forEach(p -> this.players.add(new PlayerData(p)));
	}

	public RankData(int rank, int score, List<PlayerData> players) {
		this.rank = rank;
		this.score = score;
		this.players = players;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("rank", this.rank);
		data.put("score", this.score);
		data.put("players", this.players);
		return data;
	}

	public static RankData deserialize(Map<String, Object> data) {
		int rank = (int) data.get("rank");
		int score = (int) data.get("score");
		@SuppressWarnings("unchecked")
		List<PlayerData> players = (List<PlayerData>) data.get("players");

		return new RankData(rank, score, players);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<PlayerData> getPlayers() {
		return players;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(RankData other) {
		return other.getScore() - getScore();
	}

	public boolean containsPlayer(Player p) {
		if (p == null) {
			return false;
		}

		for (PlayerData pData : this.players) {
			if (pData.getUUID().equals(p.getUniqueId())) {
				return true;
			}
		}

		return false;
	}

	public boolean isSamePlayers(List<Player> otherPlayers) {
		// check players count
		if (this.players.size() != otherPlayers.size()) {
			return false;
		}

		OUT: for (PlayerData pData : this.players) {
			for (Player p : otherPlayers) {
				if (pData.getUUID().equals(p.getUniqueId())) {
					continue OUT;
				}
			}
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		String playerString = "";
		for (PlayerData pData : getPlayers()) {
			playerString += pData.getName() + ", ";
		}

		playerString = playerString.substring(0, playerString.length() - 2);

		return String.format("[%d] %s: " + ChatColor.GOLD + "%d", getRank(), playerString, getScore());
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
