const http = require('http');
const https = require('https');

const app = require('./app');

const port = process.env.PORT || 4000;

const server = http.createServer(app);

server.listen(port);