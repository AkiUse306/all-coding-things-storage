#!/bin/bash

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║  🚀 Create Rivals - Local Dashboard & Backend Startup     ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

BACKEND_DIR="/workspaces/rg-Create_Rivals/backend"
DASHBOARD_DIR="/workspaces/rg-Create_Rivals/local"

echo -e "${BLUE}Starting backend server on port 5000...${NC}"
echo "Command: dotnet run --project $BACKEND_DIR/Matchmaker.csproj"
echo ""

# Start backend in background
cd "$BACKEND_DIR" && dotnet run --project Matchmaker.csproj &
BACKEND_PID=$!

# Wait for backend to start
sleep 3

echo ""
echo -e "${BLUE}Starting dashboard on port 3000...${NC}"
echo "Command: dotnet run --project $DASHBOARD_DIR/LocalDashboard.csproj"
echo ""

# Start dashboard in foreground
cd "$DASHBOARD_DIR" && dotnet run

# Cleanup on exit
trap "kill $BACKEND_PID 2>/dev/null" EXIT
