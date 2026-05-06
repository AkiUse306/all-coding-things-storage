using RivalBackend.Models;

namespace RivalBackend.Services {
    public class PlayerStatsService {
        private readonly Dictionary<string, PlayerStats> _store = new();

        public PlayerStats GetOrCreate(string playerId) {
            if (!_store.TryGetValue(playerId, out var stat)) {
                stat = new PlayerStats { PlayerId = playerId, Score = 0, Level = 1 };
                _store[playerId] = stat;
            }
            return stat;
        }

        public PlayerStats AddScore(string playerId, int add) {
            var stat = GetOrCreate(playerId);
            stat.Score += add;
            stat.Level = Math.Max(1, stat.Score / 100 + 1);
            return stat;
        }

        public PlayerStats UpdateStats(string playerId, PlayerStatsUpdate update) {
            var stat = GetOrCreate(playerId);
            if (update == null) return stat;

            if (update.Score.HasValue) {
                stat.Score = update.Score.Value;
                stat.Level = Math.Max(1, stat.Score / 100 + 1);
            }

            if (update.Level.HasValue) {
                stat.Level = Math.Max(1, update.Level.Value);
            }

            return stat;
        }
    }
}
