using AI.CodeAssistant.Application.Services;

namespace AI.CodeAssistant.UnitTests;

public class CodeAnalysisServiceTests
{
    [Fact]
    public async Task AnalyzeAsync_ReturnsAnalysis()
    {
        // Arrange
        var service = new CodeAnalysisService();

        // Act
        var result = await service.AnalyzeAsync("public class Test {}");

        // Assert
        Assert.NotNull(result);
        Assert.Contains("Analysis", result);
    }
}
