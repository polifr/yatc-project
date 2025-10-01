FROM node:24-alpine

RUN mkdir -p /home/node/app/node_modules && chown -R node:node /home/node/app

WORKDIR /home/node/app

COPY package*.json ./ 
RUN chown -R node:node /home/node/app/package*.json

USER node

RUN npm install

COPY --chown=node:node . .

EXPOSE 8080

RUN addgroup -S yatcusr && adduser -S yatcusr -G yatcusr
USER yatcusr

CMD [ "node", "app.js" ]
