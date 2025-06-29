# Decentralized Voting System

A blockchain-based decentralized voting system built with Spring Boot, MySQL, and WebSocket for real-time updates.

## Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control
- **Election Management**: Create, manage, and monitor elections
- **Candidate Management**: Add and manage candidates for elections
- **Secure Voting**: Encrypted ballot submission with blockchain transaction simulation
- **Real-time Updates**: WebSocket integration for live vote counting
- **Vote Verification**: Blockchain-based vote verification system
- **Result Calculation**: Automated election result calculation
- **Audit Trail**: Complete audit logging for all operations

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **Real-time**: WebSocket with STOMP
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- MySQL 8.0
- Redis (optional, for caching)
- Maven 3.6+

## Installation & Setup

### 1. Database Setup

Create a MySQL database:
```sql
CREATE DATABASE voting_system;
```

### 2. Configuration

Update `src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/voting_system
    username: your_username
    password: your_password
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Elections
- `GET /api/elections` - Get all elections
- `GET /api/elections/active` - Get active elections
- `GET /api/elections/{id}` - Get election by ID
- `POST /api/elections` - Create new election
- `PUT /api/elections/{id}/status` - Update election status

### Candidates
- `GET /api/candidates/election/{electionId}` - Get candidates for election
- `GET /api/candidates/{id}` - Get candidate by ID
- `POST /api/candidates` - Create new candidate
- `PUT /api/candidates/{id}` - Update candidate
- `DELETE /api/candidates/{id}` - Delete candidate

### Voting
- `POST /api/votes/cast` - Cast a vote
- `GET /api/votes/{id}` - Get vote by ID
- `GET /api/votes/election/{electionId}` - Get total votes for election
- `GET /api/votes/election/{electionId}/results` - Get election results
- `GET /api/votes/verify/{transactionId}` - Verify vote on blockchain

### WebSocket
- `ws://localhost:8080/ws` - WebSocket endpoint for real-time updates
- `/topic/vote-counts` - Subscribe to vote count updates

## Sample API Usage

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "voter1",
    "email": "voter1@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "voter1",
    "password": "password123"
  }'
```

### 3. Create an Election
```bash
curl -X POST "http://localhost:8080/api/elections?createdByUserId=1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Student Council Election 2024",
    "description": "Annual student council election",
    "startDate": "2024-01-15T09:00:00",
    "endDate": "2024-01-15T17:00:00",
    "maxVotesPerVoter": 1
  }'
```

### 4. Add Candidates
```bash
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "electionId": 1,
    "name": "Alice Johnson",
    "party": "Progressive Party",
    "description": "Experienced student leader"
  }'
```

### 5. Cast a Vote
```bash
curl -X POST "http://localhost:8080/api/votes/cast?voterId=1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "electionId": 1,
    "candidateId": 1
  }'
```

### 6. Get Election Results
```bash
curl -X GET http://localhost:8080/api/votes/election/1/results \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Database Schema

The application uses the following main tables:
- `users` - User accounts and authentication
- `elections` - Election information
- `candidates` - Candidate information
- `votes` - Encrypted vote data
- `blockchain_transactions` - Blockchain transaction records
- `audit_logs` - System audit trail

## Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-based Access Control**: Different permissions for voters, admins, and election officials
- **Encrypted Ballots**: AES encryption for vote data
- **Blockchain Verification**: Transaction hash verification
- **Audit Logging**: Complete audit trail for all operations

## Real-time Features

- **Live Vote Counting**: Real-time vote count updates via WebSocket
- **Election Status Updates**: Live election status changes
- **Vote Verification**: Real-time vote verification status

## Monitoring

The application includes Spring Boot Actuator for monitoring:
- `GET /actuator/health` - Application health check
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

## Development

### Project Structure
```
src/main/java/com/voting/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data transfer objects
├── entity/         # JPA entities
├── exception/      # Exception handlers
├── repository/     # Data access layer
├── service/        # Business logic
└── util/           # Utility classes
```

### Running Tests
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please open an issue in the repository. 