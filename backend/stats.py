from memory import SessionLocal, Message

def get_stats():
    db = SessionLocal()
    try:
        total = db.query(Message).count()
        return {
            "total_messages": total,
            "status": "ok"
        }
    finally:
        db.close()
