# Script PowerShell para compilar e executar o Hash Extensível
# Use: .\executar_completo.ps1

Write-Host "╔════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   HASH EXTENSÍVEL - Compilar e Executar   ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Verificar se Java está instalado
Write-Host "[1/3] Verificando instalação do Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "✓ Java encontrado: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Java não encontrado!" -ForegroundColor Red
    Write-Host "  Instale o Java JDK 17+ e adicione ao PATH" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Compilar
Write-Host "[2/3] Compilando código-fonte..." -ForegroundColor Yellow

# Criar diretório bin se não existir
if (-not (Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
    Write-Host "  Diretório 'bin' criado" -ForegroundColor Gray
}

# Compilar
try {
    javac -d bin src/hash/*.java 2>&1 | Out-Null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Compilação concluída com sucesso!" -ForegroundColor Green
    } else {
        Write-Host "✗ Erro na compilação!" -ForegroundColor Red
        javac -d bin src/hash/*.java
        exit 1
    }
} catch {
    Write-Host "✗ Erro ao compilar!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}

Write-Host ""

# Executar
Write-Host "[3/3] Executando programa..." -ForegroundColor Yellow
Write-Host "════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

java -cp bin hash.Main

Write-Host ""
Write-Host "════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "Programa finalizado!" -ForegroundColor Green
