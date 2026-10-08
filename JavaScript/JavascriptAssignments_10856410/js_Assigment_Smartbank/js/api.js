
// Fetch API demonstration required by the assignment.
// Run the project through a local HTTP server for this to work.
export const fetchCustomers = async () => {
  const response = await fetch("./data/customers.json");
  if (!response.ok) throw new Error("Unable to load customer data");
  return response.json();
};
