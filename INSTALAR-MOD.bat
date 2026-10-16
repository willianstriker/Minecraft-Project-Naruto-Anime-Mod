@echo off
setlocal EnableDelayedExpansion
title Naruto Anime Mod 26.3 - Instalador + Relatorio
color 0B

rem ============================================================
rem  INSTALAR-MOD.bat  -  Naruto Anime Mod [port 26.3 / Forge 66]
rem  Codigo aberto MIT. Nada e apagado: o script apenas COPIA o
rem  jar para a pasta certa e gera um RELATORIO para enviar.
rem ============================================================

set "MC=%APPDATA%\.minecraft"
set "HERE=%~dp0"
set "RELDIR=%HERE%RELATORIO"
set "JAR="
set "JARNAME="
set "JARBYTES=0"
set "DEST="

echo ============================================================
echo    NARUTO ANIME MOD 26.3   -   INSTALADOR + RELATORIO
echo ============================================================
echo.
echo  Feche o Minecraft antes de continuar.
echo.

if not exist "%MC%" (
    echo  [ERRO] Nao encontrei a pasta:
    echo         %MC%
    echo.
    echo  Se o seu Minecraft fica em outro lugar, extraia este
    echo  arquivo para dentro da pasta .minecraft e rode de novo.
    echo.
    pause
    exit /b 1
)
echo  Pasta do Minecraft: %MC%
echo.

rem ---------- 1. localizar o jar ----------
for /f "delims=" %%F in ('dir /b /o-n "%HERE%narutoanimemod*.jar" 2^>nul') do set "JAR=%HERE%%%F"
if not defined JAR for /f "delims=" %%F in ('dir /b /o-n "%USERPROFILE%\Downloads\narutoanimemod*.jar" 2^>nul') do set "JAR=%USERPROFILE%\Downloads\%%F"
if not defined JAR (
    echo  [ATENCAO] Nao encontrei nenhum arquivo narutoanimemod*.jar
    echo            nesta pasta nem na pasta Downloads.
    echo.
    echo  Dica: arraste o arquivo .jar para dentro desta janela
    echo        preta e aperte ENTER.
    echo.
    set /p "JAR=Jar: "
    set "JAR=!JAR:"=!"
)
if defined JAR (
    for %%F in ("!JAR!") do ( set "JARNAME=%%~nxF" & set "JARBYTES=%%~zF" )
    set /a JARKB=JARBYTES/1024
    echo  Arquivo: !JARNAME! - !JARKB! KB
    if !JARBYTES! GTR 524288 (
        echo.
        echo  *** AVISO IMPORTANTE ***
        echo  Este arquivo tem !JARKB! KB. O jar certo do port 26.3 tem
        echo  cerca de 50 KB. Arquivos de 20 MB / 40 MB / 100 MB sao os
        echo  mods ANTIGOS de 1.7.10 ou 1.20 e NAO funcionam no 26.3.
        echo  Use SOMENTE o narutoanimemod-26.3-*.jar do GitHub.
        echo.
    )
    echo.
)

:menu
echo ------------------------------------------------------------
echo  Onde instalar?
echo.
echo    [1] Versao normal Forge 26.3  ....  %MC%\mods
echo    [2] Modpack do TLauncher      ....  %MC%\versions\NOME\mods
echo    [3] Instalar nos dois + relatorio
echo    [4] Somente gerar o RELATORIO - nao instala nada
echo    [0] Sair
echo.
set /p "OP=Digite o numero e aperte ENTER: "
echo.

if "!OP!"=="0" goto :fim
if "!OP!"=="1" goto :inst_raiz
if "!OP!"=="2" goto :inst_pack
if "!OP!"=="3" goto :inst_tudo
if "!OP!"=="4" goto :relatorio
echo  Opcao invalida.
goto :menu

:inst_raiz
set "DEST=%MC%"
call :copiar
goto :relatorio

:inst_pack
echo  Pastas em %MC%\versions :
echo.
set /a N=0
for /d %%D in ("%MC%\versions\*") do (
    set /a N+=1
    set "P[!N!]=%%~nxD"
    echo    [!N!] %%~nxD
)
if !N! EQU 0 (
    echo.
    echo  [ERRO] Nenhuma pasta em %MC%\versions
    echo.
    pause
    goto :menu
)
echo.
echo    [0] voltar ao menu
echo    Dica: o seu modpack tem Naruto no nome, escolha ele.
echo.
set /p "PN=Numero da pasta do modpack: "
if "!PN!"=="0" goto :menu
set "NOME=!P[%PN%]!"
if not defined NOME (
    echo  Numero invalido.
    echo.
    goto :inst_pack
)
set "DEST=%MC%\versions\!NOME!"
call :copiar
goto :relatorio

:inst_tudo
set "DEST=%MC%"
call :copiar
for /d %%D in ("%MC%\versions\*") do (
    set "PUT="
    if exist "%%~fD\mods" set "PUT=1"
    echo %%~nxD | findstr /i /c:"naruto" >nul && set "PUT=1"
    if defined PUT (
        set "DEST=%%~fD"
        call :copiar
    )
)
goto :relatorio

:copiar
if not defined JAR (
    echo  [ERRO] Nenhum jar selecionado. Nada foi instalado.
    echo.
    exit /b 1
)
if not exist "!DEST!" (
    echo  [ERRO] A pasta nao existe: !DEST!
    echo.
    exit /b 1
)
if not exist "!DEST!\mods" mkdir "!DEST!\mods"
copy /y "!JAR!" "!DEST!\mods\" >nul
if errorlevel 1 (
    echo  [ERRO] Falha ao copiar para !DEST!\mods
) else (
    echo  [OK] Instalado em: !DEST!\mods\!JARNAME!
)
echo.
exit /b 0

:relatorio
if not exist "%RELDIR%" mkdir "%RELDIR%"
set "REP=%RELDIR%\RELATORIO.txt"
echo RELATORIO - Naruto Anime Mod 26.3 > "%REP%"
echo Data: %DATE% %TIME% >> "%REP%"
echo Pasta .minecraft: %MC% >> "%REP%"
echo Jar usado: !JAR! >> "%REP%"
echo Ultimo destino instalado: !DEST! >> "%REP%"
echo. >> "%REP%"
echo ==== [A] CONTEUDO DE %MC%\mods ==== >> "%REP%"
if exist "%MC%\mods" (dir "%MC%\mods" >> "%REP%") else (echo PASTA NAO EXISTE >> "%REP%")
echo. >> "%REP%"
echo ==== [B] PASTAS DE VERSAO DO LAUNCHER ==== >> "%REP%"
for /d %%D in ("%MC%\versions\*") do (
    echo --- %%~nxD >> "%REP%"
    if exist "%%~fD\%%~nxD.json" findstr /i /c:"inheritsFrom" "%%~fD\%%~nxD.json" >> "%REP%" 2>nul
    if exist "%%~fD\mods" (
        dir /b "%%~fD\mods" >> "%REP%"
    ) else (
        echo     [sem pasta mods] >> "%REP%"
    )
)
echo. >> "%REP%"
echo ==== [C] LOGS E CRASHES ==== >> "%REP%"
if exist "%MC%\logs\latest.log" (
    copy /y "%MC%\logs\latest.log" "%RELDIR%\LOG-raiz.txt" >nul
    echo copiado: LOG-raiz.txt >> "%REP%"
)
for /d %%D in ("%MC%\versions\*") do (
    set "PUT="
    if exist "%%~fD\mods" set "PUT=1"
    echo %%~nxD | findstr /i /c:"naruto" >nul && set "PUT=1"
    if defined PUT (
        if exist "%%~fD\logs\latest.log" (
            copy /y "%%~fD\logs\latest.log" "%RELDIR%\LOG-%%~nxD.txt" >nul
            echo copiado: LOG-%%~nxD.txt >> "%REP%"
        )
        if exist "%%~fD\crash-reports" (
            xcopy /y /i /e "%%~fD\crash-reports" "%RELDIR%\CRASH-%%~nxD\" >nul 2>nul
            echo copiado: CRASH-%%~nxD >> "%REP%"
        )
    )
)
if exist "%MC%\crash-reports" (
    xcopy /y /i /e "%MC%\crash-reports" "%RELDIR%\CRASH-raiz\" >nul 2>nul
    echo copiado: CRASH-raiz >> "%REP%"
)
echo. >> "%REP%"
echo ==== [D] SISTEMA ==== >> "%REP%"
ver >> "%REP%"
echo. >> "%REP%"
echo ==== FIM ==== >> "%REP%"

echo.
echo ============================================================
echo  PRONTO!
echo.
echo  1. O relatorio esta em: %RELDIR%
echo  2. Abra o Minecraft na versao Forge 26.3 ou no modpack
echo  3. Confira a tela Mods: deve aparecer Naruto Anime Mod
echo  4. Depois de jogar, rode este script de novo e escolha [4]
echo     para gerar um relatorio com o log NOVO.
echo.
echo  Envie no chat: o arquivo RELATORIO.txt + os LOG-*.txt
echo  e um print da tela Mods do jogo.
echo ============================================================
start "" "%RELDIR%"
pause
goto :fim

:fim
endlocal
exit /b 0
