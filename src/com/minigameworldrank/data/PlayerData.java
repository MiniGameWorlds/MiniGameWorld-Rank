package com.minigameworldrank.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 * Can gets player's name, uuid<br>
 * [IMPORTANT] player's name in all config data can be changed when
 * <code>MiniGameWorldRank#syncPlayerDataName()</code> is called
 */
public class PlayerData implements ConfigurationSerializable, Cloneable {

	/**
	 * Player's name
	 */
	private String name;
	/**
	 * Player's fixed Unique ID
	 */
	private UUID uuid;

	public PlayerData(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId();
	}

	public PlayerData(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("name", this.name);
		data.put("uuid", this.uuid.toString());
		return data;
	}

	/**
	 * For deserialization
	 * 
	 * @param data
	 * @return
	 */
	public static PlayerData deserialize(Map<String, Object> data) {
		String name = (String) data.get("name");
		UUID uuid = UUID.fromString((String) data.get("uuid"));

		return new PlayerData(name, uuid);
	}

	/**
	 * Gets player name
	 * 
	 * @return Player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets player name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets player UUID
	 * 
	 * @return
	 */
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass().equals(obj.getClass())) {
			return uuid.equals(((PlayerData) obj).uuid);
		}
		return false;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return (PlayerData) super.clone();
	}
}
