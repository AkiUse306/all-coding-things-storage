# Backend Core Service (Multi-framework)

This repository includes backend templates for multiple frameworks:

- C# (ASP.NET Core)
- Node.js (Express)
- Python (FastAPI)

## C# Setup
1. `cd backend`
2. `dotnet run`
3. API runs on `http://localhost:5000`

## Node Setup
1. `cd backend/node`
2. `npm install`
3. `npm start`
4. API runs on `http://localhost:5001`

## Python Setup
1. `cd backend/python`
2. `python3 -m venv .venv`
3. `source .venv/bin/activate`
4. `pip install -r requirements.txt`
5. `uvicorn main:app --reload --port 5002`

## Shared API contract
All backends expose these endpoints:
- `GET /status`
- `POST /matchmake` (JSON: `{ "PlayerId": "123" }`)
- `GET /player/{id}`
- `POST /player/{id}/score` (JSON: `{ "Add": 10 }`)

## Roblox integration
Use `HttpService` with the backend URL in `RobloxProject/ServerScriptService/ExternalCoreIntegration.lua`.

## Notes
- Run one backend at a time and set `BASE_URL` accordingly.
- C# uses ASP.NET controllers; Node uses Express; Python uses FastAPI.
