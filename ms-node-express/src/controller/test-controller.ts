import express from 'express';
import { validateToken, AuthRequest } from './auth-filter';

const createTestController = function() {

    // Esposizione endpoint di test 
    const app = express();
    const port = 8080;

    app.get('/api/node-express/test/v1', validateToken, (req: AuthRequest, res) => {
        res.send(`Hello World! This is a Node.js endpoint requested by ${req.user}!`);
    });

    app.listen(port, () => {
        return console.log(`Express is listening at http://localhost:${port}`);
    });
}

module.exports = {createTestController};
