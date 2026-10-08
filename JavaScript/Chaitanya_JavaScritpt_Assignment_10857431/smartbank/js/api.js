export const fetchSampleCustomers = async () => {
  const response = await fetch("./data/customers.json");
  if (!response.ok) throw new Error("Unable to load sample JSON data.");
  return await response.json();
};

export const loadSampleData = async () => {
  try {
    return await fetchSampleCustomers();
  } catch (error) {
    console.warn(error.message);
    return [];
  }
};
