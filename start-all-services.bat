@echo off
setlocal

:: Set full path to Maven executable
set MAVEN_PATH="C:\The Maven 3.9.10\apache-maven-3.9.10\bin\mvn.cmd"

:: Delay function (in seconds)
set DELAY=10

echo Starting Eureka Server...
start "EurekaServer" cmd /k "cd /d %~dp0eurekaServer && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting APIGateway...
start "APIGateway" cmd /k "cd /d %~dp0APIGateway && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting UserService...
start "UserService" cmd /k "cd /d %~dp0AuthLoginRegister && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting AuthService...
start "AuthService" cmd /k "cd /d %~dp0AuthService && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting DoctorAvailabilityManagement...
start "DoctorAvailablityManagement" cmd /k "cd /d %~dp0DoctorAvailablityManagement && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting AppointmentBookingModule...
start "AppointmentBookingModule" cmd /k "cd /d %~dp0appointmentBookingModule && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting Consultations...
start "Consultations" cmd /k "cd /d %~dp0Consultations\Consultations && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo Starting Notification...
start "Notification" cmd /k "cd /d %~dp0notification && %MAVEN_PATH% spring-boot:run"
timeout /t %DELAY% /nobreak >nul

echo All services are launching in separate windows...

endlocal
