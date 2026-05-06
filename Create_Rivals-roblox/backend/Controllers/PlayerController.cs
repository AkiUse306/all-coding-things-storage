using Microsoft.AspNetCore.Mvc;
using RivalBackend.Models;
using RivalBackend.Services;

namespace RivalBackend.Controllers {
    [ApiController]
    [Route("player")]
    public class PlayerController : ControllerBase {
        private readonly PlayerStatsService _stats;

        public PlayerController(PlayerStatsService stats) {
            _stats = stats;
        }

        [HttpGet("{id}")]
        public IActionResult Get(string id) {
            var stat = _stats.GetOrCreate(id);
            return Ok(stat);
        }

        [HttpPost("{id}/score")]
        public IActionResult AddScore(string id, [FromBody] ScoreUpdate update) {
            if (update == null) return BadRequest(new { error = "Missing score payload" });
            var stat = _stats.AddScore(id, update.Add);
            return Ok(stat);
        }

        [HttpPost("{id}/stats")]
        public IActionResult UpdateStats(string id, [FromBody] PlayerStatsUpdate update) {
            if (update == null) return BadRequest(new { error = "Missing stats payload" });
            var stat = _stats.UpdateStats(id, update);
            return Ok(stat);
        }
    }
}
