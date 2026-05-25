# API Documentation

This documentation provides details about the available API endpoints for the SumUp Wallet application.

Base URL: `http://localhost:8080/api/v1`

## Auth Controller
- `POST /auth/login` - Authenticates a user.

## User Controller
- `POST /users` - Creates a new user.
- `PUT /users` - Updates an existing user's information.
- `GET /users/{userId}` - Retrieves a user by their ID.
- `GET /users/email` - Retrieves a user by their email.
- `DELETE /users/{userId}` - Deletes a user by their ID.

## Wallet Controller
- `POST /wallets` - Creates a new wallet for a user.
- `PUT /wallets` - Updates an existing wallet's name. (Parameters: `walletId`, `walletName`)
- `PUT /wallets/deposit` - Deposits money into a wallet. (Parameters: `walletId`, `amount`)
- `PUT /wallets/withdraw` - Withdraws money from a wallet. (Parameters: `walletId`, `amount`)
- `GET /wallets/user/{userId}` - Retrieves all wallets belonging to a user.
- `GET /wallets/{walletId}` - Retrieves a wallet by its ID.
- `GET /wallets/balance/{walletId}` - Retrieves the balance of a wallet.
- `DELETE /wallets/{walletId}` - Deletes a wallet.

## Transaction Controller
- `GET /transactions/wallet/{walletId}` - Retrieves all transactions for a specific wallet.
- `GET /transactions/deposit` - Retrieves all deposit transactions for a specific wallet. (Parameters: `walletId`)
- `GET /transactions/withdrawal` - Retrieves all withdrawal transactions for a specific wallet. (Parameters: `walletId`)
