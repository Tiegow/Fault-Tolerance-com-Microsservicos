import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Trend } from 'k6/metrics';

const count200 = new Counter('count_200_ok');
const count503FailFast = new Counter('count_503_fail_fast');
const count503FailSlow = new Counter('count_503_fail_slow');
const count500 = new Counter('count_500_error');

const durationSuccess = new Trend('duration_success');
const durationFail = new Trend('duration_fail');

export const options = {
    stages: [
    { duration: '0s', target: 5 },
    { duration: '20s', target: 5 },

    { duration: '5s', target: 350 },
    { duration: '5s', target: 5 },

    { duration: '20s', target: 5 },

    { duration: '5s', target: 350 },
    { duration: '5s', target: 5 },

    { duration: '20s', target: 5 },

    { duration: '5s', target: 350 },
    { duration: '5s', target: 5 },

    { duration: '20s', target: 5 },
    ],

    thresholds: {
      http_req_failed: ['rate<0.05'],
      'duration_success': ['p(95)<3000'],
      'duration_fail': ['p(90)<2000'],
    },
};

export default function () {
    const BASE_URL = 'http://localhost:8084/imdtravel';
  
    const flight = 'TP-008';
    const day = '2025-11-10';
    const user = 'k6-tester-' + __VU;

    const ft = false;

    const url = `${BASE_URL}/buyTicket?flight=${flight}&day=${day}&user=${user}&ft=${ft}`;

    const res = http.post(url);

    if (res.status === 200) {
        durationSuccess.add(res.timings.duration);
        count200.add(1);

        check(res, {
            'Sucesso (200)': (r) => true,
        });

    } else if (res.status === 503 && res.timings.duration < 2000) {
        durationFail.add(res.timings.duration);
        count503FailFast.add(1);

        check(res, {
            'Fail Fast (503)': (r) => true,
        });
    }  else if (res.status === 503) {
        durationFail.add(res.timings.duration);
        count503FailSlow.add(1);

        check(res, {
            'Fail Fast (503)': (r) => false,
        });
    } else {
        durationFail.add(res.timings.duration);
        count500.add(1);

        check(res, {
            'Erro Inesperado': (r) => false,
        });
    }

    sleep(Math.random() * 0.5 + 0.5);
}
