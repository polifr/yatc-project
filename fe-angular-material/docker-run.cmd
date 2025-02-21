for %%I in (.) do set CurrDirName=%%~nxI

docker run -p 5001:5001 yatc/%CurrDirName%
