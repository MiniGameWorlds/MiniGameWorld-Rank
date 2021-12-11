package com.worldbiomusic.minigameworldrank.observer;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworldrank.observer.MiniGameRankNotifier.RankEvent;

public interface MiniGameRankObserver {
	public void update(MiniGameAccessor minigame, RankEvent event);
}
