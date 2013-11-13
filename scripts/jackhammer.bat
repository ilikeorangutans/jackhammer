@ECHO OFF

F "%JACKHAMMER_HOME%" == "" GOTO NEED-PATH

java -jar "%JACKHAMMER_HOME%\cli-0.0.2.jar" %*
GOTO END

:NEED-PATH
echo "Set JACKHAMMER_HOME path variable, please."

:END
