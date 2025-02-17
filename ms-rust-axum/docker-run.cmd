for %%I in (.) do set CurrDirName=%%~nxI

docker run -p 8081:8081 %CurrDirName%
