const express = require('express')
const app = express()

app.get('/', function (req, res) {
  res.send('Hello World! This is a Node.js endpoint!')
})

app.listen(8080)
