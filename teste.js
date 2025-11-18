import http from 'k6/http';
import { check, sleep } from 'k6';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";

export const options = {
  stages: [
    { duration: '10s', target: 5 }, 
    { duration: '1s', target: 0 }, 
    { duration: '10s', target: 5 }, 
  ],

  // Critérios de Aceitação (Thresholds)
  thresholds: {
    // Disponibilidade: Menos de 5% das requisições podem falhar
    http_req_failed: ['rate<0.05'], 
    
    // Desempenho: 95% das requisições devem ser atendidas em menos de 5s
    http_req_duration: ['p(95)<5000'], 
  },
};

export default function () {
  // --- CONFIGURAÇÃO ---
  const BASE_URL = 'http://localhost:8084/imdtravel'; 
  
  const flight = 'TP-008';
  const day = '2025-11-10';
  const user = 'k6-tester-' + __VU; 
  
  // Testar COM ou SEM tolerância a falhas
  const ft = true; 

  const url = `${BASE_URL}/buyTicket?flight=${flight}&day=${day}&user=${user}&ft=${ft}`;

  // --- REQUISIÇÃO ---
  const res = http.post(url);

  // --- VALIDAÇÃO (CHECKS) ---
  check(res, {
    'status é 200 (OK)': (r) => r.status === 200
  });

  // Pausa aleatória entre 0.5s e 1s antes da próxima compra
  sleep(Math.random() * 0.5 + 0.5);
}

// export function handleSummary(data) {
//   return {
//     "relatorio-teste-pico-low.html": htmlReport(data),
//   };
// }