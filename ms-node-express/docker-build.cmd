for %%I in (.) do set CurrDirName=%%~nxI

docker build -t %CurrDirName% .
