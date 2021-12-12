# Description
- Print and save the rank data

# Features
- Depend on [MiniGameWorld] (always use LATEST API)
- Also can use with [MiniGameReward] to give reward by saved rank data
- Rank always save with the highest score
- Other plugin can use rank data access API (READ-ONLY)
- Auto backup
- Only manage exist minigame rank data with config file(delete removed minigame rank config automatically)
- config: `settings.yml`, `data/#minigame.yml`
- Sync player name with uuid of all data when a player join the server
- Save rank data sorted by score

# Commands
- `/rank reload`: Reload all config from file
- `/rank save`: Save all data to file

# Rank print example
```yaml
[Rank]
[34] A, B: 45
[35] A, O, C: 43
[36] <you>, C: 36
[37] T, B: 24
[38] H, D, Z: 24
```

# API Usage
- [API doc](https://minigameworlds.github.io/MiniGameWorld-Rank/) ([list](https://github.com/MiniGameWorlds/MiniGameWorld-Rank/docs/README.md))
## Print all minigame rank info
```java
// get MiniGameWorld api instance
MiniGameWorld mw = MiniGameWorld.create("x.x.x");

// get MiniGameWorldRank api instance
MiniGameWorldRank minigameRank = MiniGameWorldRank.create();

// get all minigame
for (MiniGameAccessor minigame : mw.getMiniGameList()) {
  // get minigame rank data list
  List<RankData> rankDataList = minigameRank.getRankDataList(minigame);
  rankDataList.forEach(rank -> {
    // print rank info
    System.out.println("========================");
    System.out.println("Rank: " + rank.getRank());
    System.out.println("Score: " + rank.getScore());
    System.out.print("Players: ");
    rank.getPlayers().forEach(p -> System.out.println(p.getName() + ", "));
  });
}
```





# config
## settings.yml
```yaml
data:
  save-backup-data-delay: 60
  surrounded-up-rank-count: 2
  surrounded-down-rank-count: 2
```
- `save-backup-data-delay`: Data save delay (min) (>= 0)
- `surrounded-up-rank-count`: Visible upper rank count when finished the game (>= 0)
- `surrounded-down-rank-count`: Visible below rank count when finished the game (>= 0)

## data/#minigame.yml
```yaml
data:
- ==: com.worldbiomusic.minigameworldrank.data.RankData
  rank: 1
  score: 21
  players:
  - ==: com.worldbiomusic.minigameworldrank.data.PlayerData
    name: worldbiomusic
    uuid: ae5f2897-0b94-4aeb-94a1-e2f8ed4a9544
- ==: com.worldbiomusic.minigameworldrank.data.RankData
  rank: 2
  score: 6
  players:
  - ==: com.worldbiomusic.minigameworldrank.data.PlayerData
    name: Realniceness
    uuid: abe2212f-29cb-45fe-8da6-012c5ea0b799
```
- Data will be saved every data backup delay or when the server stops
- If edit rank data, plugin could not work


[MiniGameWorld]: https://github.com/MiniGameWorlds/MiniGameWorld
[MiniGameReward]: https://github.com/MiniGameWorlds/MiniGameWorld-Reward


