# Vehicle Registry API

A system that downloads, processes, and stores vehicle information from Ukraine's open government registries (data.gov.ua). The main purpose is to verify vehicle parameters and view registration history by license plate number. The system also checks vehicles for theft status and provides the ability to generate registration statistics based on specified parameters.

## 🛠 Technologies & Tools

- **Java**: 17
- **Spring Boot**: 3.5.0
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Key Dependencies**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter JDBC
  - Lombok
  - Jackson Databind 2.19.0
  - JSoup 1.17.2

## 🚀 Features

### Core Functionality:
- **License Plate Search** - retrieve complete vehicle information and check theft status
- **VIN Code Search** - get vehicle information by chassis number
- **Record Filtering** - search vehicles by various parameters in the database
- **Statistics Generation** - create reports based on specified parameters and time periods
- **Report Consolidation** - combine multiple statistics into comprehensive reports

## 🏗 Project Architecture

The project follows **Clean Architecture** principles with the following layers:

- **Controller Layer** - REST controllers for handling HTTP requests
- **Service Layer** - business logic implementation
- **Repository Layer** - database operations via Spring Data JPA
- **DTO Layer** - data transfer objects between layers
- **Entity Layer** - JPA entities for database mapping

### Package Structure:
```
src/main/java/com.danilko.carOpenData/
├── controller/          # REST controllers
├── dataDownloading/     # File download and processing logic
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities
├── exception/           # Custom exceptions
├── repository/          # Data access layer
├── service/             # Business logic
└── resources/           # Configuration files
```

### Key Packages:
- `controller` - REST API endpoints
- `dataDownloading` - External data fetching and processing
- `service` - Business logic and data processing
- `repository` - Database access layer
- `dto` - Request/Response objects
- `entity` - Database entities
- `exception` - Custom exception handling

## 📊 API Endpoints

### Vehicle History Controller
```
GET /vehicles/vin/{vin}          # Search by VIN code
GET /vehicles/numplate/{numPlate} # Search by license plate
```

### Reports Controller
```
GET    /api/v1/reports                    # Get all reports
POST   /api/v1/reports                    # Create new report
GET    /api/v1/reports/{reportId}         # Get specific report
PUT    /api/v1/reports/{reportId}         # Update report
DELETE /api/v1/reports/{reportId}         # Delete report
GET    /api/v1/reports/{reportId}/filters # Get report filters
```

### Statistics Endpoints
```
GET /api/v1/reports/{reportId}/statistic              # Statistics for all periods
GET /api/v1/reports/{reportId}/statistic/{year}       # Statistics for specific year
GET /api/v1/reports/{reportId}/statistic/{year}/{month} # Statistics for specific month
```

### Filters Controller
```
GET    /api/v1/filters                    # Get all filters
POST   /api/v1/filters                    # Create new filter
GET    /api/v1/filters/{filterId}         # Get specific filter
PUT    /api/v1/filters/{filterId}         # Update filter
DELETE /api/v1/filters/{filterId}         # Delete filter
GET    /api/v1/filters/{filterId}/statistic # Statistics by filter
GET    /api/v1/filters/{filterId}/statistic/{year} # Statistics by filter and year
```

### Actions Controller
```
GET /actions              # Get all actions with optional filtering
GET /actions/{filterId}   # Get actions by filter
```

#### Advanced Filtering
The API supports complex filtering through query parameters using `@ModelAttribute` binding:

**Example Request:**
```
GET /actions?vehicle.brand=SUBARU&vehicle.model=OUTBACK&vehicle.makeYearFrom=2012&vehicle.makeYearTo=2014&
```

**Supported Filter Parameters:**
- `vehicle.brand` - Filter by vehicle brand
- `vehicle.model` - Filter by vehicle model  
- `vehicle.makeYearFrom` / `vehicle.makeYearTo` - Filter by manufacture year range
- `vehicle.color` - Filter by vehicle color
- `vehicle.kind` - Filter by vehicle type
- `vehicle.bodyType` - Filter by body type
- `vehicle.fuelType` - Filter by fuel type
- `vehicle.engineCapacity` - Filter by engine capacity
- `operation.name` - Filter by operation type
- `department.name` - Filter by registration department
- `regDate` - Filter by registration date
- `numberPlate` - Filter by license plate

## 📋 API Response Examples

### License Plate Search
**Request**: `GET /vehicles/numplate/КА0320ММ`

**Response**:
```json
{
  "vehicleReadDto": {
    "vin": "JF1BRDLY5DG161640",
    "brand": "SUBARU",
    "model": "OUTBACK",
    "makeYear": 2013,
    "color": "БІЛИЙ",
    "kind": "ЛЕГКОВИЙ",
    "bodyType": "УНІВЕРСАЛ",
    "purpose": "ЗАГАЛЬНИЙ",
    "fuelType": "ДИЗЕЛЬНЕ ПАЛИВО",
    "engineCapacity": 1998,
    "ownWeight": 1552,
    "totalWeight": 2080
  },
  "stealInfoDto": null,
  "actionReadDtoList": [
    {
      "id": 6143468,
      "person": "P",
      "regAddr": "8036400000",
      "regDate": "2024-01-12",
      "operationReadDto": {
        "id": 410,
        "name": "ПЕРЕРЕЄСТРАЦІЯ ПРИ ЗАМІНІ НОМЕРНОГО ЗНАКУ"
      },
      "departmentReadDto": {
        "id": 12292,
        "name": "ТСЦ 8043"
      },
      "vehicleReadDto": {
        "vin": "JF1BRDLY5DG161640",
        "brand": "SUBARU",
        "model": "OUTBACK",
        "makeYear": 2013,
        "color": "БІЛИЙ",
        "kind": "ЛЕГКОВИЙ",
        "bodyType": "УНІВЕРСАЛ",
        "purpose": "ЗАГАЛЬНИЙ",
        "fuelType": "ДИЗЕЛЬНЕ ПАЛИВО",
        "engineCapacity": 1998,
        "ownWeight": 1552,
        "totalWeight": 2080
      },
      "numberPlate": "КА0320ММ"
    },
    {
      "id": 6103546,
      "person": "P",
      "regAddr": "8036400000",
      "regDate": "2024-01-05",
      "operationReadDto": {
        "id": 315,
        "name": "ПЕРЕРЕЄСТРАЦІЯ ТЗ НА НОВ. ВЛАСН. ПО ДОГОВОРУ УКЛАДЕНОМУ В ТСЦ"
      },
      "departmentReadDto": {
        "id": 12292,
        "name": "ТСЦ 8043"
      },
      "vehicleReadDto": {
        "vin": "JF1BRDLY5DG161640",
        "brand": "SUBARU",
        "model": "OUTBACK",
        "makeYear": 2013,
        "color": "БІЛИЙ",
        "kind": "ЛЕГКОВИЙ",
        "bodyType": "УНІВЕРСАЛ",
        "purpose": "ЗАГАЛЬНИЙ",
        "fuelType": "ДИЗЕЛЬНЕ ПАЛИВО",
        "engineCapacity": 1998,
        "ownWeight": 1552,
        "totalWeight": 2080
      },
      "numberPlate": "КА6056НН"
    }
  ]
}
```

### Actions API Response
**Request**: `GET /actions?vehicle.brand=SUBARU&vehicle.makeYearFrom=2012&vehicle.makeYearTo=2014`

**Response**:
```json
[
  {
    "id": 1913,
    "person": "P",
    "regAddr": "8036300000",
    "regDate": "2021-12-24",
    "operationReadDto": {
      "id": 315,
      "name": "ПЕРЕРЕЄСТРАЦІЯ ТЗ НА НОВ. ВЛАСН. ПО ДОГОВОРУ УКЛАДЕНОМУ В ТСЦ"
    },
    "departmentReadDto": {
      "id": 12290,
      "name": "ТСЦ 8041"
    },
    "vehicleReadDto": {
      "vin": "JF1BR9L95CG118779",
      "brand": "SUBARU",
      "model": "OUTBACK",
      "makeYear": 2012,
      "color": "СІРИЙ",
      "kind": "ЛЕГКОВИЙ",
      "bodyType": "УНІВЕРСАЛ",
      "purpose": "ЗАГАЛЬНИЙ",
      "fuelType": "БЕНЗИН",
      "engineCapacity": 2457,
      "ownWeight": 1552,
      "totalWeight": 2080
    },
    "numberPlate": "КА8035ЕХ"
  },
  {
    "id": 2524021,
    "person": "P",
    "regAddr": "8036300000",
    "regDate": "2022-12-01",
    "operationReadDto": {
      "id": 440,
      "name": "ПЕРЕРЕЄСТРАЦIЯ ПРИ ВТРАТІ СВIДОЦТВА ПРО РЕЄСТРАЦIЮ"
    },
    "departmentReadDto": {
      "id": 12290,
      "name": "ТСЦ 8041"
    },
    "vehicleReadDto": {
      "vin": "JF1BR9L95CG118779",
      "brand": "SUBARU",
      "model": "OUTBACK",
      "makeYear": 2012,
      "color": "СІРИЙ",
      "kind": "ЛЕГКОВИЙ",
      "bodyType": "УНІВЕРСАЛ",
      "purpose": "ЗАГАЛЬНИЙ",
      "fuelType": "БЕНЗИН",
      "engineCapacity": 2457,
      "ownWeight": 1552,
      "totalWeight": 2080
    },
    "numberPlate": "КА8035ЕХ"
  },
  {
    "id": 4948142,
    "person": "P",
    "regAddr": "4825455400",
    "regDate": "2023-02-11",
    "operationReadDto": {
      "id": 315,
      "name": "ПЕРЕРЕЄСТРАЦІЯ ТЗ НА НОВ. ВЛАСН. ПО ДОГОВОРУ УКЛАДЕНОМУ В ТСЦ"
    },
    "departmentReadDto": {
      "id": 12295,
      "name": "ТСЦ 8046"
    },
    "vehicleReadDto": {
      "vin": "JF1BR9L95CG118779",
      "brand": "SUBARU",
      "model": "OUTBACK",
      "makeYear": 2012,
      "color": "СІРИЙ",
      "kind": "ЛЕГКОВИЙ",
      "bodyType": "УНІВЕРСАЛ",
      "purpose": "ЗАГАЛЬНИЙ",
      "fuelType": "БЕНЗИН",
      "engineCapacity": 2457,
      "ownWeight": 1552,
      "totalWeight": 2080
    },
    "numberPlate": "КА6281КС"
  }
]
```

### Filter Statistics
**Request**: `GET /api/v1/filters/16/statistic`

**Response**:
```json
[
  {
    "id": 520,
    "filter": {
      "id": 16,
      "name": "Тестова генерація статистики",
      "operations": [],
      "departments": [],
      "numPlate": null,
      "vin": null,
      "brand": "TESLA",
      "model": null,
      "makeYearFrom": null,
      "makeYearTo": null,
      "color": "WHITE",
      "kind": null,
      "bodyType": null,
      "fuelType": null,
      "engineCapacityFrom": null,
      "engineCapacityTo": null
    },
    "fromDate": "2021-01-01",
    "toDate": "2021-12-31",
    "count": 1083
  },
  {
    "id": 533,
    "filter": {
      "id": 16,
      "name": "Тестова генерація статистики",
      "operations": [],
      "departments": [],
      "numPlate": null,
      "vin": null,
      "brand": "TESLA",
      "model": null,
      "makeYearFrom": null,
      "makeYearTo": null,
      "color": "WHITE",
      "kind": null,
      "bodyType": null,
      "fuelType": null,
      "engineCapacityFrom": null,
      "engineCapacityTo": null
    },
    "fromDate": "2022-01-01",
    "toDate": "2022-12-31",
    "count": 1474
  },
  {
    "id": 546,
    "filter": {
      "id": 16,
      "name": "Тестова генерація статистики",
      "operations": [],
      "departments": [],
      "numPlate": null,
      "vin": null,
      "brand": "TESLA",
      "model": null,
      "makeYearFrom": null,
      "makeYearTo": null,
      "color": "WHITE",
      "kind": null,
      "bodyType": null,
      "fuelType": null,
      "engineCapacityFrom": null,
      "engineCapacityTo": null
    },
    "fromDate": "2023-01-01",
    "toDate": "2023-12-31",
    "count": 4414
  },
  {
    "id": 559,
    "filter": {
      "id": 16,
      "name": "Тестова генерація статистики",
      "operations": [],
      "departments": [],
      "numPlate": null,
      "vin": null,
      "brand": "TESLA",
      "model": null,
      "makeYearFrom": null,
      "makeYearTo": null,
      "color": "WHITE",
      "kind": null,
      "bodyType": null,
      "fuelType": null,
      "engineCapacityFrom": null,
      "engineCapacityTo": null
    },
    "fromDate": "2024-01-01",
    "toDate": "2024-12-31",
    "count": 7890
  }
]
```

## 🚀 Installation & Setup

### System Requirements:
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Setup Steps:

1. **Clone Repository**
   ```bash
   git clone [REPOSITORY_URL]
   cd vehicle-registry-api
   ```

2. **Database Configuration**
   - Create PostgreSQL database
   - Configure connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vehicle_registry
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build & Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verify Installation**
   - API available at: `http://localhost:8080`
   - Test endpoint: `GET http://localhost:8080/vehicles/numplate/AA1234BB`

## 🧪 Testing

The project includes comprehensive testing with:
- **JUnit 5** - unit testing framework
- **Spring Boot Test** - integration testing
- **Testcontainers** - database testing

### Run Tests:
```bash
mvn test
```

### Test Coverage:
- Unit tests for service layer business logic
- Integration tests for REST API endpoints
- Repository layer testing with test database

## 🔧 Advanced Features

### Query Parameter Filtering
The API implements sophisticated filtering using Spring's `@ModelAttribute` binding, allowing complex nested object filtering through URL parameters:

```java
@GetMapping()
public ResponseEntity<List<ActionReadDto>> filterActions(
    @ModelAttribute ActionFilterDto actionFilterDto
) {
    // Automatically binds query parameters to nested DTO structure
}
```

**Key Technical Features:**
- **Nested Parameter Binding** - `vehicle.brand=TESLA&vehicle.makeYearFrom=2020`
- **Range Filtering** - Support for `From/To` parameters for numeric ranges
- **Flexible Query Structure** - Easy to extend with new filter parameters
- **Type Safety** - Automatic parameter validation and type conversion

### Data Transfer Objects (DTOs)
Well-structured DTO layer for clean API contracts:

- **ActionReadDto** - Complete registration action information
- **VehicleReadDto** - Comprehensive vehicle specifications  
- **OperationReadDto** - Registration operation details
- **DepartmentReadDto** - Government department information
- **ActionFilterDto** - Flexible filtering parameters
