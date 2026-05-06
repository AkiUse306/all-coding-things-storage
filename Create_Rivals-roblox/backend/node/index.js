const express = require('express');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

const players = {};

app.get('/status', (req, res) => res.json({ status: 'ok', now: new Date().toISOString() }));

app.post('/matchmake', (req, res) => {
  const { PlayerId } = req.body;
  if (!PlayerId) return res.status(400).json({ error: 'PlayerId required' });
  return res.json({ matchId: `match-${Date.now()}`, queue: 'battle-royale' });
});

app.get('/player/:id', (req, res) => {
  const id = req.params.id;
  if (!players[id]) players[id] = { PlayerId: id, Score: 0, Level: 1 };
  return res.json(players[id]);
});

app.post('/player/:id/score', (req, res) => {
  const id = req.params.id;
  const add = Number(req.body.Add || 0);
  if (!players[id]) players[id] = { PlayerId: id, Score: 0, Level: 1 };
  players[id].Score += add;
  players[id].Level = Math.max(1, Math.floor(players[id].Score / 100) + 1);
  return res.json(players[id]);
});

app.listen(5001, () => console.log('Node backend running on http://localhost:5001'));
