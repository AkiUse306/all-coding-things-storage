using AI.CodeAssistant.Domain.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace AI.CodeAssistant.API.Controllers;

[ApiController]
[Route("api/ai")]
[Authorize]
public class AIController : ControllerBase
{
    private readonly ICodeAnalysisService _service;

    public AIController(ICodeAnalysisService service)
    {
        _service = service;
    }

    [HttpPost("analyze")]
    public async Task<IActionResult> Analyze([FromBody] string code)
    {
        if (string.IsNullOrEmpty(code))
            return BadRequest("Code cannot be empty");

        var result = await _service.AnalyzeAsync(code);
        return Ok(new { analysis = result });
    }
}