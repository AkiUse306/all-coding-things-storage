// Tab switching
function showTab(tabName) {
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.getElementById(tabName).classList.add('active');

    document.querySelectorAll('.menu a').forEach(link => link.classList.remove('active'));
    event.target.classList.add('active');
}

// Update connection status
async function updateStatus() {
    try {
        await API.healthCheck();
        document.getElementById('connectionStatus').className = 'status-dot online';
        document.getElementById('statusText').textContent = 'Connected';
    } catch {
        document.getElementById('connectionStatus').className = 'status-dot offline';
        document.getElementById('statusText').textContent = 'Disconnected';
    }
}

// Dashboard functions
async function refreshDashboard() {
    updateStatus();
    refreshQueue();
}

async function refreshQueue() {
    try {
        const data = await API.getQueue();
        const queue = data.queue || [];
        document.getElementById('queueCount').textContent = queue.length;
    } catch (error) {
        console.error(error);
        document.getElementById('queueCount').textContent = 'Error';
    }
}

// Player functions
async function searchPlayer() {
    const id = document.getElementById('playerId').value;
    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    try {
        const player = await API.getPlayer(id);
        document.getElementById('pId').textContent = player.playerId;
        document.getElementById('pScore').textContent = player.score;
        document.getElementById('pLevel').textContent = player.level;
        document.getElementById('playerInfo').style.display = 'block';
    } catch (error) {
        alert('Player not found: ' + error.message);
    }
}

async function addScore() {
    const id = document.getElementById('playerId').value;
    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    try {
        const result = await API.addScore(id, 10);
        document.getElementById('pScore').textContent = result.score;
        alert('Score updated!');
    } catch (error) {
        alert('Failed to update score: ' + error.message);
    }
}

async function updateLevel() {
    const id = document.getElementById('playerId').value;
    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    try {
        const result = await API.updateStats(id, { level: 5 });
        document.getElementById('pLevel').textContent = result.level;
        alert('Level updated!');
    } catch (error) {
        alert('Failed to update level: ' + error.message);
    }
}

// Matchmaking functions
async function createMatch() {
    const id = document.getElementById('matchPlayerId').value;
    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    try {
        const match = await API.createMatch(id);
        document.getElementById('mId').textContent = match.matchId;
        document.getElementById('mQueue').textContent = match.queue;
        document.getElementById('matchResult').style.display = 'block';
        refreshQueue();
    } catch (error) {
        alert('Failed to create match: ' + error.message);
    }
}

// Stats functions
async function getStats() {
    const id = document.getElementById('statsPlayerId').value;
    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    try {
        const stats = await API.getPlayer(id);
        document.getElementById('sId').textContent = stats.playerId;
        document.getElementById('sScore').textContent = stats.score;
        document.getElementById('sLevel').textContent = stats.level;
        document.getElementById('statsInfo').style.display = 'block';
    } catch (error) {
        alert('Failed to fetch stats: ' + error.message);
    }
}

async function updateStats() {
    const id = document.getElementById('statsPlayerId').value;
    const newScore = parseInt(document.getElementById('newScore').value);

    if (!id) {
        alert('Please enter a Player ID');
        return;
    }

    if (isNaN(newScore)) {
        alert('Please enter a valid score');
        return;
    }

    try {
        const result = await API.updateStats(id, { score: newScore });
        document.getElementById('sScore').textContent = result.score;
        document.getElementById('sLevel').textContent = result.level;
        alert('Stats updated!');
    } catch (error) {
        alert('Failed to update stats: ' + error.message);
    }
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    updateStatus();
    setInterval(updateStatus, 5000);
    setInterval(refreshQueue, 10000);
});