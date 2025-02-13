for %%I in (.) do set CurrDirName=%%~nxI

docker run -p 8080:8080 %CurrDirName%
