const API_BASE = 'http://localhost:3000/api';

class API {
    static async getPlayer(id) {
        try {
            const response = await fetch(`${API_BASE}/player/${id}`);
            if (!response.ok) throw new Error('Player not found');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async addScore(id, amount) {
        try {
            const response = await fetch(`${API_BASE}/player/${id}/score`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ add: amount })
            });
            if (!response.ok) throw new Error('Failed to add score');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async updateStats(id, stats) {
        try {
            const response = await fetch(`${API_BASE}/player/${id}/stats`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(stats)
            });
            if (!response.ok) throw new Error('Failed to update stats');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async createMatch(playerId) {
        try {
            const response = await fetch(`${API_BASE}/matchmake`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ playerId })
            });
            if (!response.ok) throw new Error('Failed to create match');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async getQueue() {
        try {
            const response = await fetch(`${API_BASE}/matchmake/queue`);
            if (!response.ok) throw new Error('Failed to fetch queue');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async healthCheck() {
        try {
            const response = await fetch(`${API_BASE}/health`);
            if (!response.ok) throw new Error('Health check failed');
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
}