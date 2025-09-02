import express from 'express';

var createTestController = function() {

    // Esposizione endpoint di test 
    const app = express();
    const port = 8080;

    app.get('/api/node-express/test/v1', (req, res) => {
    res.send('Hello World! This is a Node.js endpoint!')
    });

    app.listen(port, () => {
    return console.log(`Express is listening at http://localhost:${port}`);
    });
}

module.exports = {createTestController};
