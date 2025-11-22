
CREATE DATABASE digital_bus_pass;

-- Use the database
USE digital_bus_pass;

CREATE TABLE UserRoles(
    UserRoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleDescription VARCHAR(50)
);

CREATE TABLE User (
	UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    FullName VARCHAR(50) DEFAULT 'Unnamed',
    UserPassword VARCHAR(128) NULL DEFAULT NULL,
    Age INT,
    Phone VARCHAR(15),
    UserAddress VARCHAR(100),
    UserRoleID INT,
    FOREIGN KEY (UserRoleID) REFERENCES UserRoles(UserRoleID),
    CHECK (Age >= 0),
	CONSTRAINT chkNoSpaces CHECK (Username NOT LIKE '% %')
);


CREATE TABLE Route(
	RouteID INT PRIMARY KEY AUTO_INCREMENT,
    RouteName VARCHAR(50),
    StartLocation VARCHAR(50),
    EndLocation VARCHAR(50),
    Fare DECIMAL(10,2),
    Distance float,
    Duration TIME,
    CONSTRAINT CHK_RouteName CHECK (RouteName REGEXP '^[0-9]+(-[0-9]+)?$')
);

CREATE TABLE Driver(
	DriverID INT PRIMARY KEY AUTO_INCREMENT,
    DriverName VARCHAR(50),
    Age INT,
    License VARCHAR(20),
    Phone VARCHAR(15),
    CHECK (Age >= 0)
);

CREATE TABLE Bus_info(
	BusID INT PRIMARY KEY AUTO_INCREMENT,
	PlateNumber VARCHAR(20),
    Capacity INT,
    DriverID INT,
    RouteID INT,
    FOREIGN KEY (RouteID) REFERENCES Route(RouteID),
    FOREIGN KEY (DriverID) REFERENCES Driver(DriverID)
);

CREATE TABLE Trip (
	TripID INT PRIMARY KEY AUTO_INCREMENT,
    TripDate DATE,
    DepartureTime TIME,
    ArrivalTime TIME,
    BusID INT,
    FOREIGN KEY (BusID) REFERENCES Bus_info(BusID)
);

CREATE TABLE Payment_Methods(
	Payment_Method_ID INT PRIMARY KEY AUTO_INCREMENT,
    Method VARCHAR(30)
);

CREATE TABLE Payment(
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Amount DECIMAL(10,2),
    PaymentDate DATETIME,
    Payment_Method_ID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (Payment_Method_ID) REFERENCES Payment_Methods(Payment_Method_ID)
);

CREATE TABLE Ticket (
	TicketID INT PRIMARY KEY AUTO_INCREMENT,
    TicketDateTime DATETIME,
    UserID INT,
    TripID INT,
    PaymentID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (TripID) REFERENCES Trip(TripID),
    FOREIGN KEY (PaymentID) REFERENCES Payment(PaymentID)
);

CREATE VIEW trip_detailed_view AS
SELECT TripID, RouteName, TripDate, DepartureTime, ArrivalTime, StartLocation, EndLocation, Capacity, Duration, PlateNumber, Fare
FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID
	JOIN Route r ON b.RouteID = r.RouteID
WHERE (TripDate > CURDATE()
	OR (TripDate = CURDATE()
		AND DepartureTime > NOW()))
ORDER BY TripDate, DepartureTime;