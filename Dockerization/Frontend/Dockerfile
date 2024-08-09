# Frontend Containerfile

FROM docker.io/library/node

LABEL description="Frontend image"

ENV FRONTEND_SERVER_PORT=3000

EXPOSE $FRONTEND_SERVER_PORT

COPY ./front_lastVersion/online-platform/ /opt/source-code

WORKDIR /opt/source-code

RUN npm install 

CMD npm start --host=0.0.0.0 --port=3000 
