from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import Dict

app = FastAPI()

class MatchRequest(BaseModel):
    PlayerId: str

class ScoreUpdate(BaseModel):
    Add: int

class PlayerStats(BaseModel):
    PlayerId: str
    Score: int
    Level: int

store: Dict[str, PlayerStats] = {}

@app.get('/status')
def status():
    return { 'status': 'ok' }

@app.post('/matchmake')
def matchmake(data: MatchRequest):
    if not data.PlayerId:
        raise HTTPException(status_code=400, detail='PlayerId required')
    return { 'matchId': f"match-{data.PlayerId}-{len(store)+1}", 'queue': 'battle-royale' }

@app.get('/player/{player_id}')
def get_player(player_id: str):
    if player_id not in store:
        store[player_id] = PlayerStats(PlayerId=player_id, Score=0, Level=1)
    return store[player_id]

@app.post('/player/{player_id}/score')
def add_score(player_id: str, update: ScoreUpdate):
    if player_id not in store:
        store[player_id] = PlayerStats(PlayerId=player_id, Score=0, Level=1)
    store[player_id].Score += update.Add
    store[player_id].Level = max(1, store[player_id].Score // 100 + 1)
    return store[player_id]
