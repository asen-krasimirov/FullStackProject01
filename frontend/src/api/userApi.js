import axios from 'axios';

export const getUserBalance = (userId) => axios.get(`/api/user/balance?userId=${userId}`);
export const resetUser = (userId) => axios.post(`/api/user/reset?userId=${userId}`);
