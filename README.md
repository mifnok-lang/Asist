# AI Assistant

Android AI assistant with backend memory, stats, and Codemagic CI.

## Backend
```bash
cd backend
pip install -r requirements.txt
uvicorn main:app --reload --host 0.0.0.0 --port 8000
