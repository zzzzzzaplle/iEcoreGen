### CR 1:  +Manage accounts. Customers can add/remove accounts by providing account id. Each account has a unique account ID. If the account to be deleted has no a balance, or stock transactions, then remove the account. Return true if the operation is successful, false otherwise.
```
Manage accounts. Customers can add/remove accounts by providing account id. Each account has a unique account ID. If the account to be deleted has no a balance, or stock transactions, then remove the account. Return true if the operation is successful, false otherwise.

+ Test Case 1: "Add a new savings account successfully"
    SetUp:
    - Customer “John Doe”, address “123 Main St”, currently holds no accounts.
    Action:
    - Add a savings account with ID “SAV001”, interest rate 2 %.
    Expected Output: True
---
+ Test Case 2: "Add account with duplicate ID"
    SetUp:
    - Customer “Mia” (address “123 Main St”) already holds an investment account “INV001”.
    Action:
    - Attempt to add another investment account using the same ID “INV001”.
    Expected Output:
    - False
    - Customer still holds exactly one account “INV001”.
---
+ Test Case 3: "Remove existing zero-balance account"
    SetUp:
    - Customer “Doli” (address “123 Main St”) holds savings account “SAV002”, **balance $0**, interest rate 3.65 %.
    Action:
    - Remove account “SAV002”.
    Expected Output:
    - True
    - Customer now has no account “SAV002”.
---
+ Test Case 4: "Attempt to remove account that still has balance"
    SetUp:
    - Customer “Liam” (address “123 Main St”) holds savings account “SAV003”, **balance $250**, interest rate 3.65 %.
    Action:
    - Request deletion of account “SAV003”.
    Expected Output:
    - False
    - Customer still holds account “SAV003” with balance $250.
---
+ Test Case 5: "Remove an investment account"
    SetUp:
    - Customer “Aliza” (address “123 Main St”) currently has 2 investment accounts:  "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5).
    Action:
    - Attempt to remove account "INV001", "INV002"
    Expected Output:
    - remove "INV001" : True
    - remove "INV002" : False
```
***

### CR 2: Deposit operation. Customers can deposit money into the savings and investments account and update the account balance. Check that the amount must be positive and cannot exceed the maximum single - deposit limit of $1000000. Return true if the operation is successful, false otherwise.

```
Computational requirement: Deposit operation. Customers can deposit money into the savings and investments account and update the account balance. Check that the amount must be positive and cannot exceed the maximum single - deposit limit of $1000000. Return true if the operation is successful, false otherwise.

+ Test Case 1: "Deposit positive amount to savings"
    SetUp:
    - Customer "Doli" (address "123 Main St") holds savings account "SAV004", balance $1 000, interest rate 3.65 %.
    Action:
    - Deposit $500 into "SAV004".
    Expected Output: 1 500      // new balance
---
+ Test Case 2: "Deposit zero amount"
    SetUp:
    - Customer "Alie" (address "0812 Center St") holds savings account "SAV005", balance $200, interest rate 3.65 %.
    Action:
    - Attempt to deposit $0.
    Expected Output: False, the balance of 'SAV005' is still $200.
---
+ Test Case 3: "Deposit negative amount"
    SetUp:
    - Investment account "INV001" holds balance $300.
    Action:
    - Attempt to deposit –$100.
    Expected Output: False
---
+ Test Case 4: "Deposit exactly at single-deposit limit"
    SetUp:
    - Customer "Alie" (address "0812 Center St") holds an investment account "INV002" holds balance $0.
    Action:
    - Deposit $1 000 000.
    Expected Output: 1 000 000
---
+ Test Case 5: "Deposit exceeds limit by one dollar"
    SetUp:
    - Customer "Alie" (address "0812 Center St") holds a savings account "SAV006" holds balance $50, interest rate 3.65 %.
    Action:
    - Attempt to deposit $1 000 001.
    Expected Output: False
```
***

### CR 3: Calculate the daily interest of the savings account and update the daily interest to the balance at 23:59:59 on the same day. Daily interest = balance * interest rate / 360. 


```
Computational requirement: Calculate the daily interest of the savings account and update the daily interest to the balance at 23:59:59 on the same day. Daily interest = balance * interest rate / 360. 

+ Test Case 1: "Standard balance and rate"
    SetUp:
    - Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
    - "SAV007" balance $10 000, interest rate 3.65 %.
    Action:
    - Trigger daily-interest calculation.
    Expected Output: 1.01        // interest added, new balance $10 001.01
---
+ Test Case 2: "Zero balance"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
    - Savings account "SAV008" balance $0, interest rate 5 %.
    Action:
    - Trigger daily-interest calculation.
    Expected Output: 0.00
---
+ Test Case 3: "Very small balance"
    SetUp:
    - Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
    - Savings account "SAV009" balance $0.01, interest rate 2 %.
    Action:
    - Trigger daily-interest calculation.
    Expected Output: 0.00        // rounds down
---
+ Test Case 4: "Large balance"
    SetUp:
    - Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
    - Savings account "SAV010" balance $1 000 000, interest rate 2 %.
    Action:
    - Trigger daily-interest calculation.
    Expected Output: 55.56
---
+ Test Case 5: "High-precision rate"
    SetUp:
    - Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
    - Savings account "SAV011" balance $1 234.56, interest rate 2.75 %.
    Action:
    - Trigger daily-interest calculation.
    Expected Output: 0.09
```
***

### CR 4: Buy stocks and update the investment account balance. The system save the transaction records. There is no need to combine multiple purchases of the same stock. Before buying stocks, it is necessary to ensure that the current balance is sufficient to cover the stock cost and the bank's commission. Stock cost = number of stocks * price. Return true if save the transaction records successfully, false otherwise. 
```
Computational requirement: Buy stocks and update the investment account balance. The system save the transaction records. There is no need to combine multiple purchases of the same stock. Before buying stocks, it is necessary to ensure that the current balance is sufficient to cover the stock cost and the bank's commission. Stock cost = number of stocks * price. Return true if save the transaction records successfully, false otherwise. 

+ Test Case 1: "Sufficient balance purchase"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a investment account "INV100"
    - Investment account "INV100" balance $10 000.
    Action:
    - Buy 100 shares of "ABC" at $50 each (cost $5 000, commission $500).
    Expected Output: True
---
+ Test Case 2: "Insufficient funds"
    SetUp:
    - Investment account "INV101" balance $5 000.
    Action:
    - Attempt to buy 200 shares of "XYZ" at $30 each (commission $600, total needed $6 600).
    Expected Output: False
---
+ Test Case 3: "Exact-funds purchase"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a investment account "INV102"
    - Investment account "INV102" balance $5 500.
    Action:
    - Buy 100 shares of "DEF" at $50 each (commission $500, total needed $5 500 exactly).
    Expected Output: True
---
+ Test Case 4: "Low-value single share"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a investment account "INV103"
    - Investment account "INV103" balance $100.
    Action:
    - Buy 1 share of "GHI" at $80 (commission $8, total $88).
    Expected Output: True
---
+ Test Case 5: "Second purchase fails after balance drops"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a investment account "INV104"
    - Investment account "INV104" balance $4 000.
    - First purchase already completed: 50 shares of "JKL" at $40 (cost $2 000, commission $200, remaining balance $1 800).
    Action:
    - Attempt a second identical purchase requiring $2 200.
    Expected Output: False
```
***

### CR 5: Calculate the value of the investment account, including the account balance and the total value of stocks. The value of each stock is the number of stocks multiplied by the current stock market price (1.1 times its purchase price). Return the account balance if there is no stock transaction in the investment account. The value keep two decimal places.


```
Computational requirement: Calculate the value of the investment account, including the account balance and the total value of stocks. The value of each stock is the number of stocks multiplied by the current stock market price (1.1 times its purchase price). Return the account balance if there is no stock transaction in the investment account. The value keep two decimal places.

+ Test Case 1: "Multiple stock positions"
    SetUp:
    - Customer "Poe" (address "0814 Center St") holds a investment account "INV200"
    - Investment account "INV200" balance $5000.
    - Buy 10 shares "AAPL" at $100; (The new balance is $3900)
    - Buy 20 shares "MSFT" at $50; (The new balance is $2800)
    Action:
    - Calculate total value.
    Expected Output: $5 000      
---
+ Test Case 2: "Empty account"
    SetUp:
    - Customer "Peter" (address "0816 Center St") holds a investment account "INV201"
    - New investment account "INV201" balance $0, no trades.
    Action:
    - Calculate total value.
    Expected Output: 0
---
+ Test Case 3: "Cash only, no stocks"
    SetUp:
    - Customer 'Alice' (address "0811 Center St") holds a investment account "INV202"
    - Investment account "INV202" balance $1 000, no trades.
    Action:
    - Calculate total value.
    Expected Output: $1 000
---
+ Test Case 4: "Repeated purchases of same stock"
    SetUp:
    - Customer 'Bide' (address "0810 Main St") holds a investment account "INV203"
    - Investment account "INV203" balance $1 000.
    - Buy 5 shares "AAPL" at $100. (The new balance is $450)
    - Buy 1 shares "AAPL" at $120. (The new balance is $318)
    Action:
    - Calculate total value.
    Expected Output: $ 1 000
---
+ Test Case 5: "Precision check with fractional share"
    SetUp:
    - Customer 'Carli' (address "0820 Main St") holds a investment account "INV204"
    - Investment account "INV204" balance $123.45.
    - Buy 10 shares of "MNO" at $10.10. (The new balance is $12.35)
    - Deposit $500 into "INV204". (The new balance is $512.35)
    - Buy 5 shares of "MNO" at $10.10. (The new balance is $456.8)
    Action:
    - Calculate total value.
    Expected Output: $623.45
```
***