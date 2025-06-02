🎬 CLÍNICA MÉDICA API 🎬

¡Bienvenido! Este proyecto es una solución orientada a la gestión de clínicas médicas.

✨ Características ✨
Login
Filtros de búsqueda
Visualización de historial clínico.
Obtener turno / carro de compras
🛠️ Construido con 🛠️
Java 17
Spring Boot 3.4.2
Maven
JPA
Hibernate
MySQL
Spring Security

📜 Documentación de la API

Swagger está habilitado para visualizar y probar los endpoints.

Swagger UI: http://localhost:9001/swagger-ui/index.html

💡 Puedes usar Postman o cualquier cliente REST para probar los endpoints.

📂 Endpoints principales

| Método | Endpoint       | Descripción                          |
|--------|--------------|--------------------------------------|
| POST | `/register-customer` | Registro de usuario       |
| POST | `/login` | Login usuario       |
| GET    | `/paciente/historial/{id}`    | Obtener historial de paciente         | 
| POST    | `/reserva/crear` | Paciente inicia el proceso de compra         |
| POST   | `/prestacion/obtener-turno `   | Elección de prestación          |
| POST    | `/reserva/{pacienteId}/{prestacionId}` | Prestaciones se agregan al carrito      |


🧾 Registro/login:
 - [x] Iniciar sesión

 - [x] Registrar usuario paciente
 - [x] Registrar usuario admin
 Paciente:

 - [x] Crear paciente (admin)

 - [x] Listar pacientes (admin)

 - [x] Buscar paciente por DNI o ID (admin)

 - [] Actualizar paciente (admin/paciente)

 - [] Eliminar paciente (admin)

🗓️ Turno:
 - [X] Crear/agendar turno

 - [X] Listar turnos por especialidad y disponibilidad

 - [] Cancelar turno

 - [] Validar disponibilidad (evitar superposición de turnos)

📄 Consulta médica:
 - [] Crear consulta (al final del turno)

 - [] Listar consultas por paciente

 - [] Ver detalle de una consulta

👨‍⚕️ Médico:
 - [] Crear médico

 - [] Listar médicos

 - [] Consultar disponibilidad