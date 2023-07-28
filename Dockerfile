FROM openjdk:8u171-jre

ENV USERNAME deployee
RUN useradd -ms /bin/bash ${USERNAME}
USER ${USERNAME}

ENV PROJECT_NAME nanhu-print-demo
ENV DEPLOY_DIR /home/${USERNAME}/webroot
ENV VM_OPTION "-Xms128m -Xmx512m"

RUN mkdir -p ${DEPLOY_DIR}
COPY ./fonts /home/${USERNAME}/fonts
COPY ./target/${PROJECT_NAME}.jar ${DEPLOY_DIR}/${PROJECT_NAME}.jar

ENTRYPOINT java -jar ${VM_OPTIONS} -XX:OnOutOfMemoryError="kill -9 %p" ${DEPLOY_DIR}/${PROJECT_NAME}.jar
