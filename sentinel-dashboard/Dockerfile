### phase 1
FROM openjdk:8u275

COPY target/sentinel-dashboard.jar /opt/

ARG PROPERTY_FILE=BOOT-INF/classes/application.properties

RUN cd /opt \
    && jar xf sentinel-dashboard.jar ${PROPERTY_FILE} \
    && sed -i 's/nacos.addr=.*/nacos.addr=${NACOS_ADDRESS:127.0.0.1:8848}/g' ${PROPERTY_FILE} \
    && sed -i 's/nacos.namespace=.*/nacos.namespace=${NACOS_NAMESPACE:sentinel_namespace}/g' ${PROPERTY_FILE} \
    && sed -i 's/id.dataCenterId=.*/id.dataCenterId=${ID_DATA_CENTER_ID:0}/g' ${PROPERTY_FILE} \
    && sed -i 's/id.workerId=.*/id.workerId=${ID_WORKER_ID:0}/g' ${PROPERTY_FILE} \
    && jar uf sentinel-dashboard.jar ${PROPERTY_FILE}

### phase 2
FROM openjdk:8u275-jre

COPY --from=0  /opt/sentinel-dashboard.jar /opt/

CMD ["java", "-Dserver.port=8080", "-Dcsp.sentinel.dashboard.server=localhost:8080", "-Dproject.name=sentinel-dashboard", "-jar", "/opt/sentinel-dashboard.jar"]

EXPOSE 8080 8719