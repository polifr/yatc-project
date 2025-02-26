for %%I in (.) do set CurrDirName=%%~nxI

docker save yatc/%CurrDirName% > %CurrDirName%.tar
