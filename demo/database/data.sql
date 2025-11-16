INSERT INTO user (UserName, Age, Phone, Address, Role)
VALUES
('Nguyen Van A', 24, '0912345678', '81 LA', 'Passenger'),
('Le Van B', 30, '0987654321', '35 WA', 'Passenger'),
('Nguyen Van C', 52, '0965432987', '56 TA', 'Admin'),
('Tran Yen D', 35, '0924681357', '75 WE', 'Passenger'),
('Nam Cong E', 65, '0913579246', '20 VN', 'Admin'),
('Pham Thi F', 19, '0901112233', '12 QB', 'Passenger'),
('Hoang Minh G', 41, '0978765432', '99 XT', 'Passenger'),
('Vo Quoc H', 28, '0934567890', '40 KN', 'Passenger'),
('Dinh Thi I', 58, '0955667788', '10 CD', 'Admin'),
('Doan Van J', 22, '0941234567', '21 GH', 'Passenger');

INSERT INTO driver (DriveID, Name, Age, License, Phone)
VALUES
(12345678, 'Le Driver', 45, 'L12345', 902345678),
(12345679, 'Nguyen Driver', 30, 'L54321', 907654321),
(12345680, 'Tran Driver', 50, 'L13579', 998765432),
(12345681, 'Vu Driver', 38, 'L98765', 911223344),
(12345682, 'Bui Driver', 55, 'L24680', 909887766);

INSERT INTO route (RouteName, StartLocation, EndLocation, FARE, Distance, Times)
VALUES
('Route A', 'Station A', 'Station B', 1.50, 12.3, '00:45:00'),
('Route B', 'Station B', 'Station C', 1.50, 12.3, '00:45:00'),
('Route C', 'Station C', 'Station D', 1.50, 10.3, '00:35:00'),
('Route D', 'Station D', 'Station E', 2.00, 15.0, '01:00:00'),
('Route E', 'Station E', 'Station F', 1.00, 8.5, '00:25:00');

INSERT INTO bus_info (PlateNumber, Capacity, DriveID, RouteID)
VALUES
('30A-12345', 40, 12345678, 1),
('30A-54321', 40, 12345679, 2),
('30A-13579', 40, 12345680, 3),
('29B-99887', 50, 12345681, 4),
('51C-10203', 35, 12345682, 5);

INSERT INTO trip (Date, DepartureTime, ArrivalTime, BusID, RouteID)
VALUES
('2025-06-01', '08:00:00', '08:45:00', 1, 1),
('2025-06-01', '09:00:00', '09:45:00', 2, 2),
('2025-06-01', '10:00:00', '10:35:00', 3, 3),
('2025-06-01', '09:00:00', '09:45:00', 1, 1),
('2025-06-01', '10:00:00', '10:45:00', 2, 2),
('2025-06-01', '11:00:00', '11:35:00', 3, 3),
('2025-06-02', '07:30:00', '08:30:00', 4, 4),
('2025-06-02', '12:00:00', '12:25:00', 5, 5),
('2025-06-02', '14:00:00', '14:45:00', 1, 1),
('2025-06-03', '06:00:00', '06:45:00', 2, 2),
('2025-06-03', '18:00:00', '18:35:00', 3, 3),
('2025-06-03', '17:00:00', '18:00:00', 4, 4);

INSERT INTO ticket (Date, UserID, TripID)
VALUES
('2025-06-01 00:00:00', 1, 1),
('2025-06-01 00:00:00', 2, 5),
('2025-06-01 00:00:00', 4, 3),
('2025-06-02 00:00:00', 6, 7),
('2025-06-02 00:00:00', 7, 9),
('2025-06-03 00:00:00', 8, 12),
('2025-06-03 00:00:00', 10, 10);

INSERT INTO payment_method (PaymentDate, Method)
VALUES
('2025-06-01 00:00:00', 'Cash'),
('2025-06-01 00:00:00', 'Card'),
('2025-06-01 00:00:00', 'Online'),
('2025-06-02 00:00:00', 'Cash'),
('2025-06-02 00:00:00', 'Online'),
('2025-06-03 00:00:00', 'Card');

INSERT INTO payment (Amount, PaymentDATE, UserID, PaymentID)
VALUES
(10000.00, '2025-06-01', 1, 1),
(10000.00, '2025-06-01', 2, 2),
(10000.00, '2025-06-01', 4, 3),
(15000.00, '2025-06-02', 6, 4),
(10000.00, '2025-06-02', 7, 5),
(20000.00, '2025-06-03', 8, 6),
(10000.00, '2025-06-03', 10, 2);