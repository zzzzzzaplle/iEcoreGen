// ==version1==
```plantuml
class Customer {
    - name : String
    - address : String
    - accounts : List<Account>

    + addSavingAccount(id: String, interestRate: double): boolean
    + addInvestmentAccount(id: String): boolean
    + removeInvestmentAccount(id: String): boolean
    + removeSavingAccount(id: String): boolean
    + findAccountById(id: String) : Account
}

abstract class Account {
    - id : string
    - balance : double
    
    + deposit(amount : double) : boolean
}

Customer *-- "*" Account : accounts

class SavingAccount extends Account {
    - interestRate : double

    + calculateDailyInterest() : double
}

class InvestmentAccount extends Account {
    - transactions : List<StockTransaction>

    + buyStock(stockSymbol : String, quantity : int, price : double) : boolean
    + calculateValue() : double
}

class StockTransaction {
    - stock : String
    - quantity : integer
    - price : double
    - commission : double
}

InvestmentAccount *-- "*" StockTransaction : transactions
```
// ==end==
