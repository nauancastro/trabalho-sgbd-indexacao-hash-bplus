@echo off
REM Script para executar o programa Hash Extensível
REM Windows PowerShell/CMD

echo ========================================
echo  Executando Hash Extensivel
echo ========================================
echo.

if not exist "bin\hash\Main.class" (
    echo [!] Programa nao compilado!
    echo [!] Execute compilar.bat primeiro
    exit /b 1
)

java -cp bin hash.Main
