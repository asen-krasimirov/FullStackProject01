import { useEffect, useState } from 'react';
import { buyCrypto, sellCrypto } from '../api/transactionApi';
import { getMarketPrices, getSupportedPairs } from '../api/marketApi';

const BuySellPage = () => {
  const [currency, setCurrency] = useState('');
  const [amount, setAmount] = useState('');
  const [action, setAction] = useState('buy');
  const [message, setMessage] = useState('');
  const [currencies, setCurrencies] = useState([]);
  const [prices, setPrices] = useState({});
  const userId = 1;

  useEffect(() => {
    getSupportedPairs().then(res => {
      const currencyList = res.data.map(pair => pair.split('/')[0]);
      setCurrencies(currencyList);
    });
    getMarketPrices().then(res => setPrices(res.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!currency || !amount) return;

    const numericAmount = parseFloat(amount);
    if (isNaN(numericAmount) || numericAmount <= 0) {
      setMessage('Enter a valid amount.');
      return;
    }

    try {
      if (action === 'buy') {
        await buyCrypto(userId, currency, numericAmount);
      } else {
        await sellCrypto(userId, currency, numericAmount);
      }
      setMessage(`Successfully executed ${action} order.`);
    } catch (err) {
      setMessage(err.response?.data || 'Transaction failed.');
    }
  };

  return (
    <div className="trade-container">
      <h1 className="trade-title">💼 Buy / Sell Cryptocurrency</h1>
      <form onSubmit={handleSubmit} className="trade-form">
        <div className="form-group">
          <label>Action:</label>
          <select value={action} onChange={(e) => setAction(e.target.value)}>
            <option value="buy">Buy</option>
            <option value="sell">Sell</option>
          </select>
        </div>

        <div className="form-group">
          <label>Currency:</label>
          <select value={currency} onChange={(e) => setCurrency(e.target.value)}>
            <option value="">-- Select Currency --</option>
            {currencies.map(curr => (
              <option key={curr} value={curr}>{curr}</option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Amount:</label>
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            placeholder="e.g. 0.01"
          />
        </div>

        <div className="form-price">
          <strong>Current Price:</strong> {currency && prices[currency] ? `$${prices[currency].toFixed(2)}` : 'N/A'}
        </div>

        <button type="submit" className="trade-button">{action === 'buy' ? 'Buy Now' : 'Sell Now'}</button>
        {message && <p className="trade-message">{message}</p>}
      </form>
    </div>
  );
};

export default BuySellPage;
