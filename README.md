#  Clínica Médica API  
Solución backend para la gestión de clínicas médicas con funcionalidades de turnos, usuarios y más.

## 🎥 Video explicativo  
👉 Mirá el video del proyecto en [LinkedIn](https://www.linkedin.com/posts/maximiliano-abel-marcos_java-springboot-backenddeveloper-activity-7339410286061178880-EhKK?utm_source=share&utm_medium=member_desktop&rcm=ACoAADwjhKgBFeG7Q3tf0Cxwind36BaqnlIyJkg)

## ✨ Características principales  
- Login y registro de usuarios  
- Búsqueda con filtros  
- Visualización de historial clínico  
- Gestión de turnos y carrito de compras 

## 🛠️ Tecnologías usadas  
- Java 17  
- Spring Boot 3.4.2  
- Maven  
- JPA / Hibernate  
- MySQL  
- Spring Security  
  
## 📜 Documentación API  
Puedes visualizar y probar los endpoints con Swagger UI en:  
(http://localhost:[puerto]/swagger-ui/index.html) 

También puedes usar Postman o cualquier cliente REST para probar la API.💡

## 📂 Endpoints principales

| Método | Endpoint                          | Descripción                         |
|--------|---------------------------------|-----------------------------------|
| GET    | `/pacientes/historial/{dni}`       | Obtener historial de paciente     |
| POST   | `/reservas/filtro`                   | Paciente filtra turnos por prestación |
| POST   | `/admin/turnos`      | Admin crea un turno            |
| PUT   | `/reservas/pacientes/{pacienteId}/turnos/{turnoId}")` | Paciente reserva un turno   |




## 🧾 Funcionalidades implementadas

### Registro / Login

- [x] Iniciar sesión  
- [x] Registrar usuario paciente  
- [x] Registrar usuario admin  

### Paciente

- [x] Crear paciente (admin)  
- [x] Listar pacientes (admin)  
- [x] Buscar paciente por DNI o ID (admin)  
- [x] Actualizar paciente (admin/paciente)  
- [x] Eliminar paciente (admin)  

### Turnos

- [x] Tomar turno (paciente)  
- [x] Listar turnos por especialidad y disponibilidad (paciente)  
- [x] Validaciones para listar turnos según obra social  
- [ ] Cancelar turno (paciente)  
- [x] Validar disponibilidad (evitar superposición)  

### Médico

- [x] Crear médico (admin) 
- [x] Listar médicos
