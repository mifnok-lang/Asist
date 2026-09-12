import httpx

async def web_search(query: str) -> str:
    async with httpx.AsyncClient(timeout=30) as client:
        return f"Search placeholder for: {query}"
