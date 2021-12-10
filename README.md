# Description
- Print and save rank data when minigame finished

# Features
- Use `MiniGameWorld` API
- Rank always save with highest score
- Rank data access API (READ-ONLY)
- Auto backup
- Only manage exist minigame rank data with config file(delete removed minigame rank config automatically)
- config: settings.yml, data/<minigame>.yml
- Sync player name with uuid when join the server
- Save rank data sorted by score

# Print
```yaml
[34] A, B: 45
[35] A, C: 43
[36] <you>, C: 36
[37] T, B: 24
[38] H, D: 24
```

# config
## settings.yml
```yaml
data:
  save-backup-data-delay: 60
  surrounded-up-rank-count: 2
  surrounded-down-rank-count: 2
```
- ``: 
- ``: 
- ``: 

## data/<minigame>.yml
```yaml
data:
    - {
      rank: <rank>
      score: <score>
      players:
        - {name: <name>, uuid: <uuid>}
        - {name: <name>, uuid: <uuid>}
      }
```