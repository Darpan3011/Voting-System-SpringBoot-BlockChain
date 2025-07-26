# Decentralized Voting System

A blockchain-based decentralized voting system built with Spring Boot, MySQL, and WebSocket for real-time updates. This system provides secure, transparent, and verifiable voting with blockchain integration for vote integrity.

## Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control (VOTER, ADMIN, ELECTION_OFFICIAL)
- **Election Management**: Create, manage, and monitor elections with status tracking (DRAFT, ACTIVE, CLOSED, RESULTS_PUBLISHED)
- **Candidate Management**: Add and manage candidates for elections with party affiliations
- **Secure Voting**: AES-encrypted ballot submission with blockchain transaction simulation
- **Real-time Updates**: WebSocket integration for live vote counting and election status updates
- **Vote Verification**: Blockchain-based vote verification system using Ethereum transactions
- **Result Calculation**: Automated election result calculation with candidate vote counts
- **Audit Trail**: Complete audit logging for all operations with JSON details
- **API Documentation**: OpenAPI/Swagger UI integration for comprehensive API documentation

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT (io.jsonwebtoken 0.11.5)
- **Real-time**: WebSocket with STOMP
- **Blockchain**: Web3j 4.10.3 for Ethereum integration
- **Caching**: Redis for session management
- **API Documentation**: SpringDoc OpenAPI 2.2.0
- **Build Tool**: Maven
- **Java Version**: 17
- **Validation**: Spring Boot Validation

## Prerequisites

- Java 17 or higher
- MySQL 8.0
- Redis (for caching and session management)
- Maven 3.6+
- Ethereum node (local or testnet) for blockchain integration

## Installation & Setup

### 1. Database Setup

Create a MySQL database:
```sql
CREATE DATABASE voting_system;
```

### 2. Configuration

Update `src/main/resources/application.yml` with your database credentials and blockchain settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/voting_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379

blockchain:
  ethereum:
    node-url: http://127.0.0.1:8545  # Your Ethereum node URL
    private-key: your_private_key    # Your Ethereum private key
    chain-id: 1337                   # Chain ID (1 for mainnet, 5 for Goerli, 1337 for local)
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
- `POST /api/auth/refresh` - Refresh JWT token (Not Implemented)

### Elections
- `GET /api/elections` - Get all elections
- `GET /api/elections/active` - Get active elections
- `GET /api/elections/{id}` - Get election by ID
- `POST /api/elections?createdByUserId={id}` - Create new election
- `PUT /api/elections/{id}/status?status={status}` - Update election status

### Candidates
- `GET /api/candidates/election/{electionId}` - Get candidates for election
- `GET /api/candidates/{id}` - Get candidate by ID
- `POST /api/candidates` - Create new candidate
- `PUT /api/candidates/{id}` - Update candidate
- `DELETE /api/candidates/{id}` - Delete candidate

### Voting
- `POST /api/votes/cast?voterId={id}` - Cast a vote
- `GET /api/votes/{id}` - Get vote by ID
- `GET /api/votes/election/{electionId}` - Get total votes for election
- `GET /api/votes/election/{electionId}/results` - Get election results
- `GET /api/votes/election/{electionId}/votes` - Get all votes for election
- `GET /api/votes/verify/{transactionId}` - Verify vote on blockchain

### WebSocket
- `ws://localhost:8080/ws` - WebSocket endpoint for real-time updates
- `/topic/vote-counts` - Subscribe to vote count updates
- `/app/vote-updates` - Send vote updates

### API Documentation
- `http://localhost:8080/swagger-ui.html` - Swagger UI
- `http://localhost:8080/api-docs` - OpenAPI documentation

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
    "username": "admin",
    "password": "admin123"
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

### Core Tables
- `users` - User accounts with roles (VOTER, ADMIN, ELECTION_OFFICIAL)
- `elections` - Election information with status tracking
- `candidates` - Candidate information with party affiliations
- `votes` - Encrypted vote data with blockchain transaction IDs
- `blockchain_transactions` - Ethereum transaction records for vote verification
- `audit_logs` - System audit trail with JSON details

### Key Features
- **Encrypted Ballots**: AES encryption for vote data
- **Unique Constraints**: One vote per voter per election
- **Audit Logging**: Complete audit trail with IP addresses and user agents
- **Blockchain Integration**: Transaction hashes for vote verification

## Security Features

- **JWT Authentication**: Secure token-based authentication with configurable expiration
- **Password Encryption**: BCrypt password hashing
- **Role-based Access Control**: Different permissions for voters, admins, and election officials
- **Encrypted Ballots**: AES encryption for vote data with configurable keys
- **Blockchain Verification**: Ethereum transaction-based vote verification
- **Audit Logging**: Complete audit trail for all operations
- **Input Validation**: Comprehensive request validation using Bean Validation

## Real-time Features

- **Live Vote Counting**: Real-time vote count updates via WebSocket
- **Election Status Updates**: Live election status changes
- **Vote Verification**: Real-time vote verification status
- **STOMP Protocol**: Standard WebSocket messaging protocol

## Blockchain Integration

- **Ethereum Transactions**: Each vote is recorded as an Ethereum transaction
- **Transaction Verification**: Vote verification through blockchain transaction receipts
- **Configurable Network**: Support for mainnet, testnets, and local networks
- **Gas Management**: Configurable gas limits and prices

## Monitoring & Health Checks

The application includes Spring Boot Actuator for monitoring:
- `GET /actuator/health` - Application health check
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics
- `GET /actuator/prometheus` - Prometheus metrics endpoint

## Development

### Project Structure
```
src/main/java/com/voting/
├── config/          # Configuration classes (Security, WebSocket, OpenAPI)
├── controller/      # REST controllers and WebSocket handlers
├── dto/            # Data transfer objects for API requests/responses
├── entity/         # JPA entities with audit support
├── exception/      # Global exception handlers
├── repository/     # Data access layer with JPA repositories
├── service/        # Business logic services
└── util/           # Utility classes (JWT, Encryption)
```

### Default Users
The system comes with pre-configured users:
- **Admin**: username: `admin`, password: `admin123`
- **Election Official**: username: `election_official`, password: `admin123`
- **Sample Voters**: `voter1`, `voter2`, `voter3` (password: `admin123`)

### Running Tests
```bash
mvn test
```

### Development Tools
- **Spring Boot DevTools**: Hot reload for development
- **Hibernate SQL Logging**: SQL query logging for debugging
- **OpenAPI Documentation**: Auto-generated API documentation

## Configuration

### Environment Variables
- `JWT_SECRET`: JWT signing secret
- `JWT_EXPIRATION`: JWT token expiration time
- `AES_KEY`: AES encryption key for ballots
- `ETHEREUM_NODE_URL`: Ethereum node URL
- `ETHEREUM_PRIVATE_KEY`: Ethereum private key for transactions

### Application Properties
- Database connection settings
- Redis configuration
- Blockchain network settings
- Logging levels
- Actuator endpoints

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please open an issue in the repository.

## Postman Collection

A complete Postman collection (`Voting_System_API.postman_collection.json`) is included with all API endpoints and example requests for easy testing and development. 