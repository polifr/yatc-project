from fastapi import FastAPI
from fastapi.responses import Response
from prometheus_client import generate_latest, CONTENT_TYPE_LATEST
import asyncio

app = FastAPI(title="Microservice Resource Server")

@app.get("/health")
async def health():
    return {"status": "ok"}

@app.get("/metrics")
async def metrics():
    return Response(generate_latest(), media_type=CONTENT_TYPE_LATEST)

@app.get("/api/python-fastapi/test/v1")
async def test():
    return "Hello, Python! This is a microservice!"
