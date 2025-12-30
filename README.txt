Red Link – Backend Challenge (MVP)

Este proyecto implementa una API REST como MVP para una campaña de créditos blandos
destinada a empleados de clientes corporativos del banco TuPlataCrece.

El objetivo del MVP es:
- Validar si un empleado (identificado por DNI) es elegible
- Informar el monto de crédito disponible
- Importar diariamente la relación DNI–importe
- Generar un reporte diario de visualizaciones de préstamos




TECNOLOGÍAS UTILIZADAS
- Java 17
- Spring Boot 3
- Maven
- JUnit 5 + MockMvc
- Persistencia en memoria (in-memory)


CÓMO EJECUTAR EL PROYECTO

Requisitos:
- Java 17
- Maven 3.8+

Ejecutar la aplicación:
mvn spring-boot:run

La API queda disponible en:
http://localhost:8080

Ejecutar tests:
mvn clean test


ENDPOINTS DISPONIBLES

LOGIN (validación de elegibilidad)
POST /api/login

Request:
{
  "dni": "12345678"
}

Respuestas:
- 200 OK: DNI reconocido como empleado
- 404 Not Found: DNI no elegible
- 400 Bad Request: DNI vacío o inválido


CONSULTA DE PRÉSTAMO
GET /api/loan/{dni}

Respuestas:
- 200 OK: DNI reconocido
  - con crédito disponible
  - sin crédito disponible
- 404 Not Found: DNI no reconocido

Ejemplo de respuesta:
{
  "dni": "12345678",
  "hasCredit": true,
  "availableAmount": 150000,
  "message": "Crédito disponible"
}

Cada consulta registrada genera una visualización, utilizada luego para el
reporte diario.


IMPORTACIÓN DIARIA DE EMPLEADOS
POST /api/admin/import

Tipo: multipart/form-data

Archivo CSV con formato:
dni,amount
12345678,150000
87654321,0

Comportamiento:
- Limpia la información previa
- Carga la nueva relación DNI–importe
- Simula la recepción diaria del archivo del banco

Respuesta:
- 200 OK si la importación fue exitosa


REPORTE DIARIO DE VISUALIZACIONES
GET /api/admin/report?date=YYYY-MM-DD

- Si no se envía date, se utiliza la fecha actual
- Devuelve un archivo CSV descargable con los DNIs que visualizaron el préstamo
  ese día

Formato del CSV:
dni,viewedAt
12345678,2025-12-30T10:15:22


TESTS

El proyecto incluye tests para:
- Controllers (WebMvcTest)
- Servicios (unit tests)
- Casos felices y de error (validaciones, no elegibles, etc.)

El objetivo es asegurar:
- Correctos códigos HTTP
- Correcta orquestación de capas
- Reglas de negocio encapsuladas en services


DECISIONES DE DISEÑO

- Persistencia en memoria: elegida por simplicidad y por tratarse de un MVP
- Separación por capas: controller / service / repository / model / dto
- Sin autenticación real: el login se limita a validar elegibilidad
- Importación manual vía endpoint: simula el proceso diario sin schedulers
- Reporte en CSV: formato simple y alineado con el requerimiento del banco


POSIBLES MEJORAS (FUERA DEL ALCANCE DEL MVP)

- Persistencia en base de datos
- Job batch o scheduler para importación y reportes diarios
- Seguridad (auth real)
- Manejo de errores más granular
- Métricas y observabilidad


Desarrollado por Ricardo Gastón Núñez
