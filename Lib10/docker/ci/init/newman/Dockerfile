FROM node:alpine

COPY wait-for.sh ./etc
RUN ["chmod", "+x", "/etc/wait-for.sh"]
RUN npm install -g newman newman-reporter-htmlextra
WORKDIR /etc/newman

ENTRYPOINT ["newman"]
EXPOSE 3307