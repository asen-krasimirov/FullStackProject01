import axios from 'axios';

export const buyCrypto = (userId, currency, amount) =>
    axios.post(`/api/transaction/buy?userId=${userId}&currency=${currency}&amount=${amount}`);

export const sellCrypto = (userId, currency, amount) =>
    axios.post(`/api/transaction/sell?userId=${userId}&currency=${currency}&amount=${amount}`);

export const getTransactionHistory = (userId) =>
    axios.get(`/api/transaction/history?userId=${userId}`);

export const getReturns = (userId) =>
    axios.get(`/api/transaction/returns?userId=${userId}`);
