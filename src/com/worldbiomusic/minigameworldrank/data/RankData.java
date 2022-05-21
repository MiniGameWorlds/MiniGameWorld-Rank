package com.worldbiomusic.minigameworldrank.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 * Contains rank in the config, score, played players<br>
 * Sorted by score when a pluing loaded or new RankData saved
 */
public class RankData implements ConfigurationSerializable, Comparable<RankData>, Cloneable {
	/**
	 * Rank in the config files
	 */
	private int rank;

	/**
	 * Score
	 */
	private int score;

	/**
	 * Played player list
	 */
	private List<PlayerData> players;

	/**
	 * Extracts info from MiniGameRankComparable
	 * 
	 * @param comp MiniGameRankComparable
	 */
	public RankData(com.worldbiomusic.minigameworld.minigameframes.helpers.MiniGameRank comp) {
		this.rank = 0;
		this.score = comp.getScore();
		this.players = new ArrayList<>();
		comp.getPlayers().forEach(p -> this.players.add(new PlayerData(p)));
	}

	/**
	 * For serialization constructor
	 * 
	 * @param rank
	 * @param score
	 * @param players
	 */
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

	/**
	 * Deserialize method
	 * 
	 * @param data
	 * @return
	 */
	public static RankData deserialize(Map<String, Object> data) {
		int rank = (int) data.get("rank");
		int score = (int) data.get("score");
		@SuppressWarnings("unchecked")
		List<PlayerData> players = (List<PlayerData>) data.get("players");

		return new RankData(rank, score, players);
	}

	/**
	 * Gets rank
	 * 
	 * @return Rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Sets Rank (with API, not applied)
	 * 
	 * @param rank Rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Gets played player list
	 * 
	 * @return Player list
	 */
	public List<PlayerData> getPlayers() {
		return players;
	}

	/**
	 * Gets score
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(RankData other) {
		return other.getScore() - getScore();
	}

	/**
	 * Check this rank data is related with a player
	 * 
	 * @param p Player to check
	 * @return True if rank data related with a player
	 */
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

	/**
	 * Check this rank data is related with this all players
	 * 
	 * @param otherPlayers Players to check
	 * @return True if all of players are related with the rank data
	 */
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

	@Override
	public Object clone() {
		RankData rankData = null;
		try {
			rankData = (RankData) super.clone();

			// copy PlayerData
			List<PlayerData> copiedPlayers = new ArrayList<>();
			for (PlayerData originPData : this.players) {
				copiedPlayers.add((PlayerData) originPData.clone());
			}
			rankData.players = copiedPlayers;

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return rankData;
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
