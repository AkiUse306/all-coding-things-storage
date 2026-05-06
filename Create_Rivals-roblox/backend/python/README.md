# Python FastAPI Backend

## Run
1. Create venv: `python3 -m venv .venv`
2. Activate: `source .venv/bin/activate`
3. Install: `pip install -r requirements.txt`
4. Start: `uvicorn main:app --reload --port 5002`

## API
- GET `/status`
- POST `/matchmake`
- GET `/player/{id}`
- POST `/player/{id}/score`
