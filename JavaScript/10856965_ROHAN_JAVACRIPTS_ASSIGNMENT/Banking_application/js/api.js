export function checkAccount(accountNumber) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (accountNumber) {
        resolve("Account found");
      } else {
        reject("Account not found");
      }
    }, 1500);
  });
}
 
export async function loadCustomerData() {
  try {
    const response = await fetch("./data/customers.json");
    if (!response.ok) throw new Error("Network response was not ok");
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Fetch error:", error);
    return [];
  }
}
 