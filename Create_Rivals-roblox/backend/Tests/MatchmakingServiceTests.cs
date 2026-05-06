using RivalBackend.Models;
using RivalBackend.Services;
using Xunit;

namespace RivalBackend.Tests {
    public class MatchmakingServiceTests {
        [Fact]
        public void Matchmake_PersistsMatchIdForPlayer() {
            var service = new MatchmakingService();
            var req = new MatchRequest { PlayerId = "player1" };
            var first = service.Matchmake(req);
            var second = service.Matchmake(req);

            Assert.Equal(first.MatchId, second.MatchId);
            Assert.Contains("player1", service.GetQueue());
            Assert.Equal(first.MatchId, service.GetMatchId("player1"));
        }
    }
}