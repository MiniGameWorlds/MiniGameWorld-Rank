package com.worldbiomusic.minigameworldrank.observer;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;

public interface MiniGameRankNotifier {
	public enum RankEvent {
		AFTER_DATA_SAVED;
	}

	public void registerObserver(MiniGameRankObserver observer);

	public void unregisterObserver(MiniGameRankObserver observer);

	public void notifyObservers(MiniGameAccessor minigame, RankEvent event);
}
