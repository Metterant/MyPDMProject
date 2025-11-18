
CREATE DATABASE digital_bus_pass;

-- Use the database
USE digital_bus_pass;

CREATE TABLE UserRoles(
    UserRoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleDescription VARCHAR(50)
);

CREATE TABLE User (
	UserID INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(50),
    UserPassword VARCHAR(128) NOT NULL,
    Age INT,
    Phone VARCHAR(15),
    UserAddress VARCHAR(100),
    UserRoleID INT,
    FOREIGN KEY (UserRoleID) REFERENCES UserRoles(UserRoleID)
);


CREATE TABLE Route(
	RouteID INT PRIMARY KEY AUTO_INCREMENT,
    RouteName VARCHAR(50),
    StartLocation VARCHAR(50),
    EndLocation VARCHAR(50),
    FARE DECIMAL(10,2),
    Distance float,
    Times TIME
);

CREATE TABLE Driver(
	DriverID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Age INT,
    License VARCHAR(20),
    Phone INT
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
    Date DATE,
    DepartureTime TIME,
    ArrivalTime TIME,
    BusID INT,
    RouteID INT,
    FOREIGN KEY (BusID) REFERENCES Bus_info(BusID),
    FOREIGN KEY (RouteID) REFERENCES Route(RouteID)
);

CREATE TABLE Ticket (
	TicketID INT PRIMARY KEY AUTO_INCREMENT,
    Date DATETIME,
    UserID INT,
    TripID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (TripID) REFERENCES Trip(TripID)
);

CREATE TABLE Payment_Methods(
	Payment_Method_ID INT PRIMARY KEY AUTO_INCREMENT,
    Method VARCHAR(30)		-- Card/Cash/Online
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
