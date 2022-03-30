FROM node:alpine
RUN npm install -g newman newman-reporter-htmlextra
WORKDIR /etc/newman
COPY wait-for.sh .
RUN chmod +x ./wait-for.sh
# RUN chmod +x wait-for.sh
#RUN ["chmod", "+x", "/etc/newman/wait-for.sh"]
  # Set newman as the default container command
ENTRYPOINT ["newman"]
EXPOSE 3307