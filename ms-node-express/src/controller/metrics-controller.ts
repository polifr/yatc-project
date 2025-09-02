import express from 'express';
import { collectDefaultMetrics, register } from 'prom-client';

const createMetricsController = function() {

    collectDefaultMetrics();

    // Esposizione endpoint per monitoraggio su /metrics
    const monitoring = express();
    const port = 4001;

    monitoring.get('/metrics', async (_req, res) => {
        try {
            res.set('Content-Type', register.contentType);
            res.end(await register.metrics());
        } catch (err) {
            res.status(500).end(err);
        }
    });
    monitoring.listen(port, '0.0.0.0');
}

module.exports = {createMetricsController};
