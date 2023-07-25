#!/bin/sh

echo "The application will start in ${START_DELAY_SECS}s..." && sleep ${START_DELAY_SECS}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "org.ylzl.eden.demo.LayerApplication" "$@"
