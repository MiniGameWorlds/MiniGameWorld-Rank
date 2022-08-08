package com.minigameworldrank;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.minigameworldrank.api.MiniGameWorldRank;
import com.minigameworldrank.cmd.Commands;
import com.minigameworldrank.data.PlayerData;
import com.minigameworldrank.data.RankData;
import com.minigameworldrank.listener.CommonListener;
import com.minigameworldrank.manager.DataManger;
import com.minigameworldrank.manager.MiniGameRankManager;
import com.wbm.plugin.util.Metrics;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.minigameworld.util.Utils;

public class MiniGameWorldRankMain extends JavaPlugin {
	private MiniGameRankManager rankManager;
	private YamlManager yamlManager;
	private DataManger dataManger;
	private CommonListener commonListener;
	private Commands commands;

	@Override
	public void onEnable() {
		super.onEnable();

		Utils.info(ChatColor.GREEN + "MiniGameWorld Rank ON");
		
		// bstats
		new Metrics(this, 14384);

		// register custom ConfigurationSerializables
		ConfigurationSerialization.registerClass(PlayerData.class);
		ConfigurationSerialization.registerClass(RankData.class);

		this.yamlManager = new YamlManager(getDataFolder());

		this.rankManager = new MiniGameRankManager(this, this.yamlManager);
		this.dataManger = new DataManger(this, this.yamlManager);
		this.commonListener = new CommonListener(this.rankManager);
		getServer().getPluginManager().registerEvents(this.commonListener, this);

		// commands
		this.commands = new Commands(this.yamlManager);
		getCommand("rank").setExecutor(this.commands);

		// Init API
		MiniGameWorldRank mwRank = MiniGameWorldRank.create();
		mwRank.setMiniGameManager(this.rankManager);

		// register listener
		getServer().getPluginManager().registerEvents(this.rankManager, this);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		this.rankManager.removeNotExistMiniGameRankConfig();

		// save all data
		this.rankManager.saveAllData();

		// save backup data
		this.dataManger.saveBackupData();

		Utils.info(ChatColor.RED + "MiniGameWorld Rank OFF");
	}
}
