# Java 8 Workbench

Small field work-order desk for SAM Java 8 → 21 smoke testing.

- Java 8 (`maven.compiler.source/target=1.8`)
- Spring Boot 2.7.18 (javax servlet, not Jakarta)
- Maven
- In-memory store (no database)
- Browser UI plus REST

## Run

```bash
cd /Users/madhuchenna/java8-workbench
mvn clean install
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080)

Or run the jar:

```bash
java -jar target/java8-workbench-1.0.0.jar
```

## What you can do in the UI

1. See three seeded work orders.
2. Create a new order.
3. Filter by owner or status.
4. Click a row, change status, add a note, or delete.

## REST

```bash
curl http://localhost:8080/api/health

curl http://localhost:8080/api/orders

curl "http://localhost:8080/api/orders?owner=Priya&status=IN_PROGRESS"

curl -X POST http://localhost:8080/api/orders \
  -H 'Content-Type: application/json' \
  -d '{"title":"Check fire extinguishers","owner":"Lee","location":"Floor 2","description":"Monthly walkthrough"}'

curl -X POST http://localhost:8080/api/orders/1001/status \
  -H 'Content-Type: application/json' \
  -d '{"status":"DONE"}'

curl -X POST http://localhost:8080/api/orders/1001/notes \
  -H 'Content-Type: application/json' \
  -d '{"author":"Lee","text":"Signed off."}'

curl -X DELETE http://localhost:8080/api/orders/1003
```

IDs start at 1001 after seed data.

## Why this shape

Legacy on purpose: `javax.servlet` filter, `java.util.Date`, `SimpleDateFormat`, raw `Vector`, Commons Lang3. Enough surface for a Java 21 bump without being a full GSA EAR.
