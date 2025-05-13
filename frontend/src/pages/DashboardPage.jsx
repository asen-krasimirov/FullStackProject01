import { useEffect, useState } from 'react';
import { getUserBalance } from '../api/userApi';
import { getReturns } from '../api/transactionApi';

const DashboardPage = () => {
    const [balance, setBalance] = useState(0);
    const [returns, setReturns] = useState({});
    const userId = 1;

    useEffect(() => {

    }, []);

    useEffect(() => {
        const fetchPrices = () => {
            getUserBalance(userId).then(res => setBalance(res.data));
            getReturns(userId).then(res => setReturns(res.data));
        };

        fetchPrices();
        const interval = setInterval(fetchPrices, 1000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">📊 Dashboard</h1>

            <div className="balance-card">
                <h2>💰 Current Balance</h2>
                <p className="balance-amount">${balance.toFixed(2)}</p>
            </div>

            <div className="returns-card">
                <h2>📈 Investment Returns</h2>
                <ul>
                    {Object.entries(returns).length === 0 ? (
                        <li>No transactions yet.</li>
                    ) : (
                        Object.entries(returns).map(([currency, value]) => (
                            <li key={currency} className={value >= 0 ? 'positive' : 'negative'}>
                                {currency}: {value >= 0 ? '+' : ''}${value.toFixed(5)}
                            </li>
                        ))
                    )}
                </ul>
            </div>
        </div>
    );
};

export default DashboardPage;
