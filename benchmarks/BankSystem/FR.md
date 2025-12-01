// ==version1==
```
+ Manage accounts. Customers can add/remove accounts by providing account id. Each account has a unique account ID. If the account to be deleted has no a balance, or stock transactions, then remove the account. Return true if the operation is successful, false otherwise.

+ Deposit operation. Customers can deposit money into the savings and investments account and update the account balance. Check that the amount must be positive and cannot exceed the maximum single - deposit limit of $1000000. Return true if the operation is successful, false otherwise.

+ Calculate the daily interest of the savings account and update the daily interest to the balance at 23:59:59 on the same day. Daily interest = balance * interest rate / 360. The daily interest keep two decimal places.

+ Buy stocks and update the investment account balance. The system save the transaction records. There is no need to combine multiple purchases of the same stock. Before buying stocks, it is necessary to ensure that the current balance is sufficient to cover the stock cost and the bank's commission. Stock cost = number of stocks * price. Return true if save the transaction records successfully, false otherwise. 

+ Calculate the value of the investment account, including the account balance and the total value of stocks. The value of each stock is the number of stocks multiplied by the current stock market price (1.1 times its purchase price). Return 0 if there is no stock transaction in the investment account. The value keep two decimal places.
```
// ==end==
