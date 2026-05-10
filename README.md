# \# OTP Authentication Service

# 

# Secure OTP-based authentication service built using Spring Boot, Redis, JWT, and PostgreSQL.

# 

# \## Features

# 

# \- User registration and login

# \- OTP generation and verification

# \- JWT-based authentication

# \- Redis caching for OTP expiration

# \- PostgreSQL database integration

# \- RESTful APIs

# \- Secure authentication flow

# 

# \## Tech Stack

# 

# \- Java

# \- Spring Boot

# \- Spring Security

# \- JWT

# \- Redis

# \- PostgreSQL

# \- Maven

# 

# \## Project Architecture

# 

# Client → REST API → Spring Boot Service → Redis/PostgreSQL

# 

# \## API Endpoints

# 

# \### Authentication

# \- POST /auth/register

# \- POST /auth/send-otp

# \- POST /auth/verify-otp

# \- POST /auth/login

# 

# \## How It Works

# 

# 1\. User enters phone/email

# 2\. OTP is generated and stored in Redis

# 3\. User verifies OTP

# 4\. JWT token is issued after successful verification

# 

# \## Future Improvements

# 

# \- Rate limiting

# \- Email/SMS integration

# \- Docker deployment

# \- Refresh tokens

# \- API documentation using Swagger

# 

# \## Learning Outcomes

# 

# \- Implemented secure authentication flow

# \- Learned Redis caching mechanisms

# \- Built scalable REST APIs

# \- Integrated JWT authentication

# 

# \## Author

# 

# Rahul Nayak

