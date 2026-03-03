## Notes 👀️

#### EatClub Backend Developer Technical Challenge

## Technologies Used
*   **Java 17 (OpenJDK)**
*   **Spring Boot 3.2.3** (Web, Test)
*   **Maven**
*   **JUnit 5 & Mockito**

### Prerequisites
*   Java JDK 17+
*   Maven

### How to Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

app will start on `http://localhost:8080`.

## API Endpoints

### 1. Get Active Deals (Task 1)
Returns a list of all restaurant deals active at the specified time.

**GET** `/deals?timeOfDay={time}`

**Parameters:**
*   `timeOfDay`: String (Formats supported: `HH:mm`, `H:mm`, `h:mma` e.g., "10:30", "13:00", "3:00pm")

**Example Request:**
`GET http://localhost:8080/deals?timeOfDay=3:00pm`

**Response:**
```json
{
  "deals": [
    {
      "restaurantName": "Example Burger",
      "discount": "20%",
      ...
    }
  ]
}
```

### 2. Get Peak Time (Task 2)
Calculates the time window with the highest number of concurrent active deals.

**GET** `/peak-time`

**Response:**
```json
{
  "peakTimeStart": "18:30",
  "peakTimeEnd": "18:45"
}
```

## Testing
To run the unit tests:
```bash
mvn test
```