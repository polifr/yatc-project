for %%I in (.) do set CurrDirName=%%~nxI

docker save %CurrDirName% > %CurrDirName%.tar
