using RivalBackend.Models;
using RivalBackend.Services;
using Xunit;

namespace RivalBackend.Tests {
    public class PlayerStatsServiceTests {
        [Fact]
        public void UpdateStats_UpdatesScoreAndLevel() {
            var service = new PlayerStatsService();
            var id = "player2";

            var updated = service.UpdateStats(id, new PlayerStatsUpdate { Score = 500 });

            Assert.Equal(500, updated.Score);
            Assert.Equal(6, updated.Level); // 500 / 100 + 1

            updated = service.UpdateStats(id, new PlayerStatsUpdate { Level = 10 });
            Assert.Equal(10, updated.Level);
        }
    }
}