
import { customerTransactions, formatINR } from "./storage.js";

const state = {search:"",type:"all",sort:"newest",min:0};
const render = () => {
  let transactions = [...customerTransactions()];
  const filtered = transactions.filter(txn => {
    const query = state.search.toLowerCase();
    return (!query || `${txn.id} ${txn.description} ${txn.amount}`.toLowerCase().includes(query))
      && (state.type === "all" || txn.type === state.type)
      && Number(txn.amount) >= state.min;
  });
  filtered.sort((a,b) => {
    if (state.sort === "oldest") return new Date(a.date)-new Date(b.date);
    if (state.sort === "high") return b.amount-a.amount;
    if (state.sort === "low") return a.amount-b.amount;
    return new Date(b.date)-new Date(a.date);
  });
  document.querySelector("#filteredCount").textContent = filtered.length;
  document.querySelector("#filteredTotal").textContent = formatINR(filtered.reduce((sum,t)=>sum+t.amount,0), true);
  document.querySelector("#transactionTable").innerHTML = filtered.map(txn => `
    <tr><td>${txn.date}</td><td><b>${txn.id}</b></td><td><span class="type-badge ${txn.type.toLowerCase()}">${txn.type}</span></td><td>${txn.description}</td><td class="align-right"><b>${txn.type==="Credit"?"+":"-"}${formatINR(txn.amount)}</b></td><td class="align-right">${formatINR(txn.balance)}</td></tr>`).join("");
  document.querySelector("#emptyTransactions").classList.toggle("hidden", filtered.length > 0);
};
["searchInput","typeFilter","sortFilter","minAmount"].forEach(id => document.getElementById(id)?.addEventListener("input", event => {
  const map = {searchInput:"search",typeFilter:"type",sortFilter:"sort",minAmount:"min"};
  state[map[id]] = id === "minAmount" ? Number(event.target.value || 0) : event.target.value;
  render();
}));
render();
