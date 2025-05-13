import axios from 'axios';

export const getMarketPrices = () => axios.get('/api/market/prices');
export const getSupportedPairs = () => axios.get('/api/market/supportedPairs');
