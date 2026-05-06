using Microsoft.AspNetCore.Mvc;
using RivalBackend.Models;
using RivalBackend.Services;

namespace RivalBackend.Controllers {
    [ApiController]
    [Route("matchmake")]
    public class MatchmakingController : ControllerBase {
        private readonly MatchmakingService _mm;

        public MatchmakingController(MatchmakingService mm) {
            _mm = mm;
        }

        [HttpPost]
        public IActionResult Post([FromBody] MatchRequest req) {
            if (req == null || string.IsNullOrWhiteSpace(req.PlayerId)) {
                return BadRequest(new { error = "PlayerId is required" });
            }
            var result = _mm.Matchmake(req);
            return Ok(new { matchId = result.MatchId, queue = result.Queue });
        }

        [HttpGet("queue")]
        public IActionResult GetQueue() {
            return Ok(new { queue = _mm.GetQueue() });
        }
    }
}
