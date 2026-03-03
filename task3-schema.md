# Database Schema Design (Bonus Task)

**Technology:** PostgreSQL
**Reasoning:** PostgreSQL is chosen for its robustness, native JSON support (JSONB), and strong relational integrity, which is essential for managing the relationship between Restaurants and their multiple Deals. Its PostGIS extension would also support future geolocation features (e.g., "find deals near me").

## Schema Diagram

```mermaid
erDiagram
    RESTAURANT ||--o{ DEAL : offers
    
    RESTAURANT {
        uuid id PK "Primary Key"
        string name "Not Null"
        string address1
        string suburb
        string state
        string postcode
        time open_time
        time close_time
        point location_lat_long "For geolocation queries"
        timestamp created_at
        timestamp updated_at
    }

    DEAL {
        uuid id PK "Primary Key"
        uuid restaurant_id FK "Foreign Key -> RESTAURANT.id"
        integer discount_percentage "Stored as integer (e.g., 20)"
        time start_time "Not Null"
        time end_time "Not Null"
        boolean is_dine_in "Default true"
        boolean is_lightning "Default false"
        integer quantity_available "Tracks remaining deals"
        date active_date "Specific date for the deal"
        jsonb metadata "Flexible storage for extra attributes"
    }