//Common CSS for all pages.
const css = `
body{font-family:Georgia,'Times New Roman',serif;background:#eef1ec;color:#1f2a24;margin:0;padding:20px}
.card{max-width:560px;margin:16px auto;background:#fff;padding:24px;border-radius:6px;border:1px solid #c9d1c6}
h2{margin-top:0;font-size:22px}
label{display:block;margin-top:12px;font-weight:bold}
input[type=text],input[type=email],input[type=password],input[type=number],select,textarea{width:100%;padding:8px;margin-top:4px;box-sizing:border-box;border:1px solid #9aa89a;border-radius:4px;font:inherit}
button{margin-top:14px;padding:9px 16px;border:0;border-radius:4px;background:#2f5d46;color:#fff;cursor:pointer;font:inherit}
button:disabled{background:#999;cursor:not-allowed}
.err{color:#b3261e;font-size:14px;min-height:16px}.ok{color:#1b7a3d}
table{width:100%;border-collapse:collapse;margin-top:14px}td,th{border:1px solid #c9d1c6;padding:6px;text-align:left}
a.back{display:block;max-width:560px;margin:0 auto;color:#2f5d46}
.row{display:flex;gap:8px;align-items:center}
`;
const st = document.createElement("style");
st.textContent = css;
document.head.appendChild(st);
