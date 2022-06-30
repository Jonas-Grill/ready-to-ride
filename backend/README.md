# Overview
## Infos
Runs on deno version 1.22.0
## How to start app
### With docker
1. Create .env file with same fields as the .env.example file
2. Run docker compose -f .\docker-compose.yaml -f .\docker-compose.local.yaml up --build

### Without docker
1. Create .env file with same fields as the .env.example file
2. Start mongodb
3. deno run --allow-net --allow-write --allow-read --allow-env main.ts