import express from 'express';
import { collectDefaultMetrics, register } from 'prom-client';

collectDefaultMetrics();

// Esposizione endpoint per monitoraggio su /metrics
const monitoring = express();
monitoring.get('/metrics', async (_req, res) => {
  try {
    res.set('Content-Type', register.contentType);
    res.end(await register.metrics());
  } catch (err) {
    res.status(500).end(err);
  }
});
monitoring.listen(4001, '0.0.0.0');

// Esposizione endpoint di test 
const app = express();
app.get('/api/node-express/test/v1', function (req, res) {
  res.send('Hello World! This is a Node.js endpoint!')
});
app.listen(8080)
