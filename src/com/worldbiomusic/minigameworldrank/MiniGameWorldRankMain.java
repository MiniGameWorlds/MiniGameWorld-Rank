package com.worldbiomusic.minigameworldrank;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.wbm.plugin.util.data.yaml.YamlManager;
import com.worldbiomusic.minigameworld.util.Utils;
import com.worldbiomusic.minigameworldrank.data.PlayerData;
import com.worldbiomusic.minigameworldrank.data.RankData;
import com.worldbiomusic.minigameworldrank.listener.CommonListener;
import com.worldbiomusic.minigameworldrank.manager.DataManger;
import com.worldbiomusic.minigameworldrank.manager.MiniGameRankManager;

public class MiniGameWorldRankMain extends JavaPlugin {
	private MiniGameRankManager rankManager;
	private YamlManager yamlManager;
	private DataManger dataManger;
	private CommonListener commonListener;

	@Override
	public void onEnable() {
		super.onEnable();

		Utils.info(ChatColor.GREEN + "MiniGameWorldRankMain ON");

		// register custom ConfigurationSerializables
		ConfigurationSerialization.registerClass(PlayerData.class);
		ConfigurationSerialization.registerClass(RankData.class);

		this.yamlManager = new YamlManager(getDataFolder());

		this.rankManager = new MiniGameRankManager(this, this.yamlManager);
		this.dataManger = new DataManger(this, this.yamlManager);
		this.commonListener = new CommonListener(this.rankManager);
		getServer().getPluginManager().registerEvents(this.commonListener, this);

	}

	@Override
	public void onDisable() {
		super.onDisable();

		this.rankManager.removeNotExistMiniGameRankConfig();

		// save all data
		this.rankManager.saveAllData();

		// save backup data
		this.dataManger.saveBackupData();

		Utils.info(ChatColor.RED + "MiniGameWorldRankMain OFF");
	}
}
