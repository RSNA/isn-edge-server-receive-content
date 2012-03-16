${atChar}echo off

SET RSNA_ROOT=%~dp0

java -Xmx1024M -jar "%RSNA_ROOT%\${project.build.finalName}.jar"

