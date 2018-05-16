FROM java:8

MAINTAINER tx

ENV BOOT_ARGS=""

ADD zyk-sso-client-1.0.1-SNAPSHOT.jar /home/zyk-sso-client.jar

WORKDIR /home

CMD java -Djava.security.egd=file:/dev/./urandom ${BOOT_ARGS} -jar zyk-sso-client.jar --spring.profiles.active=dev

EXPOSE  8087