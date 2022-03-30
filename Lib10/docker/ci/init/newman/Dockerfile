FROM node:alpine

#COPY --chmod=777 wait-for.sh ./etc
#the --chmod option requires BuildKit. Refer to https://docs.docker.com/go/buildkit/ to learn how to build images with BuildKit enabled
COPY wait-for.sh ./etc
RUN ["chmod", "+x", "/etc/wait-for.sh"]
RUN npm install -g newman newman-reporter-htmlextra
WORKDIR /etc/newman
#RUN chmod +x wait-for.sh
# RUN chmod +x wait-for.sh
#RUN ["chmod", "+x", "/etc/newman/wait-for.sh"]
  # Set newman as the default container command
ENTRYPOINT ["newman"]
EXPOSE 3307