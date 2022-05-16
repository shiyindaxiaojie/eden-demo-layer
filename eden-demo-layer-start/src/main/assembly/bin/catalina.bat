@echo off

set APP_NAME=eden-demo-layer-start
set APP_DIR=..\modules
set APP_PATTERN=%APP_DIR%\%APP_NAME%*.jar
for /f "delims=\" %%a in ('dir /b /a-d /o-d %APP_PATTERN%') do (
     set APP_RUNNING_BIN=%APP_DIR%\%%a
)


if "%1" == "start" (
     if exist "%JAVA_HOME%" (
          echo JAVA_HOME: %JAVA_HOME%
          echo JAVA_VERSION:
          java -version
     )
     echo start %APP_NAME%
     start "%APP_NAME%" java -server -Xms512m -Xmx512m -Dfile.encoding=UTF-8 -jar %APP_RUNNING_BIN% --spring.config.additional-location=../config/ --logging.file.path=../logs/
) else if "%1" == "stop" (
     echo stop %APP_NAME%
     taskkill /fi "WINDOWTITLE eq %APP_NAME%"
)
