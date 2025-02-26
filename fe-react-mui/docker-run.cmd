for %%I in (.) do set CurrDirName=%%~nxI

docker run -p 3001:3001 yatc/%CurrDirName%
