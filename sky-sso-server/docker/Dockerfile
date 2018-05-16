FROM java:8

MAINTAINER tx

ENV BOOT_ARGS=""

ADD zyk-sso-server-1.0.1-SNAPSHOT.jar /home/zyk-sso-server.jar

WORKDIR /home

CMD java -Djava.security.egd=file:/dev/./urandom ${BOOT_ARGS} -jar zyk-sso-server.jar --spring.profiles.active=dev

EXPOSE  8086