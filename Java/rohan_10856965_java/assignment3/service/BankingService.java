package assignment3.service;
import assignment3.exception.CustomerNotFoundException;
import assignment3.exception.InsufficientBalanceException;
import assignment3.exception.InvalidAccountException;
import assignment3.exception.InvalidTransactionException;
import assignment3.model.AccountStatus;
import assignment3.model.BankAccount;
import assignment3.model.Customer;
import assignment3.model.Transaction;
import assignment3.exception.*; import assignment3.model.*; import assignment3.record.AccountSummary;
import java.time.LocalDateTime; import java.util.*; import java.util.concurrent.atomic.AtomicLong; import java.util.stream.*;
public class BankingService {
 private final List<Customer> customers=new ArrayList<>();
 private final List<BankAccount> accounts=new ArrayList<>();
 private final List<Transaction> transactions=new ArrayList<>();
 private final AtomicLong txSeq=new AtomicLong(1);
 public List<Customer> customers(){return List.copyOf(customers);}
 public List<BankAccount> accounts(){return List.copyOf(accounts);}
 public List<Transaction> transactions(){return List.copyOf(transactions);}
 public void addCustomer(Customer c){if(findCustomer(c.getCustomerId()).isPresent()) throw new IllegalArgumentException("Duplicate customer ID"); customers.add(c);}
 public void addAccount(BankAccount a){requireCustomer(a.customerId()); if(accounts.stream().anyMatch(x->x.accountNumber()==a.accountNumber())) throw new IllegalArgumentException("Duplicate account number"); accounts.add(a);}
 public Optional<Customer> findCustomer(int id){return customers.stream().filter(c->c.getCustomerId()==id).findFirst();}
 public Customer requireCustomer(int id){return findCustomer(id).orElseThrow(()->new CustomerNotFoundException("Customer "+id+" not found"));}
 public BankAccount requireAccount(long n){return accounts.stream().filter(a->a.accountNumber()==n).findFirst().orElseThrow(()->new InvalidAccountException("Account "+n+" not found"));}
 private void validAmount(double amount){if(!Double.isFinite(amount)||amount<=0) throw new InvalidTransactionException("Amount must be positive and finite");}
 public synchronized void deposit(long n,double amount){validAmount(amount); BankAccount a=requireAccount(n); ensureActive(a); a.credit(amount); addTx(n,"DEPOSIT",amount,"Cash deposit");}
 public synchronized void withdraw(long n,double amount){validAmount(amount); BankAccount a=requireAccount(n); ensureActive(a); if(a.balance()-amount<a.minimumBalance()) throw new InsufficientBalanceException("Minimum balance ₹"+a.minimumBalance()+" must remain"); a.debit(amount); addTx(n,"WITHDRAW",amount,"Cash withdrawal");}
 public synchronized void transfer(long source,long target,double amount){validAmount(amount); if(source==target) throw new InvalidTransactionException("Source and target must differ"); BankAccount s=requireAccount(source),t=requireAccount(target); ensureActive(s); ensureActive(t); if(s.balance()-amount<s.minimumBalance()) throw new InsufficientBalanceException("Transfer violates minimum balance"); s.debit(amount); t.credit(amount); addTx(source,"TRANSFER_OUT",amount,"Transfer to "+target); addTx(target,"TRANSFER_IN",amount,"Transfer from "+source);}
 public synchronized double calculateInterest(long n,double rate){if(rate<0) throw new InvalidTransactionException("Rate cannot be negative"); BankAccount a=requireAccount(n); double value=a.balance()*rate/100; a.credit(value); addTx(n,"INTEREST",value,"Interest at "+rate+"%"); return value;}
 public double checkBalance(long n){return requireAccount(n).balance();}
 private void ensureActive(BankAccount a){if(a.status()!= AccountStatus.ACTIVE) throw new InvalidAccountException("Account is not active");}
 private void addTx(long n,String type,double amount,String d){transactions.add(new Transaction(txSeq.getAndIncrement(),n,type,amount,LocalDateTime.now(),d));}
 public Map<Integer,Double> customerTotals(){return accounts.stream().collect(Collectors.groupingBy(BankAccount::customerId,Collectors.summingDouble(BankAccount::balance)));}
 public List<Customer> highValueCustomers(double threshold){Map<Integer,Double> totals=customerTotals(); return customers.stream().filter(c->totals.getOrDefault(c.getCustomerId(),0.0)>threshold).toList();}
 public List<Customer> topCustomers(int limit){Map<Integer,Double> totals=customerTotals(); return customers.stream().sorted(Comparator.comparingDouble((Customer c)->totals.getOrDefault(c.getCustomerId(),0.0)).reversed()).limit(limit).toList();}
 public List<BankAccount> searchAccounts(String type,Double min,Double max,AccountStatus status){return accounts.stream().filter(a->type==null||a.accountType().equalsIgnoreCase(type)).filter(a->min==null||a.balance()>=min).filter(a->max==null||a.balance()<=max).filter(a->status==null||a.status()==status).toList();}
 public String generateBankingDashboard(){
  long premium=customers.stream().filter(c->c.getCustomerType().equals("PREMIUM")).count(); DoubleSummaryStatistics stats=accounts.stream().mapToDouble(BankAccount::balance).summaryStatistics();
  Map<String,Long> typeCounts=accounts.stream().collect(Collectors.groupingBy(BankAccount::accountType,Collectors.counting())); Map<String,Long> cities=customers.stream().collect(Collectors.groupingBy(Customer::getCity,Collectors.counting()));
  double deposits=transactions.stream().filter(t->Set.of("DEPOSIT","INTEREST","TRANSFER_IN").contains(t.transactionType())).mapToDouble(Transaction::amount).sum(); double withdrawals=transactions.stream().filter(t->Set.of("WITHDRAW","TRANSFER_OUT").contains(t.transactionType())).mapToDouble(Transaction::amount).sum();
  return """
   ========== BANKING ANALYTICS ==========
   Customers       : %d
   Premium         : %d
   Accounts        : %d
   Total balance   : ₹%.2f
   Average balance : ₹%.2f
   Highest balance : ₹%.2f
   Transactions    : %d
   Credits total   : ₹%.2f
   Debits total    : ₹%.2f
   Accounts/type   : %s
   Customers/city  : %s
   =======================================
   """.formatted(customers.size(),premium,accounts.size(),stats.getSum(),stats.getAverage(),stats.getCount()==0?0:stats.getMax(),transactions.size(),deposits,withdrawals,typeCounts,cities);
 }
 public void loadSampleData(){
  if(!customers.isEmpty()) return;
  String[][] data={{"101","Rahul","rahul@gmail.com","Bangalore","9876543210","PREMIUM"},{"102","Priya","priya@gmail.com","Mangalore","9876543211","REGULAR"},{"103","Arun","arun@gmail.com","Mysore","9876543212","PREMIUM"},{"104","Sneha","sneha@gmail.com","Udupi","9876543213","REGULAR"},{"105","Kiran","kiran@gmail.com","Bangalore","9876543214","PREMIUM"},{"106","Asha","asha@gmail.com","Mangalore","9876543215","REGULAR"},{"107","Vikram","vikram@gmail.com","Pune","9876543216","PREMIUM"},{"108","Neha","neha@gmail.com","Chennai","9876543217","REGULAR"},{"109","Ravi","ravi@gmail.com","Hyderabad","9876543218","PREMIUM"},{"110","Meera","meera@gmail.com","Kolkata","9876543219","REGULAR"}};
  for(String[] d:data)addCustomer(new Customer(Integer.parseInt(d[0]),d[1],d[2],d[3],d[4],d[5]));
  for(int i=0;i<15;i++){long n=100001L+i;int c=101+i%10;double b=10000+i*7500;BankAccount a=switch(i%3){case 0->new SavingsAccount(n,c,b,AccountStatus.ACTIVE);case 1->new CurrentAccount(n,c,b,AccountStatus.ACTIVE);default->new LoanAccount(n,c,b,AccountStatus.ACTIVE);};addAccount(a);}
  for(int i=0;i<30;i++){BankAccount a=accounts.get(i%accounts.size());double amount=1000+(i*1800); if(i%2==0) deposit(a.accountNumber(),amount); else {double allowed=Math.max(1,a.balance()-a.minimumBalance()); withdraw(a.accountNumber(),Math.min(amount,allowed));}}
 }
 public List<AccountSummary> summaries(){return accounts.stream().map(a->new AccountSummary(a.accountNumber(),a.accountType(),a.balance())).toList();}
}
