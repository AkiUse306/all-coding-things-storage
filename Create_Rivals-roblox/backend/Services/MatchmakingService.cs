using RivalBackend.Models;

namespace RivalBackend.Services {
    public class MatchmakingService {
        private readonly List<string> _queue = new();
        private readonly Dictionary<string, string> _matchByPlayer = new();

        public (string MatchId, string Queue) Matchmake(MatchRequest req) {
            if (string.IsNullOrWhiteSpace(req.PlayerId)) {
                throw new ArgumentException("PlayerId is required", nameof(req.PlayerId));
            }
            if (!_queue.Contains(req.PlayerId)) {
                _queue.Add(req.PlayerId);
            }

            if (!_matchByPlayer.TryGetValue(req.PlayerId, out var matchId)) {
                matchId = $"match-{Guid.NewGuid():N}";
                _matchByPlayer[req.PlayerId] = matchId;
            }

            return (matchId, "battle-royale");
        }

        public IEnumerable<string> GetQueue() {
            return _queue.AsReadOnly();
        }

        public string GetMatchId(string playerId) {
            if (playerId == null) return null;
            if (_matchByPlayer.TryGetValue(playerId, out var matchId)) {
                return matchId;
            }
            return null;
        }
    }
}
