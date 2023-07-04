FROM --platform=linux/amd64 quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 AS build
WORKDIR /project
VOLUME ["/project"]
ENTRYPOINT ["native-image"]