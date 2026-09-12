from sqlalchemy import create_engine, Column, Integer, String, Text, DateTime
from sqlalchemy.orm import declarative_base, sessionmaker
from datetime import datetime
import os

DATABASE_URL = os.getenv("DATABASE_URL", "sqlite:///./memory.db")

engine = create_engine(DATABASE_URL, connect_args={"check_same_thread": False} if "sqlite" in DATABASE_URL else {})
SessionLocal = sessionmaker(bind=engine, autoflush=False, autocommit=False)
Base = declarative_base()

class Message(Base):
    __tablename__ = "messages"
    id = Column(Integer, primary_key=True, index=True)
    role = Column(String(20), nullable=False)
    content = Column(Text, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

def init_db():
    Base.metadata.create_all(bind=engine)

def save_message(role: str, content: str):
    db = SessionLocal()
    try:
        msg = Message(role=role, content=content)
        db.add(msg)
        db.commit()
    finally:
        db.close()

def get_recent_messages(limit: int = 20):
    db = SessionLocal()
    try:
        return db.query(Message).order_by(Message.created_at.desc()).limit(limit).all()[::-1]
    finally:
        db.close()
