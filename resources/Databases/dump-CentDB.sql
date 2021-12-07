-- Host: localhost    Database: CentDB
-----------------------------------------------------------
-- Table structure for table orders
--
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
orderId int primary_key,
orderNumber int,
personId text foreign_key);
--
-- Dumping data for table orders
--
LOCK TABLES orders WRITE;
INSERT INTO orders VALUES (orderId,orderNumber,personId)
VALUES (orderId,orderNumber,personId)(1,77895,3)(2,44678,3);
UNLOCK TABLES;