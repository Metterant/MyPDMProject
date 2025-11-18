# Project Manual

## Store Credentials in .env file

Create `db_credentials.env` file in the root of this project and make sure the file contents follow  

``` dotenv
DB_URL=your_jdbc_db_url_including_path_to_digital_bus_pass
DB_PASSWORD=your_db_password_here
```
- `DB_URL` example: `jdbc:mysql://localhost:3306/digital_bus_pass` (the path to the `digital_bus_pass` database)
