from fastapi import FastAPI
import asyncio

app = FastAPI(title="Microservice Resource Server")

@app.get("/health")
async def health():
    return {"status": "ok"}

@app.get("/api/python-fastapi/test/v1")
async def test():
    return "Hello, Python! This is a microservice!"
