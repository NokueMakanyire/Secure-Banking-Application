# Secure-Banking-Application
## Description
This is a console-based Java application that simulates a secure banking system. It demonstrates core Object-Oriented Programming (OOP) principles and secure coding practices, including user authentication, account management, transaction handling, and file-based data persistence.

## Student Details
**Name:** Tanya
**Registration Number:** [H240166G]

## Features
- 🔐 **User Authentication**
  - Secure login system using username and password.
  - Credentials stored and validated via hashed text files.

- 🏦 **Account Management**
  - Create new bank accounts with unique account numbers.
  - View current account balance.

- 💸 **Transactions**
  - Deposit funds into an account.
  - Withdraw funds with balance validation.
  - Transaction history maintained per account.

- 💾 **Data Persistence**
  - All user credentials, account details, and transaction history are stored in text files.
  - Data is loaded automatically on application startup.
  - No external databases required.

## File Structure
├── src/
│   ├── Main.java
│   ├── BankSystem.java
│   ├── Account.java
│   └── User.java
├── data/
│   ├── users.txt
│   ├── accounts.txt
│   └── transactions.txt
├── README.md