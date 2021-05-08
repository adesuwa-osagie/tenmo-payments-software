BEGIN TRANSACTION;
UPDATE accounts
   Set balance = 5000.00
   WHERE user_id = 1001;
SELECT * FROM accounts;
ROLLBACK;


--Tracking the transactions
BEGIN TRANSACTION;
INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)
   VALUES(?, ?, ?, ?, ?);
   --params: subquery to transfer_types - SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = [FIRST ? PLACEHOLDER]
   --params: subquery to transfer_statuses - SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = [SECOND ? PLACEHOLDER]
   --params: subquery to accounts - SELECT account_id FROM accounts WHERE user_id = [THIRD ? PLACEHOLDER] will be currentUser id
   --params: subquery to accounts - SELECT account_ud FROM accounts WHERE user_id = [FOURTH ? PLACEHOLDER] will be receiverUser id
   --params: userInput amout to transfer
   
ROLLBACK;

BEGIN TRANSACTION

BEGIN TRANSACTION;
INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)
   VALUES(
   (SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = 'Send'), 
   (SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = 'Approved'), 
   (SELECT account_id FROM accounts WHERE user_id = 1001), 
   (SELECT account_id FROM accounts WHERE user_id = 1003), 
   5000.00);

SELECT * FROM transfers;

ROLLBACK;


SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = 'Send'; 
SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = 'Approved';
SELECT account_id FROM accounts WHERE user_id = 1001;

BEGIN TRANSACTION;
INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)
   VALUES(
   (SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = 'Send'[String]), 
   (SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = 'Approved'[String]), 
   (SELECT account_id FROM accounts WHERE user_id = 1001[Long currentuser]), 
   (SELECT account_id FROM accounts WHERE user_id = 1003[Long receiver]), 
   5000.00[Big Decimal amount to transfer]);

SELECT * FROM transfers;

ROLLBACK;

--HOW to get all transfers associated with current user
SELECT t.transfer_id, transfer_type_id, transfer_status_id, t.account_from, t.account_to, t.amount
   FROM transfers t
   WHERE t.account_to = 2001 OR t.account_from = 2001;

JOIN accounts a ON t.account_from = a.account_id;


SELECT username FROM users u
JOIN accounts a ON a.user_id = u.user_id
WHERE account_id = 2003;

SELECT * FROM transfer_statuses;

SELECT transfer_status_desc 
FROM transfer_statuses
WHERE transfer_status_id = 2;

SELECT * FROM transfer_types;

SELECT transfer_type_desc 
FROM transfer_types
WHERE transfer_type_id = 2;

--SQL statement for TransferDisplay model
SELECT t.transfer_id AS "transferId", type.transfer_type_desc AS "transferType", status.transfer_status_desc AS "transferStatus", 
(SELECT u.username WHERE t.account_from = a.account_id) AS "userFrom",
(SELECT uc.username WHERE t.account_to = ac.account_id) AS "userTo",
t.amount
FROM transfers t
JOIN transfer_types type ON t.transfer_type_id = type.transfer_type_id
JOIN transfer_statuses status ON t.transfer_status_id = status.transfer_status_id
JOIN accounts a ON t.account_from = a.account_id
JOIN users u ON u.user_id = a.user_id
JOIN accounts ac ON t.account_to = ac.account_id
JOIN users uc ON uc.user_id = ac.user_Id
WHERE t.account_to = 2001 OR t.account_from = 2001
ORDER BY "transferId";
----------------------------------------------------

--New SQL Statement for Transfer Model using user's account Id

SELECT t.transfer_id, t.transfer_type_id, type.transfer_type_desc, t.transfer_status_id, status.transfer_status_desc, 
t.account_from,
(SELECT u.username WHERE t.account_from = a.account_id),
t.account_to,
(SELECT uc.username WHERE t.account_to = ac.account_id) AS "username2",
t.amount
FROM transfers t
JOIN transfer_types type ON t.transfer_type_id = type.transfer_type_id
JOIN transfer_statuses status ON t.transfer_status_id = status.transfer_status_id
JOIN accounts a ON t.account_from = a.account_id
JOIN users u ON u.user_id = a.user_id
JOIN accounts ac ON t.account_to = ac.account_id
JOIN users uc ON uc.user_id = ac.user_Id
WHERE t.account_to = 2003 OR t.account_from = 2003
ORDER BY t.transfer_id;

--FUTURE SQL statement for the list method used for Step 5 and Step 6
SELECT t.transfer_id, type.transfer_type_desc, status.transfer_status_desc, 
(SELECT u.username WHERE t.account_from = a.account_id) AS "userFrom",
(SELECT uc.username WHERE t.account_to = ac.account_id) AS "userTo",
t.amount
FROM transfers t
JOIN transfer_types type ON t.transfer_type_id = type.transfer_type_id
JOIN transfer_statuses status ON t.transfer_status_id = status.transfer_status_id
JOIN accounts a ON t.account_from = a.account_id
JOIN users u ON u.user_id = a.user_id
JOIN accounts ac ON t.account_to = ac.account_id
JOIN users uc ON uc.user_id = ac.user_Id
WHERE t.account_to = 2003 OR t.account_from = 2003
ORDER BY t.transfer_id;