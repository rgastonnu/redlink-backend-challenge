```mermaid
sequenceDiagram
    autonumber
    actor Banco as Banco (Admin)
    actor User as Empleado
    participant API as Backend API
    participant Import as ImportService
    participant Auth as AuthService
    participant Loan as LoanService
    participant Repo as Repository (In-Memory)
    participant Report as ReportService

    box "Proceso Diario (Inicio)" #f9f9f9
    Banco->>API: POST /api/admin/import (CSV)
    API->>Import: importCsv(file)
    Import->>Repo: clear()
    Import->>Repo: saveAll(empleados)
    Import-->>API: OK
    API-->>Banco: 200 OK (Datos actualizados)
    end

    box "Interacción Usuario" #e6f7ff
    User->>API: POST /api/login (dni)
    API->>Auth: login(dni)
    Auth->>Repo: findByDni(dni)
    alt DNI No Existe
        Repo-->>Auth: empty
        Auth-->>API: Exception
        API-->>User: 404 Not Found
    else DNI Existe
        Repo-->>Auth: EmployeeData
        Auth-->>API: OK
        API-->>User: 200 OK (Login Exitoso)
    end

    User->>API: GET /api/loan/{dni}
    API->>Loan: getLoanOfferFor(dni)
    Loan->>Repo: findByDni(dni)
    Repo-->>Loan: EmployeeCredit
    
    note right of Loan: Registro de Auditoría
    Loan->>Repo: saveView(dni, now())
    
    Loan-->>API: LoanResponse (monto, mensaje)
    API-->>User: 200 OK (Detalle Préstamo)
    end

    box "Proceso Diario (Cierre)" #f9f9f9
    Banco->>API: GET /api/admin/report?date=hoy
    API->>Report: buildDailyReportCsv(date)
    Report->>Repo: findViewsByDate(date)
    Repo-->>Report: List<LoanView>
    Report-->>API: byte[] (CSV Content)
    API-->>Banco: 200 OK (Descarga Reporte)
    end
```
