for %%I in (.) do set CurrDirName=%%~nxI

docker build -t yatc/%CurrDirName% -f Dockerfile.multi .
