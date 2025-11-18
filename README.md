# Project Manual

## Store Credentials in a `.env` file

Create a file named `db_credentials.env` in the project root (do not commit this file).

Example contents:

```
DB_URL=jdbc:mysql://localhost:3306/digital_bus_pass
DB_USER=root
DB_PASSWORD=your_password_here
DB_MAX_POOL_SIZE=10
```

- `DB_URL` should include the database name.
- `DB_USER` and `DB_PASSWORD` are your database username and password.
- `DB_MAX_POOL_SIZE` is optional (default is 10).

Add `db_credentials.env` to your `.gitignore` to avoid committing secrets.

---



