from fastapi import FastAPI, WebSocket
from pydantic import BaseModel
from memory import init_db, save_message, get_recent_messages
from stats import get_stats

app = FastAPI(title="AI Assistant Backend")

class ChatRequest(BaseModel):
    message: str

@app.on_event("startup")
def startup():
    init_db()

@app.post("/chat")
async def chat(req: ChatRequest):
    save_message("user", req.message)
    reply = f"Received: {req.message}"
    save_message("assistant", reply)
    return {
        "reply": reply,
        "history": [
            {"role": m.role, "content": m.content}
            for m in get_recent_messages(10)
        ]
    }

@app.get("/stats")
async def stats():
    return get_stats()

@app.websocket("/ws/stats")
async def ws_stats(ws: WebSocket):
    await ws.accept()
    while True:
        await ws.send_json(get_stats())
