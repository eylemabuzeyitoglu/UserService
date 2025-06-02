# User Service Microservice

## 📝 Proje Açıklaması
User Service, Blog Web uygulamasının kullanıcı yönetimi işlemlerini gerçekleştiren bir mikroservistir. Kullanıcı kaydı, giriş, profil yönetimi ve şifre işlemleri gibi temel kullanıcı işlemlerini yönetir.

## 🚀 Teknolojiler
- Java 17
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- RabbitMQ
- JWT Authentication
- Docker
- Maven

## 🏗️ Mimari Yapı
```
src/main/java/com/BlogWebApp/UserService/
├── controller/    # REST API endpoints
├── service/       # İş mantığı katmanı
├── repository/    # Veritabanı işlemleri
├── model/         # Veri modelleri
├── mapper/        # DTO dönüşümleri
├── config/        # Yapılandırma sınıfları
├── security/      # Güvenlik yapılandırması
├── events/        # Event handling
└── exceptions/    # Özel exception sınıfları
```

## 🔧 Gereksinimler
- JDK 17
- Maven
- Docker ve Docker Compose
- PostgreSQL 14+
- RabbitMQ 3.12+

## 🛠️ Kurulum

### 1. Projeyi Klonlama
```bash
git clone [repository-url]
cd UserService
```



### 2. Uygulama Özellikleri
`src/main/resources/application.properties` dosyasında aşağıdaki yapılandırmaları kontrol edin:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/user-service
spring.datasource.username=postgres
spring.datasource.password=1234

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 3. Uygulamayı Çalıştırma
```bash
mvn spring-boot:run
```

## 🔐 API Endpoints

### Kullanıcı İşlemleri
- `POST /api/user` - Yeni kullanıcı oluşturma
- `PATCH /api/user` - Kullanıcı bilgilerini güncelleme (userId query param ile)
- `DELETE /api/user/{id}` - Kullanıcı silme
- `PATCH /api/user/{id}` - Şifre sıfırlama (userId path param ve password query param ile)

## 🔄 Event Yapısı
Servis, aşağıdaki eventleri RabbitMQ üzerinden yayınlar:
- `user.created` - Yeni kullanıcı oluşturulduğunda
- `user.updated` - Kullanıcı güncellendiğinde
- `user.deleted` - Kullanıcı silindiğinde
- `user.resetPassword` - Şifre sıfırlama işlemi yapıldığında

## 🔒 Güvenlik
- JWT tabanlı kimlik doğrulama
- Şifreleme ve güvenlik önlemleri
- Role-based access control (RBAC)

## 🧪 Test
```bash
mvn test
```

## 📦 Docker ile Çalıştırma
```bash
docker build -t user-service .
docker run -p 8082:8082 user-service
```

