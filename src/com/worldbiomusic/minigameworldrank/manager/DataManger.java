package com.worldbiomusic.minigameworldrank.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.wbm.plugin.util.data.yaml.YamlHelper;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.wbm.plugin.util.data.yaml.YamlMember;
import com.wbm.plugin.util.instance.BackupDataManager;

public class DataManger implements YamlMember {
	private JavaPlugin plugin;
	private BackupDataManager backupDataManager;
	private YamlManager yamlManager;
	private Map<String, Object> data;
	private int saveBackupDataDelay;

	public DataManger(JavaPlugin plugin, YamlManager yamlManager) {

		this.plugin = plugin;
		this.backupDataManager = new BackupDataManager(this.plugin);
		this.yamlManager = yamlManager;
		
		initData();

		startSavingDataTask();
		this.backupDataManager.startSavingBackupDataTask(this.saveBackupDataDelay);
	}

	private void initData() {
		this.data = new HashMap<>();

		if (!this.data.containsKey("save-backup-data-delay")) {
			this.data.put("save-backup-data-delay", 60);
		}
		this.saveBackupDataDelay = (int) this.data.get("save-backup-data-delay");
	}
	
	public void saveBackupData() {
		this.backupDataManager.saveBackupData();
	}

	public void startSavingDataTask() {
		new BukkitRunnable() {

			@Override
			public void run() {
				yamlManager.saveAllData();
			}
		}.runTaskTimer(this.plugin, 20 * 60 * 10, 20 * 60 * 10);
	}

	@Override
	public String getFileName() {
		return "settings.yml";
	}

	@Override
	public void reload() {
	}

	@Override
	public void setData(YamlManager yamlManager, FileConfiguration config) {
		if (config.isSet("data")) {
			this.data = YamlHelper.ObjectToMap(config.getConfigurationSection("data"));
		}

		config.set("data", this.data);
		
		initData();
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
