export const KEYS={customers:'sb_customers',accounts:'sb_accounts',transactions:'sb_transactions'};
export const get=(key,fallback=[])=>JSON.parse(localStorage.getItem(key)??JSON.stringify(fallback));
export const set=(key,value)=>localStorage.setItem(key,JSON.stringify(value));
export const currentUser=()=>sessionStorage.getItem('loggedInUser');
export const getCustomer=()=>get(KEYS.customers).find(({username})=>username===currentUser());
export const uid=(prefix='TXN')=>`${prefix}${new Date().toISOString().slice(0,10).replaceAll('-','')}${String(Date.now()).slice(-5)}`;
