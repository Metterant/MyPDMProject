
CREATE DATABASE digital_bus_pass;

-- Use the database
USE digital_bus_pass;
CREATE TABLE Users (
	UserID INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(50),
    Age INT,
    Phone VARCHAR(15),
    Address VARCHAR(100),
    Role VARCHAR(20)		-- Passenger/Admin    
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
	DriveID INT PRIMARY KEY,
    Name VARCHAR(50),
    Age INT,
    License VARCHAR(20),
    Phone INT
);

CREATE TABLE Bus_info(
	BusID INT PRIMARY KEY AUTO_INCREMENT,
	PlateNumber VARCHAR(20),
    Capacity INT,
    DriveID INT,
    RouteID INT,
    FOREIGN KEY (RouteID) REFERENCES Route(RouteID),
    FOREIGN KEY (DriveID) REFERENCES Driver(DriveID)
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
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (TripID) REFERENCES Trip(TripID)
);

CREATE TABLE Payment_Method(
	PaymentID INT PRIMARY KEY AUTO_INCREMENT,
	PaymentDate DATETIME,
    Method VARCHAR(20)		-- Card/Cash/Online
);

CREATE TABLE Payment(
    Amount DECIMAL(10,2),
    PaymentDATE DATE,
    UserID INT,
    PaymentID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PaymentID) REFERENCES Payment_Method(PaymentID)
    
);


    