@echo off
REM Script de compilação para o projeto Hash Extensível
REM Windows PowerShell/CMD

echo ========================================
echo  Compilando Hash Extensivel
echo ========================================
echo.

REM Cria o diretório bin se não existir
if not exist "bin" mkdir bin

REM Compila os arquivos Java
echo [1/2] Compilando arquivos Java...
javac -d bin src/hash/*.java

if %ERRORLEVEL% EQU 0 (
    echo [✓] Compilacao concluida com sucesso!
    echo.
    echo ========================================
    echo  Para executar o programa:
    echo  java -cp bin hash.Main
    echo ========================================
) else (
    echo [✗] Erro na compilacao!
    exit /b 1
)
